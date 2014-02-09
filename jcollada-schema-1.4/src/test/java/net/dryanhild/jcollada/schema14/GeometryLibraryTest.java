package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.IncorrectFormatException;
import net.dryanhild.jcollada.NoSuchElementIdException;
import net.dryanhild.jcollada.schema14.geometry.GeometryLibrary;
import net.dryanhild.jcollada.schema14.geometry.data.GeometryResult;

import org.testng.annotations.Test;

@Test
public class GeometryLibraryTest {

    public void freshLibraryHasNoElements() {
        GeometryLibrary library = new GeometryLibrary();
        assertThat(library.getAll()).isEmpty();
    }

    public void elementCanBeAccessedById() {
        GeometryResult geometry = new GeometryResult("test-id", "test-name");
        GeometryLibrary library = new GeometryLibrary();
        library.add(geometry);

        assertThat(library.get("#test-id")).isEqualTo(geometry);
    }

    @Test(expectedExceptions = NoSuchElementIdException.class,
            expectedExceptionsMessageRegExp = "Element with uri #test-id2 does not exist")
    public void badIdThrowsException() {
        GeometryResult geometry = new GeometryResult("test-id", "test-name");
        GeometryLibrary library = new GeometryLibrary();
        library.add(geometry);

        library.get("#test-id2");
    }

    @Test(expectedExceptions = IncorrectFormatException.class,
            expectedExceptionsMessageRegExp = "Unable to create a URI from test uri")
    public void badURIThrowsException() {
        GeometryLibrary library = new GeometryLibrary();

        library.get("test uri");
    }

}
