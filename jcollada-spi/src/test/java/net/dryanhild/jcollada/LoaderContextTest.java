/*
 * Copyright (c) 2013, D. Ryan Hild <d.ryan.hild@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.dryanhild.jcollada;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point2d;
import javax.vecmath.Vector3d;

import org.junit.Test;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class LoaderContextTest {

    /**
     * Test of getObjectById method, of class LoaderContext.
     */
    @Test
    public void testGetObjectById() {
        Map<String, Object> testObjects = new HashMap<>();
        testObjects.put("test1", new String());
        testObjects.put("test3", new Vector3d());
        testObjects.put("test4", new Point2d());
        testObjects.put("test5", new Object());
        LoaderContext context = new LoaderContext(null, 0, false, null, null);
        for (String s : testObjects.keySet()) {
            context.addObjectById(s, testObjects.get(s));
        }

        for (String s : testObjects.keySet()) {
            Object expected = testObjects.get(s);
            assertEquals(expected, context.getObjectById("#" + s, expected.getClass()));
        }
    }
}