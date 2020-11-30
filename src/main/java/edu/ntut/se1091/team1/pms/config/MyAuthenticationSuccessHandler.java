package edu.ntut.se1091.team1.pms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntut.se1091.team1.pms.web.dto.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        response.getOutputStream().println(new ObjectMapper().writeValueAsString(new Response()));

    }
}
