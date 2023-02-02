package com.mindhub.cerveceria.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


@Configuration
@EnableWebSecurity
public class AutorizacionWeb{


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

//      http.authorizeHttpRequests().requestMatchers("/rest/**").denyAll()
//          .requestMatchers(HttpMethod.POST,"/api/clientes/agregar","/api/clientes/confirmar-cuenta","/api/clientes/password-token","/clientes/reiniciar-contrasena").permitAll()
//          .requestMatchers(HttpMethod.POST,"/api/clientes/confirmar-cuenta").hasAuthority("CLIENT")
//          .requestMatchers(HttpMethod.PATCH,"api/cervezas/stock").hasAuthority("ADMIN")
//          .requestMatchers(HttpMethod.PUT,"api/cervezas/agregar").hasAuthority("ADMIN")
//          .requestMatchers(HttpMethod.PUT,"/api/cervezas/{id}").hasAuthority("ADMIN")
//          .requestMatchers(HttpMethod.PUT,"cervezas/alta/{id}").hasAuthority("ADMIN")
//          .requestMatchers(HttpMethod.DELETE, "cervezas/borrar/{id}").hasAuthority("ADMIN")
//          .requestMatchers("/api/cervezas/**").permitAll();


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();

     /*   http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendRedirect("/web/index.html"));*/

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) ->  clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }


}
