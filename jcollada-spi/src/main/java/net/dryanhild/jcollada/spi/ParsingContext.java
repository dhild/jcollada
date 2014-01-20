package net.dryanhild.jcollada.spi;

import java.io.Reader;

public interface ParsingContext {

    int getFlags();

    boolean isValidating();

    Reader getMainFileReader();
}
