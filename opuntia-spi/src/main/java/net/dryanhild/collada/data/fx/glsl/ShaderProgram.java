/*
 * The MIT License
 *
 * Copyright 2014 D. Ryan Hild <d.ryan.hild@gmail.com>.
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
package net.dryanhild.collada.data.fx.glsl;

import java.util.Set;
import net.dryanhild.collada.data.AddressableType;
import net.dryanhild.collada.data.fx.Sampler;

/**
 * Defines a shader program.
 *
 * A single instance of this class may end up using the same backend data as another shader.
 *
 */
public interface ShaderProgram extends AddressableType {

    Set<String> getAttributes();

    String getAttributeSemantic();

    Set<String> getUniforms();

    ParamType getUniformDescriptor(String uniform);

    byte[] getUniformData(String uniform);

    Set<Sampler> getSamplers();

    Set<ShaderStage> getStages();

    String getSource(ShaderStage stage);

}
