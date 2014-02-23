package net.dryanhild.collada.hk2spi;

import java.net.URL;

/**
 * Defines a context for loading COLLADA files.
 *
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public interface ParsingContext {

    boolean isValidating();

    byte[] getMainFileHeader();

    URL getMainFileURL();

}