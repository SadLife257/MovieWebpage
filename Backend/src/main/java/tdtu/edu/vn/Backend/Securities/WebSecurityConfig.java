package tdtu.edu.vn.Backend.Securities;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import tdtu.edu.vn.Backend.Utilities.JWT.JwtEntryPoint;
import tdtu.edu.vn.Backend.Utilities.JWT.JwtFilter;
import tdtu.edu.vn.Backend.Utilities.JWT.JwtForbidden;
import tdtu.edu.vn.Backend.Utilities.JWT.JwtUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    JwtUserService userService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    private JwtForbidden jwtForbidden;

    @Bean
	public JwtFilter authenticationJwtTokenFilter() {
		return new JwtFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
        		.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> 
                	   exception.authenticationEntryPoint(jwtEntryPoint)
                				.accessDeniedHandler(jwtForbidden))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> 
                		auth.requestMatchers("/api/auth/**", "/error").permitAll()
                			.requestMatchers("/api/admin/**").hasRole("ADMIN")
                			.anyRequest().authenticated());

		http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
