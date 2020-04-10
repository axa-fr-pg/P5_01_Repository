package projets.safetynet.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import projets.safetynet.model.core.Data;
import projets.safetynet.model.core.Person;

@SpringBootTest
public class LogServiceTest {

	@Autowired
	private LogService service;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	CacheRequestService request;

	@Test
	void givenValidRequest_preHandle_returnsTrue() throws IOException
	{
		// GIVEN
		Person p = new Person("f1", "l1", "a1", "c1", 11111L, "t1", "e1");
		String s = objectMapper.writeValueAsString(p);
		when(request.toString()).thenReturn(s);
		// WHEN
		boolean result = service.preHandle(request, null, null);
		// THEN
		assertTrue(result);
	}

}
