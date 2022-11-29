package com.claro.WSLigaCampeones.security.jwt;

import com.claro.WSLigaCampeones.security.entity.UsuarioPrincipal;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import io.jsonwebtoken.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("token mal formado ",e);
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado ",e);
        }catch (ExpiredJwtException e){
            logger.error("token expirado ", e );
        }catch (IllegalArgumentException e){
            logger.error("token vacío ",e);
        }catch (SignatureException e){
            logger.error("fail en la firma");
        }
        return false;
    }
}
