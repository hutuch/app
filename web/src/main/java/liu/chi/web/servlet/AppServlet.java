package liu.chi.web.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@WebServlet("/appservlet")
public class AppServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("name :" + request.getParameter("name"));
        InputStream in = new FileInputStream("C:\\Users\\Administrator\\Desktop\\qq.jpg");
        byte[] bys = new byte[in.available()];
        in.read(bys);
        response.getOutputStream().write(bys);
        in.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("post 请求");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("中文。。乱码了吗");

    }
}
