package net.dryanhild.jcollada.schema14.geometry;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dryanhild.jcollada.IncorrectFormatException;
import net.dryanhild.jcollada.NoSuchElementIdException;
import net.dryanhild.jcollada.data.AddressableType;
import net.dryanhild.jcollada.data.Library;

import com.google.common.collect.ImmutableList;

public class ElementLibrary<ElementType extends AddressableType> implements Library<ElementType> {

    private final Map<URI, ElementType> elements;

    public ElementLibrary() {
        elements = new HashMap<>();
    }

    @Override
    public void add(ElementType element) {
        try {
            elements.put(new URI("#" + element.getId()), element);
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from the id " + element.getId(), e);
        }
    }

    @Override
    public List<ElementType> getAll() {
        return ImmutableList.copyOf(elements.values());
    }

    @Override
    public ElementType get(String uri) {
        try {
            return get(new URI(uri));
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from " + uri, e);
        }
    }

    @Override
    public ElementType get(URI uri) {
        ElementType e = elements.get(uri);
        if (e == null) {
            throw new NoSuchElementIdException("Element with uri " + uri + " does not exist");
        }
        return e;
    }

}
