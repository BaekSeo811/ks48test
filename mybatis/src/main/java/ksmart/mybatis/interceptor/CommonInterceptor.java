package ksmart.mybatis.interceptor;

import java.util.Set;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ksmart.mybatis.dto.Member;

@Component
public class CommonInterceptor implements HandlerInterceptor{
	
	private static final Logger log = LoggerFactory.getLogger(Member.class);

	/**
	 * PreHandle : Controller 진입 전 실행되는 메소드
	 * HandlerMapping이 핸들러 객체를 결정한 후 HandlerAdapter가 핸들러를 호출하기 전에 호출되는 메소드
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
		throws Exception{
		//요청 파라미터
		Set<String> parameterKeySet = request.getParameterMap().keySet();
		
		//memberId="id001", memberPw="pw001" 같은 형식으로 출력하기 위해 Joiner 사용
		StringJoiner param = new StringJoiner(", ");
		
		//파라미터 내용 문자열 연결
		for(String key : parameterKeySet) {
			param.add(key + ": "+request.getParameter(key));
		}
		
		//공통 액세스 로그 처리
		log.info("ACCESS INFO=======================================START");
		log.info("PORT		:::::		{}",request.getLocalPort());
		log.info("ServerName		:::::		{}",request.getServerName());
		log.info("Method		:::::		{}",request.getMethod());
		log.info("URI		:::::		{}",request.getRequestURI());
		log.info("Client IP		:::::		{}",request.getRemoteAddr());
		log.info("Parameter		:::::		{}",param);
		log.info("ACCESS INFO=======================================END");
		
		return HandlerInterceptor.super.preHandle(request, null, handler);
	}
	
	/**
	 * PostHandle : Controller 메소드 실행 직후 실행되는 메소드
	 * HandlerAdapter가 실제로 핸들러를 호출한 후 DispatcherServlet이 뷰를 렌더링하기 전에 호출되는 메소드 

	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) 
			throws Exception{
				HandlerInterceptor.super.preHandle(request, null, handler);
	}
	
	/*
	 * afterCompletion : 뷰 렌더링 후에 호출되는 메소드
	 * DispatcherServlet이 뷰를 렌더링하기 전에 호출되는 메소드
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		
	}

}
