package inventory.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.NonNull;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                        .allowCredentials(true)
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowedOrigins("https://spring-inventory-front.vercel.app");
            }
        };
    }

    // @Bean
    // public CorsFilter corsFilter() {
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();

    // CorsConfiguration config = new CorsConfiguration();
    // config.setAllowCredentials(true);
    // config.setAllowedOrigins(Arrays.asList(
    // "http://localhost:5173"));
    // config.setAllowedHeaders(Arrays.asList("*"));
    // config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
    // "OPTIONS"));

    // source.registerCorsConfiguration("/**", config);
    // return new CorsFilter(source);
    // }
}