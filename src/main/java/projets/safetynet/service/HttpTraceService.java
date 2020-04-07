package projets.safetynet.service;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class HttpTraceService {

	@Bean
	public HttpTraceRepository htttpTraceRepository()
	{
	  return new InMemoryHttpTraceRepository();
	}
	
}
