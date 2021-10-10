package com.example.jwtsecuritydatabase.Security;

import com.example.jwtsecuritydatabase.Filter.CustomAuthenticationFilter;
import com.example.jwtsecuritydatabase.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
//@CrossOrigin("*")
@RequiredArgsConstructor //for dependency injection
public class SecurityConfig extends WebSecurityConfigurerAdapter { // websecurity class override certain method and tell spring how to manage the user in the security of the application

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @CrossOrigin("*")
    protected void configure(HttpSecurity http) throws Exception { // configure token
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login", "/api/token/refresh/**", "/api/user/save","/api/posts/all","api/posts/get/{id}", "/api/users").permitAll();
        //http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER"); //example only
        http.authorizeRequests().antMatchers(POST, "/api/posts/create").hasAnyAuthority("ROLE_ADMIN"); //example only
        http.authorizeRequests().anyRequest().authenticated(); // allow everyone to access this application
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        // line above check username and password and give token if correct and reject if incorrect username and password
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
