package com.rud.app.config;

import com.rud.app.config.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // ?????????????????? ???????????????? ?? ???????????? ????????????
                //.loginPage("/login")
                //?????????????????? ???????????? ?????????????????? ?????? ????????????
                .successHandler(loginSuccessHandler)
                // ?????????????????? action ?? ?????????? ????????????
                .loginProcessingUrl("/login")
                // ?????????????????? ?????????????????? ???????????? ?? ???????????? ?? ?????????? ????????????
                .usernameParameter("username")
                .passwordParameter("password")
                // ???????? ???????????? ?? ?????????? ???????????? ????????
                .permitAll();

        http.logout()
                // ?????????????????? ???????????? ???????????? ????????
                .permitAll()
                // ?????????????????? URL ??????????????
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // ?????????????????? URL ?????? ?????????????? ??????????????
                .logoutSuccessUrl("/login")
                //???????????????? ?????????????????????????? ?????????????????????? (???? ?????????? ???????????????? ??????????????)
                .and().csrf().disable();

        http
                // ???????????? ???????????????? ?????????????????????? ?????????????????????? ?????? ???????????????????????????????? ??????????????????????????
                .authorizeRequests()
                //???????????????? ?????????????????????????? ???????????????? ????????
                .antMatchers("/login").anonymous()
                // ???????????????????? URL
                .antMatchers("/").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/users").hasRole("USER")
                .and().formLogin()
                .successHandler(loginSuccessHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

