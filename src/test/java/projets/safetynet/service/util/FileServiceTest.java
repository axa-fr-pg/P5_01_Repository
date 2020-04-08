package projets.safetynet.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projets.safetynet.model.core.Data;

@SpringBootTest
public class FileServiceTest {

	@Autowired
	private FileService service;
	
	@Test
	void givenTestFile_getDataFromFile_returnsTestData() throws Exception
	{
		// GIVEN
		String file = "src/test/resources/test.json";
		// WHEN
		Data data = service.getDataFromFile(file);
		// THEN
		assertNotNull(data);
		assertEquals(23, data.getPersons().size());
		assertEquals(11, data.getFirestations().size());
		assertEquals(23, data.getMedicalrecords().size());
	}
}
