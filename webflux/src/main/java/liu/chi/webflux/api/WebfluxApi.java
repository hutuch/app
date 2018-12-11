package liu.chi.webflux.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuchi
 * @date 2018-12-11 16:56
 */
@Configuration
public class WebfluxApi {

    HandlerFunction<ServerResponse> timeFunction = request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);

    HandlerFunction<ServerResponse> dateFunction = request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just("Now is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())), String.class);

    @Bean
    public RouterFunction<ServerResponse> responseRouterFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/time"), timeFunction)
                .andRoute(RequestPredicates.GET("/date"), dateFunction);
    }
}
