package liu.chi.httpclient.apache;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * https://hk.saowen.com/a/080211844c07505d3c7fdc93b08cfdadb1bdd5f7d31264747ccb81baf50c32d3
 *
 * CONNECTION_TIMEOUT是連接超時時間，SO_TIMEOUT是socket超時時間
 * 連接超時時間是發起請求前的等待時間；socket超時時間是等待數據的超時時間
 *
 * http://zhoujinhuang.iteye.com/blog/2109067
 *
 * A 从连接池获取连接，最长的等待时间，使用RequestConfig. getConnectionRequestTimeout。
 *
 * B 创建socket对象，使用SocketConfig . getSoTimeout设置socket的soTimeout.
 *
 * C  socket建立网络连接，使用RequestConfig.getConnectTimeout作为发起连接的超时时间。
 *
 * D createLayeredSocket完成SSL握手处理，使用socket的超时时间（就是B设置的SocketConfig.getSoTimeout）
 *
 * E 使用RequestConfig.getSocketTimeout设置分配的Socket的soTimeout。后续处理过程就用这个超时时间了
 *
 * @author liuchi
 * @date 2018-09-14 16:47
 */
@Slf4j
public class HttpClientUtil {
    //请求连接 线程安全
    private static CloseableHttpClient httpclient;
    private static HttpClientContext httpClientContext = null;

    static {
        HttpClientConnectionManager connectionManager = getConnectionManager();
        httpclient = HttpClients.custom().setConnectionManager(connectionManager)
                .setRetryHandler(getRetryHandler())
                .build();

        //创建HttpClientContext
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCredentialsProvider(credsProvider);

        //清理无效链接
        new IdleConnectionEvictor(connectionManager, 1, TimeUnit.MINUTES).start();
    }

    private static PoolingHttpClientConnectionManager getConnectionManager() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(getsSslContext()))
                .build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        //设置整个连接池最大连接数
        connManager.setMaxTotal(200);
        //每一个路由(主机)连接数
        connManager.setDefaultMaxPerRoute(20);

        //socket配置
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoTimeout(3000).build();
        connManager.setDefaultSocketConfig(socketConfig);

        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(5000).setMaxLineLength(10000).build();
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints).build();
        connManager.setDefaultConnectionConfig(connectionConfig);

        return connManager;
    }

    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                //建立网络连接的超时时间
                .setConnectTimeout(2 * 1000)
                //通讯过程中的超时时间
                .setSocketTimeout(5 * 1000)
                //等待连接池分配的超时时间
                .setConnectionRequestTimeout(2 * 1000)
                .build();
    }

    private static HttpRequestRetryHandler getRetryHandler() {
        /**
         * 返回false则不重试
         */
        return (exception, executionCount, context) -> {
            //最多尝试一次
            if (executionCount >= 1) {
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                log.info("建立socket链接超时,请检查网络.");
                return true;
            }
            if (exception instanceof SocketTimeoutException) {
                log.info("读取数据超时,请查看目标机服务是否正常.");
                return true;
            }
            if (exception instanceof NoHttpResponseException) {
                log.info("服务器响应异常,目标机可能压力过大,丢弃了该请求.");
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                log.info("SSL握手异常");
                return false;
            }

            HttpRequest request = (HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);
            //如果目标机需要再次握手,则停止重试.
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                return true;
            } else {
                log.warn("目标机要求再次握手,停止重试");
            }
            return false;
        };
    }

    /**
     * SSLSocket扩展Socket并提供使用SSL或TLS协议的安全套接字
     * <p>
     * SSLContext 此类的实例表示安全套接字协议的实现
     */
    private static SSLContext getsSslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().build();
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sslContext;
    }


    /**
     * 发送 get请求
     * response close; httpGet  release
     * 如果httpclient是一次性,则connection shutdown,httpclient close()
     */
    public static void get(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig());

        try {
            //需要登录
            CloseableHttpResponse response = httpclient.execute(httpGet,httpClientContext);
            //不需要登录
            CloseableHttpResponse response2 = httpclient.execute(httpGet);

            //未收到响应
            if (response == null) {
                httpGet.abort();
            }

            try {
                //http响应码
                System.out.println("http响应码:" + response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("响应体:" + EntityUtils.toString(entity));
                }
            } finally {
                response.close();//回收链接
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
    }

    /**
     * post方式提交表单
     * 下载时,将entity的流按照文件处理
     * response close; httpGet  release
     */
    public static void post(String uri, Map<String, String> form) {
        //请求方式
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(getRequestConfig());

        List<NameValuePair> formParams = new ArrayList<>();
        if (form != null && form.size() > 0) {
            for (String key : form.keySet()) {
                formParams.add(new BasicNameValuePair(key, form.get(key)));
            }
        }
        //请求体
        StringEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);

            //未收到响应
            if (response == null) {
                httpPost.abort();
            }

            try {
                System.out.println("http响应码:" + response.getStatusLine().getStatusCode());
                System.out.println("响应体:" + EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
    }


    public static void upload(String serverUrl, String localFilePath, Map<String, String> forms) {
        HttpPost httpPost = new HttpPost(serverUrl);
        httpPost.setConfig(getRequestConfig());

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        FileBody binFileBody = new FileBody(new File(localFilePath));
        //表单名=表单值
        multipartEntityBuilder.addPart("file2", binFileBody);

        // 设置其他参数
        ContentType type = ContentType.create("application/x-www-form-urlencoded", Charset.forName("UTF-8"));
        if (forms != null && forms.size() > 0) {
            for (String key : forms.keySet()) {
                multipartEntityBuilder.addPart(key, new StringBody(forms.get(key), type));
            }
        }

        HttpEntity reqEntity = multipartEntityBuilder.build();
        httpPost.setEntity(reqEntity);

        try {
            //需要登录
            CloseableHttpResponse response = httpclient.execute(httpPost,httpClientContext);
            //不需要登录
            CloseableHttpResponse response2 = httpclient.execute(httpPost,httpClientContext);
            try {
                System.out.println("http响应码:" + response.getStatusLine().getStatusCode());
                System.out.println("响应体:" + EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }

    }
}
