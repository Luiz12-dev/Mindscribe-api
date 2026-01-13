package br.com.evernot.project.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.evernot.project.domain.UserEntity;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secrete;

    public String generateToken(UserEntity user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secrete);

            return JWT.create()
            .withIssuer("mindscribe-api")
            .withSubject(user.getEmail())
            .withExpiresAt(genExpirationDate())
            .sign(algorithm);

        } 
        catch(JWTCreationException ex){
            throw new RuntimeException("Error in generating token", ex);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secrete);

            return 
            JWT.require(algorithm)
            .withIssuer("mindscribe-api")
            .build()
            .verify(token)
            .getSubject();
            
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
