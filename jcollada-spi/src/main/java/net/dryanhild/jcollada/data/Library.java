package net.dryanhild.jcollada.data;

import java.net.URI;
import java.util.List;

public interface Library<LibraryType extends AddressableType> {

    void add(LibraryType geometry);

    List<LibraryType> getAll();

    LibraryType get(String id);

    LibraryType get(URI uri);

}
