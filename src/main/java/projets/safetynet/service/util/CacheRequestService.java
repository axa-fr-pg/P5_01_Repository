package projets.safetynet.service.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

public class CacheRequestService extends HttpServletRequestWrapper {

    private byte[] cache;

    public CacheRequestService(HttpServletRequest request) throws IOException {
        super(request);
		LogService.logger.debug( "new CachedRequestService()" );
        InputStream requestInputStream = request.getInputStream();
        cache = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
		LogService.logger.debug( "getInputStream()" );
        return new CacheInputStreamService(cache);
    }
    
    public String toString() {
		LogService.logger.debug( "toString()" );
        try {
            return new String(cache, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			LogService.logger.error( "toString() throws UnsupportedEncodingException" );
			return "";
		}
    }

    @Override
    public BufferedReader getReader() throws IOException {
		throw new UnsupportedOperationException();
    }
}
