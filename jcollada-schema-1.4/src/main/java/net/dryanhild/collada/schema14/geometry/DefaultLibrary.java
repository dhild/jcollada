/*
 * Copyright (c) 2014 D. Ryan Hild <d.ryan.hild@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
