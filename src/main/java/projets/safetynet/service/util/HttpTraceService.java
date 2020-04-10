package projets.safetynet.service.util;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class HttpTraceService {

	@Bean
	public HttpTraceRepository htttpTraceRepository()
	{
		LogService.logger.debug( "htttpTraceRepository()" );
		return new InMemoryHttpTraceRepository();
	}
	
}
