package br.com.evernot.project.security.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.evernot.project.domain.UserEntity;
import br.com.evernot.project.repository.UserRepository;
import br.com.evernot.project.security.jwt.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter{

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var token = parseJwt(request);

                if(token != null){
                    try{

                    

                    var email = tokenService.validateToken(token);
                    
                    if(!email.isEmpty()){
                        UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

                        var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                   } catch( Exception e) { 
                    System.out.println("Error in validate token" + e.getMessage());
                   } 
                }
                filterChain.doFilter(request, response);
    }

    public String parseJwt(HttpServletRequest req){

        String header = req.getHeader("Authorization");

        if(header == null) return null;

        return header.replace("Bearer ", "");
    }

}
