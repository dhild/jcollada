package net.dryanhild.jcollada.spi;

import java.io.Reader;

public interface ParsingContext {

    int getFlags();

    boolean isValidating();

    Reader getMainFileReader();

    <T> void storeElementById(String id, T element);

    <T> T getElementById(String id, Class<T> type);
}
