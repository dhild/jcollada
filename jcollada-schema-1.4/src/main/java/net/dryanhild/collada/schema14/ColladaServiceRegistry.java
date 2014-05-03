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

package net.dryanhild.collada.schema14;

import net.dryanhild.collada.schema14.structure.DocumentParser;
import net.dryanhild.collada.schema14.parser.XmlParser;
import net.dryanhild.collada.schema14.structure.ColladaDocument14;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class ColladaServiceRegistry {

    public static final String SERVICE_NAME = "JCollada-Schema-1.4";

    private final ServiceLocator locator;

    public ColladaServiceRegistry() {
        locator = getLocator();
    }

    private static synchronized ServiceLocator getLocator() {
        ServiceLocatorFactory factory = ServiceLocatorFactory.getInstance();

        if (factory.find(SERVICE_NAME) != null) {
            return factory.find(SERVICE_NAME);
        }

        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator(SERVICE_NAME);
        ServiceLocatorUtilities.enablePerThreadScope(locator);

        return locator;
    }

    public XmlParser<ColladaDocument14> getParser() {
        return locator.getService(DocumentParser.class);
    }

}
