package cn.itrip.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * . 创建配置类对 swagger进行基本的配置
 */
@ComponentScan(basePackages = {"cn.itrip.controller"})
@EnableSwagger2
@Configuration
public class Swagger extends WebMvcConfigurationSupport {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                /*
                访问swagger
                http://localhost:端口号/swagger-ui.html
                * */
                .title("爱旅行--Sear系统Apis")
                .description("后台测试接口")
                .version("1.0")
                .build();
    }
}
