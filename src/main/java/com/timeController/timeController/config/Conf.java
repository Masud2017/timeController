package com.timeController.timeController.config;

import java.util.Arrays;

import com.timeController.timeController.model.User;
import com.timeController.timeController.util.JWTAuthenticationEntryPoint;
import com.timeController.timeController.util.JWTRequestFilter;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class Conf extends WebSecurityConfigurerAdapter{

    @Autowired
    JWTRequestFilter jwtRequestFilter;
    @Autowired
    JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
   

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationManager();   
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/api/v1/authenticate","/api/v1/registration","/api/v1/testJson").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
    }

    @Autowired
    public void configuraGlobal (AuthenticationManagerBuilder auth) throws Exception  {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    // @Bean
    // public CorsFilter corsFilter() {
    //     CorsConfiguration corsConfiguration = new CorsConfiguration();
    //     corsConfiguration.setAllowCredentials(true);
    //     corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); 
    //     corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control,     Allow-Origin", "Content-Type", "Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Header" )); // this allows all headers
    //     corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "Access-Control-Request-Allow-Origin", "Access-Control-Allow-Credentials"));
    //     corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //     UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    //     urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
    //     return new CorsFilter();
    // }


 
}
