package kr.co.ictedu.geoulA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@PropertySource(value = {"classpath:/properties/config.properties"}, encoding = "UTF-8")
public class GeoulAApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GeoulAApplication.class);
	}
	
	public static void main(String[] args) {
		//SpringContainer가 시작및 종료
		SpringApplication.run(GeoulAApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer crosConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("Cros Allow Origin 실행");
				registry.addMapping("/**")
<<<<<<< HEAD

				.allowedOrigins("http://localhost:3001","http://localhost:3000","http://192.168.0.23:3001","http://192.168.0.23:3000")

=======
				.allowedOrigins("http://localhost:3001","http://localhost:3000","http://192.168.0.56:3001","http://192.168.0.56:3000")
>>>>>>> 3c2c07d36b62b82555444bc86be93a953e5ee61c
				.allowedHeaders("*")
				.allowCredentials(true)
				.allowedMethods("*").maxAge(3600);
			}
		};
	}
}
