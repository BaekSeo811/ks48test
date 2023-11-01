package ksmart.mybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ksmart.mybatis.interceptor.CommonInterceptor;
import ksmart.mybatis.interceptor.LoginInterceptor;

/**
 * @Configuration : 스프링 프로젝트 내에 설정에 관련된 빈을 선언할 때 사용됨
 * WebMvcConfigurer 클래스 : web에 관련된 모든 설정들이 추상화되어있는 클래스
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	//Dependency Injection(의존성주입)
	private final CommonInterceptor commonInterceptor;
	private final LoginInterceptor loginIntercepter;
	
	public WebConfig(CommonInterceptor commonInterceptor, LoginInterceptor loginInterceptor) {
		this.commonInterceptor = commonInterceptor;
		this.loginIntercepter = loginInterceptor;
	}
	
	
	/**
	 * WebMvcConfigurer의 addInterceptors 메소드를 오버라이드하여 생성한 interceptor를 추가
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		
		//정적 리소스 주소는 배제한 나머지 동적 리소스 주소만 설정
		registry.addInterceptor(commonInterceptor).addPathPatterns("/**")
				.excludePathPatterns("/css/**").excludePathPatterns("/js/**")
				.excludePathPatterns("/favicon.ico");
		
		//로그인 검증 interceptor
//		
//		registry.addInterceptor(loginIntercepter).addPathPatterns("/**")
//				.excludePathPatterns("/")
//				.excludePathPatterns("/css/**")
//				.excludePathPatterns("/js/**")
//				.excludePathPatterns("/favicon.ico")
//				.excludePathPatterns("/member/addMember")
//				.excludePathPatterns("/member/idCheck")
//				.excludePathPatterns("/member/login")
//				.excludePathPatterns("/member/logout");
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
