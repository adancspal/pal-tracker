package io.pivotal.pal.tracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private boolean forceHttpsSecurity;

    public SecurityConfiguration(@Value("${SECURITY_FORCE_HTTPS}") boolean forceHttpsSecurity) {
        this.forceHttpsSecurity = forceHttpsSecurity;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (forceHttpsSecurity) {
            http.requiresChannel()
                    .anyRequest()
                    .requiresSecure();
        }

        http.authorizeRequests()
                .antMatchers("/**")
                .hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER");
    }
}
