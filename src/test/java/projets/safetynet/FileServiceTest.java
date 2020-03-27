package projets.safetynet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import projets.safetynet.model.core.Data;
import projets.safetynet.service.FileService;

public class FileServiceTest {

	@Test
	void givenTestFile_getDataFromFile_returnsTestData()
	{
		// GIVEN
		String file = "src/test/resources/test.json";
		// WHEN
		Data data = FileService.getDataFromFile(file);
		// THEN
		assertNotNull(data);
		assertEquals(1, data.getPersons().size());
		assertEquals(1, data.getFirestations().size());
		assertEquals(1, data.getMedicalrecords().size());
	}
}
