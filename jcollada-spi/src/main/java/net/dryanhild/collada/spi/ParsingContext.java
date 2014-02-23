package net.dryanhild.collada.spi;

import java.io.Reader;

public interface ParsingContext {

    boolean isValidating();

    Reader getMainFileReader();
}
