package project.forwork.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import project.forwork.api.domain.token.service.TokenCookieService;
import project.forwork.api.domain.token.service.TokenHeaderService;
import project.forwork.api.domain.token.service.TokenService;

import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String USER_ID = "userId";
    private final TokenService tokenService;
    //private final TokenCookieService tokenCookieService;
    private final TokenHeaderService tokenHeaderService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 웹 리소스 및 옵션 요청은 통과
        if (HttpMethod.OPTIONS.matches(request.getMethod()) || handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        String accessToken = tokenHeaderService.extractTokenFromHeader(request, TokenHeaderService.ACCESS_TOKEN_HEADER);
        Long userId = tokenService.validateAndGetUserId(accessToken);
        requestContext.setAttribute(USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
        return true;
    }

/*    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // WEB, chrome 의 경우 GET, POST OPTIONS = pass
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        // js, html, png resource 를 요청 하는경우 Pass
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        String accessToken = tokenCookieService.extractTokenFromCookies(request, TokenCookieService.ACCESS_TOKEN);
        Long userId = tokenService.validateAndGetUserId(accessToken);
        requestContext.setAttribute(USER_ID, userId, RequestAttributes.SCOPE_REQUEST);

        return true;
    }*/
}
