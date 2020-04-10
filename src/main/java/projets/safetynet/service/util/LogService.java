package projets.safetynet.service.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LogService extends HandlerInterceptorAdapter {
	
    public static final Logger logger = LogManager.getLogger("safetynet");
    
	@Autowired
	private ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
    		Object handler) throws IOException {

    	String flatBody = ((CacheRequestService) request).toString();
        JsonNode nodeBody = objectMapper.readTree(flatBody);
        String indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);

    	LogService.logger.info( "Request method:" + request.getMethod() + " URL:"
					+ request.getRequestURL() + " query:" + request.getQueryString()
					+ " body:\n" + indentedBody);
		return true;
    }
 
    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response,
    		Object handler, Exception ex) throws IOException {
    	
    	String flatBody = ((CacheResponseService) response).toString();
        JsonNode nodeBody = objectMapper.readTree(flatBody);
        String indentedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nodeBody);

		LogService.logger.info( "Response method:" + request.getMethod() + " URL:"
				+ request.getRequestURL() + " status:" + response.getStatus()
				+ " body:\n" + indentedBody );
    }

}

