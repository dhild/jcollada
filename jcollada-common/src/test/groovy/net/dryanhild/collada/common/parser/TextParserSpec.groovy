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

import java.util.function.BiConsumer
import java.util.function.Function

class TextParserSpec extends ParserSpec {

    TextParser textParser
    BiConsumer charConsumer
    Function valueConsumer

    def setup() {
        textParser = new TextParser('element', Void)
        charConsumer = { a, b -> return } as BiConsumer
        valueConsumer = Mock(Function)
        textParser.setCharConsumer(charConsumer, valueConsumer)
    }

    def 'can parse basic string data'() {
        setup:
        def xmlParser = createParser {
            it.element {
                mkp.yield('foobar')
            }
        }
        when:
        textParser.apply(xmlParser, null)

        then:
        1 * valueConsumer.apply({ it.toString().matches(/\s*foo\s*bar\s*/) })
    }

    def 'can parse with comments in between string data'() {
        setup:
        def xmlParser = createParser {
            it.element {
                mkp.yield('foo')
                mkp.comment('This is ignored')
                mkp.yield('bar')
            }
        }
        when:
        textParser.apply(xmlParser, null)

        then:
        1 * valueConsumer.apply({ it.toString().matches(/\s*foo\s*bar\s*/) })
    }

    def 'can parse with nested elements and comments'() {
        setup:
        def xmlParser = createParser {
            it.element {
                mkp.yield('foo')
                mkp.comment('This is ignored')
                mkp.yield('bar')
                nested()
                mkp.yield('baz')
            }
        }
        when:
        textParser.apply(xmlParser, null)

        then:
        1 * valueConsumer.apply({ it.toString().matches(/\s*foo\s*bar\s*baz\s*/) })
    }

}