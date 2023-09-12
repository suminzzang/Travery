package com.ssafy.travery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 스프링 실행시 설정파일
@EnableSwagger2 // Swagger2를 사용
@SuppressWarnings("unchecked") // warning 제거
public class SwaggerConfiguration {

//	Swagger-UI 2.x 확인
//	http://localhost[:8080]/{your-app-root}/swagger-ui.html
//	Swagger-UI 3.x 확인
//	http://localhost[:8080]/{your-app-root}/swagger-ui/index.html

	private String version = "V1";
	private String title = "Travery API " + version;

	private ApiInfo apiInfo() {
		String descript = "Travery Vuejs API Reference for Developers<br>";
		descript += "<img src=\"http://localhost:9999/travery/static/assets/img/ssafy_logo.png\">";
		return new ApiInfoBuilder().title(title).description(descript)
//				.termsOfServiceUrl("https://edu.ssafy.com")
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com")).license("SSAFY License")
				.licenseUrl("ssafy@ssafy.com").version("1.0").build();
	}

	// API마다 구분짓기 위한 설정.
	@Bean
	public Docket allApi() {
		return getDocket("전체", Predicates.or(PathSelectors.regex("/*.*")));
	}
	
	@Bean
	public Docket userApi() {
		return getDocket("회원", Predicates.or(PathSelectors.regex("/users.*")));
	}

	@Bean
	public Docket adminBoardApi() {
		return getDocket("공지사항", Predicates.or(PathSelectors.regex("/adminboard.*")));
	}

	@Bean
	public Docket userBoardApi() {
		return getDocket("자유게시판", Predicates.or(PathSelectors.regex("/userboard.*")));

	}
	
	@Bean
	public Docket fileApi() {
		return getDocket("파일", Predicates.or(PathSelectors.regex("/file.*")));

	}
	
	@Bean
	public Docket hotPlaceBoardApi() {
		return getDocket("핫플레이스", Predicates.or(PathSelectors.regex("/hotplaceboard.*")));

	}
	
	@Bean
	public Docket tripSearchApi() {
		return getDocket("관광지검색", Predicates.or(PathSelectors.regex("/tripsearch.*")));

	}
	
	@Bean
	public Docket tripPlanBoardApi() {
		return getDocket("여행계획", Predicates.or(PathSelectors.regex("/tripplanboard.*")));

	}
	
	@Bean
	public Docket qnaBoardApi() {
		return getDocket("qna 질문글", Predicates.or(PathSelectors.regex("/qnaboard.*")));
	}

	@Bean
	public Docket areaCodeApi() {
		return getDocket("지역코드", Predicates.or(PathSelectors.regex("/areacode.*")));
	}
	
	@Bean
	public Docket mailCheckApi() {
		return getDocket("메일인증", Predicates.or(PathSelectors.regex("/mailcheck.*")));
	}

	public Docket getDocket(String groupName, Predicate<String> predicate) {
		return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.travery.controller")).paths(predicate)
				.apis(RequestHandlerSelectors.any()).build();
	}

	// swagger ui 설정.
	@Bean
	public UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().displayRequestDuration(true).validatorUrl("").build();
	}

}
