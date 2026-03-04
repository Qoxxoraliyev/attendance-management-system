package com.muhammadali.attendance_management_system.security.jwt;
import com.muhammadali.attendance_management_system.exceptions.AuthenticationFailedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;


    public JwtAuthFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{

        String path = request.getServletPath();

        if (path.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader=request.getHeader("Authorization");
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=authHeader.substring(7);
        String username;

        try{
            username=jwtService.extractUsername(token);
        } catch (AuthenticationFailedException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
            return;
        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails;
            try{
                userDetails=userDetailsService.loadUserByUsername(username);
            } catch (Exception e){
                throw new AuthenticationFailedException("User not found for username: "+username,e);
            }


            try {
                if (jwtService.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authToken=
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,null,userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (AuthenticationFailedException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request,response);
    }







}
