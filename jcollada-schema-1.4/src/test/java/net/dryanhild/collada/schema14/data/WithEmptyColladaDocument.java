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

package net.dryanhild.collada.schema14.data;

import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.schema14.ColladaLoaderService14;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class WithEmptyColladaDocument {

    private ColladaDocument14 document;

    @BeforeMethod
    public void resetDocument() {
        document = new ColladaDocument14();
    }

    @Test
    public void allIterablesAreEmpty() {
        assertThat(document.getNodes()).isEmpty();
        assertThat(document.getEffects()).isEmpty();
        assertThat(document.getMaterials()).isEmpty();
        assertThat(document.getGeometries()).isEmpty();
        assertThat(document.getVisualScenes()).isEmpty();
    }

    @Test
    public void versionSupportCanBeSet() {
        document.setVersion(ColladaLoaderService14.VERSION_1_4_0);
        assertThat(document.getVersion()).isEqualTo(ColladaLoaderService14.VERSION_1_4_0);
        document.setVersion(ColladaLoaderService14.VERSION_1_4_1);
        assertThat(document.getVersion()).isEqualTo(ColladaLoaderService14.VERSION_1_4_1);
    }

    @Test
    public void addNodeThenIsAccessibleById() {
        Node node = Mockito.mock(Node.class);
        Mockito.when(node.getId()).thenReturn("node-id");
        Mockito.when(node.getName()).thenReturn("node-name");
        document.addNode(node);
        assertThat(document.getNode("node-id").getName()).isEqualTo("node-name");
        assertThat(document.getNodes()).extracting("name").containsExactly("node-name");
    }

    @Test(expectedExceptions = NoSuchElementException.class, expectedExceptionsMessageRegExp = ".*non-existing-id.*")
    public void nonExistingNodeCannotBeAccessed() {
        document.getNode("non-existing-id");
    }

    @Test(expectedExceptions = NoSuchElementException.class, expectedExceptionsMessageRegExp = ".*non-existing-id.*")
    public void nonExistingGeometryCannotBeAccessed() {
        document.getGeometry("non-existing-id");
    }

    @Test(expectedExceptions = NoSuchElementException.class, expectedExceptionsMessageRegExp = ".*non-existing-id.*")
    public void nonExistingVisualSceneCannotBeAccessed() {
        document.getVisualScene("non-existing-id");
    }

    @Test(expectedExceptions = NoSuchElementException.class, expectedExceptionsMessageRegExp = ".*non-existing-id.*")
    public void nonExistingEffectCannotBeAccessed() {
        document.getEffect("non-existing-id");
    }

    @Test(expectedExceptions = NoSuchElementException.class, expectedExceptionsMessageRegExp = ".*non-existing-id.*")
    public void nonExistingMaterialCannotBeAccessed() {
        document.getMaterial("non-existing-id");
    }

}
