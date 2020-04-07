package projets.safetynet.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Service
public class LogService extends HandlerInterceptorAdapter {
	
    public static final Logger logger = LogManager.getLogger("safetynet");
    
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
    		Object handler) {
			LogService.logger.info( "Request method:" + request.getMethod() + " URL:"
					+ request.getRequestURL() + " query:" + request.getQueryString());
         return true;
    }
 
    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response,
    		Object handler, Exception ex) {
		LogService.logger.info( "Response method:" + request.getMethod() + " URL:"
				+ request.getRequestURL() + " status:" + response.getStatus() );
    }

}

