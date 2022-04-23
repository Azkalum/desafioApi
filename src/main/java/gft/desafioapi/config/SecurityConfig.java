package gft.desafioapi.config;

import gft.desafioapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioService usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

      http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/starter/**", "/categoria/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/starter", "/categoria").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/starter/**", "/categoria/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/usuario/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

    }
}