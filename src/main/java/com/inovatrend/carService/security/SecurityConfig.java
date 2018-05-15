package com.inovatrend.carService.security;

import com.inovatrend.carService.service.UserManagerImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserManagerImplementation userManagerImplementation;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/create").hasAuthority("CREATE_USER")
                .antMatchers("/user/create/").hasAuthority("CREATE_USER")
                .antMatchers("/user/edit/*").hasAuthority("CREATE_USER")
                .antMatchers("/user/info/*").hasAuthority("CREATE_USER")
                .antMatchers("/user/list").hasAuthority("LIST")
                .antMatchers("/client/list").hasAuthority("LIST")
                .antMatchers("/client/info").hasAuthority("LIST")
                .antMatchers("/car/list").hasAuthority("LIST")
                .antMatchers("/car/info").hasAuthority("LIST")
                .antMatchers("/service/list").hasAuthority("LIST")
                .antMatchers("/service/info").hasAuthority("LIST")
                .antMatchers("/user/delete/*").hasAuthority("DELETE")
                .antMatchers("/client/delete/*").hasAuthority("DELETE")
                .antMatchers("/car/delete/*").hasAuthority("DELETE")
                .antMatchers("/service/delete/*").hasAuthority("DELETE")
                .antMatchers("/client/create/").hasAuthority("CREATE")
                .antMatchers("/car/create/").hasAuthority("CREATE")
                .antMatchers("/client/create").hasAuthority("CREATE")
                .antMatchers("/car/create").hasAuthority("CREATE")
                .antMatchers("/service/create/*").hasAuthority("CREATE")
                .antMatchers("/client/edit/*").hasAuthority("CREATE")
                .antMatchers("/car/edit/*").hasAuthority("CREATE")
                .antMatchers("/service/edit/*").hasAuthority("CREATE")
                .antMatchers("/user/newPassword").permitAll()
                .antMatchers("/user/newPassword/*").permitAll()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/")
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userManagerImplementation).passwordEncoder(passwordEncoder);
    }
}