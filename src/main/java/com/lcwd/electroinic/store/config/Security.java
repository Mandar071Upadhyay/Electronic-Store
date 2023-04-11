package com.lcwd.electroinic.store.config;

import com.lcwd.electroinic.store.security.JwtAuthenticationEntryPoint;
import com.lcwd.electroinic.store.security.JwtAuthenticationFilter;
import com.sun.xml.bind.v2.runtime.output.Encoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security {
@Autowired
private UserDetailsService userDetailsService;
@Autowired
private JwtAuthenticationFilter authenticationFilter;
@Autowired
private JwtAuthenticationEntryPoint authenticationEntryPoint;

private    final String [] PUBLIC_URLS={
        "/swagger-ui/**",
        "/webjars/**",
        "/swagger-resources/**",
        "/v3/api-docs",
        "/v2/api-docs",

};

@Bean
public DaoAuthenticationProvider authenticationProvider()
{
    DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    System.out.println("DAOAuthicationPrivider is called");
return daoAuthenticationProvider;
}

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf()
            .disable()
            .cors()
            .disable()
            .authorizeRequests()
            .antMatchers("/auth/login")
            .permitAll()
            .antMatchers(HttpMethod.POST,"/user")
            .permitAll()
            .antMatchers(HttpMethod.DELETE,"/user/**").hasRole("ADMIN")
            .antMatchers(PUBLIC_URLS)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
    return builder.getAuthenticationManager();
}
@Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
