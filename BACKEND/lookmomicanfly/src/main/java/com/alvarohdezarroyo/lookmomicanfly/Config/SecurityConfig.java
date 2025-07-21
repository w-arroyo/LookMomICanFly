package com.alvarohdezarroyo.lookmomicanfly.Config;

import com.alvarohdezarroyo.lookmomicanfly.Utils.Filters.JwtAuthenticationFilter;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Handlers.JwtLogoutHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final JwtLogoutHandler logoutHandler;

    public SecurityConfig(JwtLogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disables CSRF (useful for developing)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // enables CORS
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/users/register").permitAll(); // allows access without authentication
                    auth.requestMatchers("/api/users/login").permitAll();
                    auth.requestMatchers("/api/products/get/**").permitAll();
                    auth.requestMatchers("/api/products/find/**").permitAll();
                    auth.requestMatchers("/api/products/best-sellers").permitAll();
                    auth.requestMatchers("/api/products/sneakers/get/**").permitAll();
                    auth.requestMatchers("/api/products/collectibles/get/**").permitAll();
                    auth.requestMatchers("/api/products/accessories/get/**").permitAll();
                    auth.requestMatchers("/api/products/music/get/**").permitAll();
                    auth.requestMatchers("/api/products/skateboards/get/**").permitAll();
                    auth.requestMatchers("/api/products/clothing/get/**").permitAll();
                    auth.requestMatchers("/api/products/get-all-summary").permitAll();
                    auth.requestMatchers("/api/products/get-all-summary-by-category/**").permitAll();
                    auth.requestMatchers("/api/products/categories/").permitAll();
                    auth.requestMatchers("/api/products/categories/subcategories/**").permitAll();
                    auth.requestMatchers("/api/products/subcategories/**").permitAll();
                    auth.requestMatchers("/api/payments/update").permitAll();
                    auth.requestMatchers("/api/sales/update").permitAll();
                    auth.requestMatchers("/api/sales/get/last/**").permitAll();
                    auth.requestMatchers("/api/orders/update").permitAll();
                    auth.requestMatchers("/api/asks/get/lowest-ask/**").permitAll();
                    auth.requestMatchers("/api/asks/lowest-ask/**").permitAll();
                    auth.requestMatchers("/api/asks/get/product/**").permitAll();
                    auth.requestMatchers("/api/bids/get/product/**").permitAll();
                    auth.requestMatchers("/api/fees/shipping").permitAll();
                    auth.requestMatchers("/api/bids/get/operational").permitAll();
                    auth.requestMatchers("/api/bids/highest-bid/**").permitAll();
                    auth.requestMatchers("/api/shipping-options/get-all").permitAll();
                    auth.requestMatchers("/api/shipping-options/get-price/**").permitAll();
                    auth.requestMatchers("/api/phone-numbers/formats").permitAll();
                    auth.requestMatchers("/api/newsletter/**").permitAll();
                    //auth.anyRequest().permitAll(); this would allow any request without being authenticated
                    auth.anyRequest().authenticated(); // requires being logged in to access any request
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no sessions
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // filters token
                .formLogin(AbstractHttpConfigurer::disable) // disables login via html form
                .logout(logout -> logout
                        .logoutUrl("/api/users/logout")
                        .addLogoutHandler(logoutHandler) // handles the logout process (this class implements LogoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("Successful logout.");
                        }));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // allows requests from any origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // allowed methods
        configuration.setAllowedHeaders(List.of("*")); // allows headers of any type
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // applies to all it's routes
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
