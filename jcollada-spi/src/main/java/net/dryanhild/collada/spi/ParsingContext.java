package net.dryanhild.collada.spi;

import java.io.InputStream;
import java.nio.charset.Charset;

public interface ParsingContext {

    boolean isValidating();

    CharSequence getMainFileHeader();

    InputStream getMainFileInputStream();

    Charset getCharset();
}
