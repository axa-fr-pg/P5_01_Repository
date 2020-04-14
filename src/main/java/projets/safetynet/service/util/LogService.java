package projets.safetynet.service.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LogService extends HandlerInterceptorAdapter {
	
    public static final Logger logger = LogManager.getLogger("safetynet");
    
	@Autowired
	private ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
    		Object handler) {
    	
    	String flatBody = "";
    	try {
    		flatBody = ((CacheRequestService) request).toString();
    	}
    	catch (ClassCastException e) {
	    	LogService.logger.error( "preHandle() throws ClassCastException" );   		
    	}
        JsonNode nodeBody;
        String indentedBody = "Could not be read due to JsonProcessingException";
		try {
			nodeBody = objectMapper.readTree(flatBody);
	        indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);
		} catch (JsonProcessingException e) {
	    	LogService.logger.error( "preHandle() throws JsonProcessingException" );
		}

    	LogService.logger.info( "Request method:" + request.getMethod() + " URL:"
					+ request.getRequestURL() + " query:" + request.getQueryString()
					+ " body:\n" + indentedBody);
		return true;
    }
 
    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response,
    		Object handler, Exception ex)  {
    	
    	String flatBody = "";
    	try {
    		flatBody = ((CacheResponseService) response).toString();
    	}
    	catch (ClassCastException e) {
	    	LogService.logger.error( "afterCompletion() throws ClassCastException" );   		
    	}
        JsonNode nodeBody;
        String indentedBody = "Could not be read due to JsonProcessingException";
		try {
			nodeBody = objectMapper.readTree(flatBody);
	        indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);
		} catch (JsonProcessingException e) {
	    	LogService.logger.error( "afterCompletion() throws JsonProcessingException" );
		}

		LogService.logger.info( "Response method:" + request.getMethod() + " URL:"
				+ request.getRequestURL() + " status:" + response.getStatus()
				+ " body:\n" + indentedBody );
    }

}

