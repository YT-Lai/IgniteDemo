package y.t.ignite.svr.two.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("y.t.ignite.svr.two.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo())
          .tags(new Tag("IgniteDemo", "ApacheIgniteDemo"));
    }
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "ignite-svr-two", 
	      "Ignite demo.", 
	      "v1.0", 
	      "Terms of service", 
	      new Contact("", "", ""), 
	      "License of API", "API license URL", Collections.emptyList());
	}
}
