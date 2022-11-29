package com.claro.WSLigaCampeones.security.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        logger.error("fail en el método commence",req);
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
    }
}
