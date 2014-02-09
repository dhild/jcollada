package net.dryanhild.jcollada.schema14.geometry;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dryanhild.jcollada.IncorrectFormatException;
import net.dryanhild.jcollada.NoSuchElementIdException;
import net.dryanhild.jcollada.data.Library;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;

import com.google.common.collect.ImmutableList;

public class GeometryLibrary implements Library<GeometryResult> {

    private final Map<URI, GeometryResult> elements;

    public GeometryLibrary() {
        elements = new HashMap<>();
    }

    @Override
    public void add(GeometryResult element) {
        try {
            elements.put(new URI("#" + element.getId()), element);
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from the id " + element.getId(), e);
        }
    }

    @Override
    public List<GeometryResult> getAll() {
        return ImmutableList.copyOf(elements.values());
    }

    @Override
    public GeometryResult get(String uri) {
        try {
            return get(new URI(uri));
        } catch (URISyntaxException e) {
            throw new IncorrectFormatException("Unable to create a URI from " + uri, e);
        }
    }

    @Override
    public GeometryResult get(URI uri) {
        GeometryResult geometry = elements.get(uri);
        if (geometry == null) {
            throw new NoSuchElementIdException("Element with uri " + uri + " does not exist");
        }
        return geometry;
    }

}
