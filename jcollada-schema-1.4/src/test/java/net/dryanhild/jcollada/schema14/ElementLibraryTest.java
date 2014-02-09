package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.IncorrectFormatException;
import net.dryanhild.jcollada.NoSuchElementIdException;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.schema14.geometry.ElementLibrary;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;

import org.testng.annotations.Test;

@Test
public class ElementLibraryTest {

    public void freshLibraryHasNoElements() {
        ElementLibrary<Geometry> library = new ElementLibrary<>();
        assertThat(library.getAll()).isEmpty();
    }

    public void elementCanBeAccessedById() {
        Geometry geometry = new GeometryResult("test-id", "test-name");
        ElementLibrary<Geometry> library = new ElementLibrary<>();
        library.add(geometry);

        assertThat(library.get("#test-id")).isEqualTo(geometry);
    }

    @Test(expectedExceptions = NoSuchElementIdException.class,
            expectedExceptionsMessageRegExp = "Element with uri #test-id2 does not exist")
    public void badIdThrowsException() {
        Geometry geometry = new GeometryResult("test-id", "test-name");
        ElementLibrary<Geometry> library = new ElementLibrary<>();
        library.add(geometry);

        library.get("#test-id2");
    }

    @Test(expectedExceptions = IncorrectFormatException.class,
            expectedExceptionsMessageRegExp = "Unable to create a URI from test uri")
    public void badURIThrowsException() {
        ElementLibrary<Geometry> library = new ElementLibrary<>();

        library.get("test uri");
    }

}
