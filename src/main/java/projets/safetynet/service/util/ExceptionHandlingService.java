package projets.safetynet.service.util;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import projets.safetynet.dao.exception.FireStationNotFoundException;
import projets.safetynet.dao.exception.MedicalRecordNotFoundException;
import projets.safetynet.dao.exception.PersonNotFoundException;
import projets.safetynet.service.exception.ServerDataCorruptedException;

@ControllerAdvice
public class ExceptionHandlingService extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PersonNotFoundException.class)
	public final ResponseEntity<String> personNotFound(
			PersonNotFoundException exception, WebRequest request) {
		LogService.logger.error("notFound() PersonNotFoundException");
		return new ResponseEntity<>("Person not found !", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FireStationNotFoundException.class)
	public final ResponseEntity<String> fireStationNotFound(
			FireStationNotFoundException exception, WebRequest request) {
		LogService.logger.error("notFound() FireStationNotFoundException");
		return new ResponseEntity<>("FireStation not found !", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MedicalRecordNotFoundException.class)
	public final ResponseEntity<String> medicalRecordNotFound(
			MedicalRecordNotFoundException exception, WebRequest request) {
		LogService.logger.error("notFound() MedicalRecordNotFoundException");
		return new ResponseEntity<>("MedicalRecord not found !", HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected final ResponseEntity<Object> handleServletRequestBindingException(
			ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		LogService.logger.error("handleServletRequestBindingException() BAD_REQUEST");
		return new ResponseEntity<>("Wrong request !", HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		LogService.logger.error("handleTypeMismatch() BAD_REQUEST");
		return new ResponseEntity<>("Wrong request !", HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(ServerDataCorruptedException.class)
	public final ResponseEntity<String> dataCorrupted(
			ServerDataCorruptedException exception, WebRequest request) {
		LogService.logger.error("dataCorrupted() ServerDataCorruptedException");
		return new ResponseEntity<>("Data corrupted : fix input file and restart server !",
			HttpStatus.INTERNAL_SERVER_ERROR);
	}
/*
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> unknownError(Exception exception, WebRequest request) {
		LogService.logger.error("unknownError() Exception");
		return new ResponseEntity<>("Unknown error : revert to IT for investigation !",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
*/
}
