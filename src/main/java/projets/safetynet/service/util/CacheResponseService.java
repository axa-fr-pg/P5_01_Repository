package projets.safetynet.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CacheResponseService extends HttpServletResponseWrapper {

    private ByteArrayOutputStream stream;

    public CacheResponseService(HttpServletResponse response) {
        super(response);
		LogService.logger.debug( "new CacheResponseService()" );
		stream = new ByteArrayOutputStream();
    }

    public String toString() {
		LogService.logger.debug( "toString()" );
        try {
			return stream.toString("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			LogService.logger.error( "toString() throws UnsupportedEncodingException" );
			return "";
		}
    }

    private static Scanner scan = new Scanner(System.in, "ISO-8859-1");
    	
    
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
		LogService.logger.debug( "getOutputStream()" );
        return new CacheOutputStreamService(super.getOutputStream(), stream);
    }

}
