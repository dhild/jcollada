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
