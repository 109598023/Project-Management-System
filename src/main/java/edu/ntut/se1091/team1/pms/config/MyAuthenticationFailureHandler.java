package edu.ntut.se1091.team1.pms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntut.se1091.team1.pms.web.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Response data = new Response(HttpStatus.FORBIDDEN.value(),exception.getMessage());
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(data));
    }

}
