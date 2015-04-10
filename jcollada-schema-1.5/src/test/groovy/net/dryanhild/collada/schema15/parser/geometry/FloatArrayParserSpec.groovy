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

package net.dryanhild.collada.schema15.parser.geometry

import net.dryanhild.collada.schema15.data.geometry.RawFloats
import net.dryanhild.collada.schema15.parser.ParserSpec

class FloatArrayParserSpec extends ParserSpec {

    def setup() {
        parser = new FloatArrayParser()
    }

    def 'can parse name'() {
        when:
        RawFloats result = runParser { it.float_array(name: 'test') }

        then:
        result.name == 'test'
    }

    def 'can parse id'() {
        when:
        RawFloats result = runParser { it.float_array(id: 'test') }

        then:
        result.id == 'test'
    }

    def 'can parse count'() {
        when:
        RawFloats result = runParser { it.float_array(count: 5) }

        then:
        result.count == 5
    }

    def 'can parse digits'() {
        when:
        RawFloats result = runParser { it.float_array(digits: 5) }

        then:
        result.digits == 5
    }

    def 'no digits is null'() {
        when:
        RawFloats result = runParser { it.float_array(count: 5) }

        then:
        result.digits == null
    }

    def 'can parse magnitude'() {
        when:
        RawFloats result = runParser { it.float_array(magnitude: 5) }

        then:
        result.magnitude == 5
    }

    def 'no magnitude is null'() {
        when:
        RawFloats result = runParser { it.float_array(count: 5) }

        then:
        result.magnitude == null
    }

    def 'can parse values'() {
        when:
        RawFloats result = runParser {
            it.float_array(id: 'test') {
                mkp.yield '1.1 2.2 3.333'
            }
        }

        then:
        result.floats.toArray().toList() == [1.1f, 2.2f, 3.333f]
    }

}
