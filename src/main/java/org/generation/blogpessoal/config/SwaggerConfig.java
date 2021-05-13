package org.generation.blogpessoal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket estoqueApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.generation.blogpessoal.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(metaInfo());
    }

    public ApiInfo metaInfo(){

        return new ApiInfoBuilder()
                .title("Blog Pessoal API REST")
                .description("Este é um projeto back-end voltado para um blog pessoal, com ele você pode desabafar problemas, registrar uma bela conquista e guardar todos os momentos que você quiser da sua vida em formato de texto.")
                .version("1.0")
                .license("MIT License")
                .licenseUrl("https://github.com/Anderson815/Blog_Pessoal_-_API_REST/blob/3a908c332bf467de5839252cde0c9d84eec499f3/LICENSE")
                .contact(contact())
                .build();
    }
    
    public Contact contact() {
    	return new Contact("Anderson Correia de Souza",
    			"https://www.linkedin.com/in/anderson-correia",
    			"anderson_souza33@outlook.com.br");
    }
	
}
