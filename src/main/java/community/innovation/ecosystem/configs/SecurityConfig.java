package community.innovation.ecosystem.configs;

import community.innovation.ecosystem.security.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true, jsr250Enabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserCredentialService userCredentialService;

    @Autowired
    public SecurityConfig(UserCredentialService userCredentialService) {
        this.userCredentialService = userCredentialService;
    }

    //Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userCredentialService);
    }

    //Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/users").permitAll()
                .antMatchers("/api/confirmRegistration").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .httpBasic()
                .and().sessionManagement().disable();
    }
    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }
}
