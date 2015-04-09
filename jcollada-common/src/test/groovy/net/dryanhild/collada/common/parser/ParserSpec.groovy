/*
 * Copyright (c) 2015 D. Ryan Hild <d.ryan.hild@gmail.com>
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
 *
 */

package net.dryanhild.collada.common.parser
import groovy.xml.MarkupBuilder
import org.xmlpull.v1.XmlPullParser
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.util.function.Function

abstract class ParserSpec extends Specification {

    Function parser

    XmlPullParser createDocumentParser(String data) {
        InputStream input = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))
        XmlParser.createPullParser(false, input, StandardCharsets.UTF_8)
    }

    XmlPullParser createParser(Closure data) {
        def sw = new StringWriter()
        def xml = new MarkupBuilder(sw)
        xml.COLLADA(xmlns: 'http://www.collada.org/2008/03/COLLADASchema', version: '1.5.0') {
            data(xml)
        }
        def xmlParser = createDocumentParser(sw.toString())
        while (xmlParser.eventType != XmlPullParser.START_TAG || xmlParser.name == 'COLLADA') {
            xmlParser.nextTag()
        }
        xmlParser
    }

    def runParser(Closure data) {
        def xmlParser = createParser(data)
        parser.apply(xmlParser)
    }

}
