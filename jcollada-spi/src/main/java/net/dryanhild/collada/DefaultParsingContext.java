package net.dryanhild.collada;

import net.dryanhild.collada.spi.ParsingContext;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DefaultParsingContext implements ParsingContext {

    public static final int BUFFER_SIZE = 4096;
    public static final String BUFFER_SIZE_PROPERTY = "jcollada.parsing.buffersize";

    private final boolean validating;
    private final BufferedInputStream inputStream;
    private final Charset charset;
    private final String header;

    public DefaultParsingContext(boolean validating, InputStream input, Charset charset) throws IOException {
        this.validating = validating;
        this.charset = charset;

        String bufferSizeStr = System.getProperty(BUFFER_SIZE_PROPERTY, Integer.toString(BUFFER_SIZE));
        int bufferSize = Integer.valueOf(bufferSizeStr);

        inputStream = new BufferedInputStream(input, bufferSize);

        inputStream.mark(bufferSize);
        byte[] read = new byte[bufferSize];
        inputStream.read(read);
        header = new String(read, StandardCharsets.UTF_8);
        inputStream.reset();
    }

    @Override
    public boolean isValidating() {
        return validating;
    }

    @Override
    public CharSequence getMainFileHeader() {
        return header;
    }

    @Override
    public InputStream getMainFileInputStream() {
        return inputStream;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }
}
