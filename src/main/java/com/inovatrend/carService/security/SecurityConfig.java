package com.inovatrend.carService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/client/create").hasRole("ADMIN")
                .antMatchers("/car/create").hasRole("ADMIN")
                .antMatchers("/service/create").hasRole("ADMIN")
                .antMatchers("/client/create/").hasRole("ADMIN")
                .antMatchers("/car/create/").hasRole("ADMIN")
                .antMatchers("/service/create/*").hasRole("ADMIN")
                .antMatchers("/client/edit/*").hasRole("ADMIN")
                .antMatchers("/car/edit/*").hasRole("ADMIN")
                .antMatchers("/service/edit/*").hasRole("ADMIN")
                .antMatchers("/client/delete/*").hasRole("ADMIN")
                .antMatchers("/car/delete/*").hasRole("ADMIN")
                .antMatchers("/service/delete/*").hasRole("ADMIN")
                .antMatchers("/client/**").hasAnyRole("USER, ADMIN")
                .antMatchers("/car/**").hasAnyRole("USER, ADMIN")
                .antMatchers("/service/**").hasAnyRole("USER, ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("pero").password("{noop}pero").roles("USER")
                .and()
                .withUser("matija").password("{noop}matija").roles("ADMIN")
        ;
    }
}