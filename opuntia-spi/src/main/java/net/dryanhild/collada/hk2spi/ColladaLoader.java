package net.dryanhild.collada.hk2spi;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import org.jvnet.hk2.annotations.Contract;

/**
 * Defines a service to load COLLADA files.
 *
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
@Contract
public interface ColladaLoader {

    Iterable<VersionSupport> getColladaVersions();

    boolean canLoad(ParsingContext context);

    ColladaDocument load(ParsingContext context);

}
