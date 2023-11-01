package ksmart.mybatis.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
    		throws Exception {
		
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("SID");
		
		if(sessionId != null) {
			int sessionLevel = (int) session.getAttribute("SLEVEL");
			String requestURI = request.getRequestURI();
			
			
			if(sessionLevel==2) {
				//판매자
				if(	  requestURI.indexOf("/member/memberList")  > -1
				   || requestURI.indexOf("/member/modify") 		> -1
				   || requestURI.indexOf("/member/remove") 		> -1) {
					response.sendRedirect("/");
					return false;
				}
					
			}else if(sessionLevel==3) {
				//구매자
				if(	  requestURI.indexOf("/member/memberList")  > -1
				   || requestURI.indexOf("/member/modify") 		> -1
				   || requestURI.indexOf("/member/remove") 		> -1
				   || requestURI.indexOf("/goods/add") 			> -1
				   || requestURI.indexOf("/goods/modify") 		> -1
				   || requestURI.indexOf("/goods/remove") 		> -1) {
					response.sendRedirect("/");
					return false;
				}
			}
			
			return true;
		}
		
		response.sendRedirect("/member/login");
		return false;
		
    }

}
