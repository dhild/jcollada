package net.dryanhild.jcollada.spi;

import java.io.Reader;

public interface ParsingContext {

    boolean isValidating();

    Reader getMainFileReader();
}
