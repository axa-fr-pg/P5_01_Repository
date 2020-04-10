package projets.safetynet.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class CacheOutputStreamService extends ServletOutputStream {

    private ServletOutputStream stream;
    private ByteArrayOutputStream cache;
    
    public CacheOutputStreamService(ServletOutputStream stream, ByteArrayOutputStream cache) {
		LogService.logger.debug( "new CacheOutputStreamService()" );
        this.stream = stream;
        this.cache = cache;
    }

    @Override
    public boolean isReady() {
		LogService.logger.debug( "isReady()" );
        return true;
    }

	@Override
	public void setWriteListener(WriteListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(int b) throws IOException {
		LogService.logger.trace( "write()" );
        stream.write(b);
        cache.write(b);
	}
	
}
