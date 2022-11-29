package com.claro.WSLigaCampeones.security.jwt;

import com.claro.WSLigaCampeones.security.service.UserDetailsServiceImpl;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.logging.log4j.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
        	
//        	System.out.println(req.getServletPath().toString());
            String token = getToken(req);
//            token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMDM0MDQ4MDExMTEiLCJpYXQiOjE2MTA3NDE1OTYsImV4cCI6MTYxMDc3NzU5Nn0.V__mmOha_SXCN20lqYVlu-7twKt_SsaCLFZQrSJaTBjl-R67eJU6OCMROWegH-gVxozs0L2sIA4afwzH5g6y_Q";
            if(token != null && jwtProvider.validateToken(token)){
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
        	logger.error("Se presentó error en el consumo del procedimiento de LUGARES", e);
        }
        filterChain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
