package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

                // 빈 ModelAndView 를 반환하면 뷰를 렌더링 하지 않고, 정상 흐름으로 서블릿이 리턴된다.
                // View 나 Model 등을 지정해서 반환하면 뷰를 렌더링 한다.
                // null 반환시 다음 exceptionResolver 를 찾아서 실행한다. 만약 처리할 수 없으면 예외처리가 안되고 기존에 발생한 예외를 던진다.
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver error", e);
        }

        return null;
    }
}
