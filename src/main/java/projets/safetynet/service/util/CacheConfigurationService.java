package projets.safetynet.service.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Service
@WebFilter(filterName = "CacheConfigurationService", urlPatterns = "/*")
public class CacheConfigurationService extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, 
    		HttpServletResponse httpServletResponse, FilterChain filterChain) 
    				throws ServletException, IOException {
		LogService.logger.debug( "doFilterInternal()" );
        CacheRequestService cachedRequest = new CacheRequestService(httpServletRequest);
        CacheResponseService cachedResponse = new CacheResponseService(httpServletResponse);
        filterChain.doFilter(cachedRequest, cachedResponse);
    }
    
}
