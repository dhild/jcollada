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

import net.dryanhild.collada.schema14.data.ColladaDocument14;
import net.dryanhild.collada.schema14.parser.DocumentParser;
import net.dryanhild.collada.schema14.parser.XmlParser;
import org.glassfish.hk2.api.DescriptorFileFinder;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import java.io.IOException;

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

        ServiceLocator locator = factory.create(SERVICE_NAME);
        ServiceLocatorUtilities.enablePerThreadScope(locator);

        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        Populator populator = dcs.getPopulator();

        try {
            ClassLoader classLoader = ColladaServiceRegistry.class.getClassLoader();
            DescriptorFileFinder finder = new ClasspathDescriptorFileFinder(classLoader, SERVICE_NAME);
            populator.populate(finder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return locator;
    }

    public ParsingData getParsingData() {
        return locator.getService(ParsingData.class);
    }

    public XmlParser<ColladaDocument14> getDocumentParser() {
        return locator.getService(DocumentParser.class);
    }

}
