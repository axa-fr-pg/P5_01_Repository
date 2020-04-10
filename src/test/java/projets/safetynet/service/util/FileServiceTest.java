package projets.safetynet.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projets.safetynet.model.core.Data;
import projets.safetynet.service.exception.InvalidDeleteFireStationRequestException;
import projets.safetynet.service.exception.ServerDataCorruptedException;

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
	
	@Test
	void givenMissingFile_getDataFromFile_throwsServerDataCorruptedException() throws Exception
	{
		// GIVEN
		String file = "does not exist";
		// WHEN & THEN
		assertThrows(ServerDataCorruptedException.class, () -> {
			service.getDataFromFile(file);
		});
	}
	
}
