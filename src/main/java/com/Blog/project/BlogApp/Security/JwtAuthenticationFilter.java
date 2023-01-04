package com.Blog.project.BlogApp.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      //1.get token
         String requestToken=request.getHeader("Authorization");

         //Bearer 2352523dgsg

        System.out.println(requestToken);

        String username=null;
        String token=null;
        if(requestToken !=null && requestToken.startsWith("Bearer"))
        {
            token=requestToken.substring(7);
            try {
                username=this.jwtTokenHelper.getUsernameFromToken(token);

            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Unale to get jwt token");
            }
            catch (ExpiredJwtException e)
            {
                System.out.println("jwt token has expired");
            }
            catch (MalformedJwtException e)
            {
                System.out.println("invalid jwt");
            }

        }
        else {
            System.out.println("jwt token does't begin with Bearer");
        }

        //once we get the token now validate
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token,userDetails))
            {
                //authentication karana he
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            else {
                System.out.println("invalid jwt token");
            }
        }
        else {
            System.out.println("userName is null or context is not null");
        }
        filterChain.doFilter(request,response);
    }
}
