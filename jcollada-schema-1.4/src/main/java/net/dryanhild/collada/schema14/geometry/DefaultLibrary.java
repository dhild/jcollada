package net.dryanhild.collada.schema14.geometry;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.NoSuchElementIdException;
import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.data.Library;

import com.google.common.collect.ImmutableList;

public class DefaultLibrary<LibraryType extends AddressableType> implements Library<LibraryType> {

    private final Map<URI, LibraryType> elements;

    public DefaultLibrary() {
        elements = new HashMap<>();
    }

    @Override
    public void add(LibraryType element) {
        try {
            elements.put(new URI("#" + element.getId()), element);
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from the id " + element.getId(), e);
        }
    }

    @Override
    public List<LibraryType> getAll() {
        return ImmutableList.copyOf(elements.values());
    }

    @Override
    public LibraryType get(String uri) {
        try {
            return get(new URI(uri));
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from " + uri, e);
        }
    }

    @Override
    public LibraryType get(URI uri) {
        LibraryType element = elements.get(uri);
        if (element == null) {
            throw new NoSuchElementIdException("Element with uri " + uri + " does not exist");
        }
        return element;
    }

}
