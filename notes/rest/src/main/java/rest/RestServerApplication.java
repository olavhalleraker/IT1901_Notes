package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Contains the starting method for the server application. Configures Springs ObjectMapper for JSON
 * to parse classes from the core-module.
 */
@SpringBootApplication
public class RestServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(RestServerApplication.class, args);
  }

  /**
   * Configures Cross-Origin Resource Sharing (CORS) settings for the application.
   *
   * @return registry The CorsRegistry to configure.
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/notes/**").allowedOrigins("http://localhost:3000")
            .allowedMethods("PUT", "POST", "GET");
      }
    };
  }
}
