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

package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.collada.IncorrectFormatException;
import net.dryanhild.collada.NoSuchElementIdException;
import net.dryanhild.collada.schema14.geometry.DefaultLibrary;
import net.dryanhild.collada.schema14.geometry.data.GeometryResult;

import org.testng.annotations.Test;

@Test
public class DefaultLibraryTest {

    public void freshLibraryHasNoElements() {
        DefaultLibrary<GeometryResult> library = new DefaultLibrary<>();
        assertThat(library.getAll()).isEmpty();
    }

    public void elementCanBeAccessedById() {
        GeometryResult geometry = new GeometryResult("test-id", "test-name");
        DefaultLibrary<GeometryResult> library = new DefaultLibrary<>();
        library.add(geometry);

        assertThat(library.get("#test-id")).isEqualTo(geometry);
    }

    @Test(expectedExceptions = NoSuchElementIdException.class,
            expectedExceptionsMessageRegExp = "Element with uri #test-id2 does not exist")
    public void badIdThrowsException() {
        GeometryResult geometry = new GeometryResult("test-id", "test-name");
        DefaultLibrary<GeometryResult> library = new DefaultLibrary<>();
        library.add(geometry);

        library.get("#test-id2");
    }

    @Test(expectedExceptions = IncorrectFormatException.class,
            expectedExceptionsMessageRegExp = "Unable to create a URI from test uri")
    public void badURIThrowsException() {
        DefaultLibrary<GeometryResult> library = new DefaultLibrary<>();

        library.get("test uri");
    }

}
