/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.dryanhild.jcollada.spi;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import net.dryanhild.jcollada.ColladaLoader;

import org.junit.Test;

import com.sun.j3d.loaders.Scene;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class ColladaLoaderTest {

    public ColladaLoaderTest() {
    }

    /**
     * Test of load method, of class ColladaLoader.
     */
    @Test
    public void testLoad_String() throws IOException {
        ColladaLoader instance = new ColladaLoader();
        Scene scene = instance.load("resources/cube.dae");
        assertNotNull(scene);
    }

    /**
     * Test of load method, of class ColladaLoader.
     */
    @Test
    public void testLoad_URL() throws IOException {
        URL resourceURL = ClassLoader.getSystemResource("resources/cube.dae");
        ColladaLoader instance = new ColladaLoader();
        Scene scene = instance.load(resourceURL);
        assertNotNull(scene);
    }

    /**
     * Test of load method, of class ColladaLoader.
     */
    @Test
    public void testLoad_Reader() throws IOException {
        URL resourceURL = ClassLoader.getSystemResource("resources/cube.dae");
        Reader reader = new InputStreamReader(resourceURL.openStream());
        ColladaLoader instance = new ColladaLoader();
        Scene scene = instance.load(reader);
        assertNotNull(scene);
    }
}