package net.dryanhild.jcollada.schema14.scene.node;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.IncorrectFormatException;
import net.dryanhild.jcollada.NoSuchElementIdException;
import net.dryanhild.jcollada.schema14.scene.NodeLibrary;
import net.dryanhild.jcollada.schema14.scene.data.NodeResult;

import org.testng.annotations.Test;

@Test
public class NodeLibraryTest {

    public void freshLibraryHasNoElements() {
        NodeLibrary library = new NodeLibrary();
        assertThat(library.getAll()).isEmpty();
    }

    public void elementCanBeAccessedById() {
        NodeResult node = new NodeResult("test-name", "test-id", "NODE");
        NodeLibrary library = new NodeLibrary();
        library.add(node);

        assertThat(library.get("#test-id")).isEqualTo(node);
    }

    @Test(expectedExceptions = NoSuchElementIdException.class,
            expectedExceptionsMessageRegExp = "Element with uri #test-id2 does not exist")
    public void badIdThrowsException() {
        NodeResult node = new NodeResult("test-name", "test-id", "NODE");
        NodeLibrary library = new NodeLibrary();
        library.add(node);

        library.get("#test-id2");
    }

    @Test(expectedExceptions = IncorrectFormatException.class,
            expectedExceptionsMessageRegExp = "Unable to create a URI from test uri")
    public void badURIThrowsException() {
        NodeLibrary library = new NodeLibrary();

        library.get("test uri");
    }

}
