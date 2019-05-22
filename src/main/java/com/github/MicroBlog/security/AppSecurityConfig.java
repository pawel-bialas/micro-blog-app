package com.github.MicroBlog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    public AppSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()

                .authorizeRequests()
                .antMatchers("/welcome/**", "/register/**","/login/**","/logout/**")
                .permitAll()
//                .antMatchers(HttpMethod.PATCH).hasRole("USER")
//                .antMatchers(HttpMethod.PATCH).hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE).hasRole("USER")
//                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")

                .antMatchers("/admin/**")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)

                .and()
                .csrf()
                .disable();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
