package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;
import net.dryanhild.jcollada.NoSuchElementIdException;
import net.dryanhild.jcollada.data.geometry.Geometry;
import net.dryanhild.jcollada.data.scene.Node;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ColladaScene14Test {

    private ColladaScene14 scene;

    @BeforeMethod
    public void resetScene() {
        scene = new ColladaScene14();
    }

    public void noGeometriesMeansNoElementById() {
        try {
            scene.getElementById("my-geometry", Geometry.class);
        } catch (NoSuchElementIdException e) {
            assertThat(e).hasMessageContaining("my-geometry").hasMessageContaining("does not exist");
        }
    }

    public void noNodesMeansNoElementById() {
        try {
            scene.getElementById("my-node", Node.class);
        } catch (NoSuchElementIdException e) {
            assertThat(e).hasMessageContaining("my-node").hasMessageContaining("does not exist");
        }
    }

    public void badClassInElementByIdThrowsException() {
        try {
            scene.getElementById("anyting", Object.class);
        } catch (NoSuchElementIdException e) {
            assertThat(e).hasMessageContaining("type " + Object.class.getName());
        }
    }

}
