package com.momenting.momentingapp.config;

import com.momenting.momentingapp.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //http 시큐리티 빌더
      http.cors()
        .and()
        .csrf().disable() //crsf는 사용하지 않으므로 disable
        .httpBasic().disable() // 토큰을 사용하므로 베이직 disable
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session 기반이 아님을 선언
        .and()
        .authorizeRequests().antMatchers("/", "/auth/**").permitAll() // "/" 와 "/auth/**"경로는 인증 안해도 됨을 선언
        .anyRequest().authenticated(); //나머지 경로는 모두 인증 해야함

      // 매 리퀘스트마다 cors필터 실행 후 jwtAuthenticationFilter 실행
      http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    }
}
