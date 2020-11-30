package edu.ntut.se1091.team1.pms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntut.se1091.team1.pms.web.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Response data = new Response(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name());
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(data));
        redirectStrategy.sendRedirect(request,response, "/");
    }
}
