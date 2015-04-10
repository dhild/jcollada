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

package net.dryanhild.collada.schema15;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Service;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Service
public class PerParseContext implements Context<PerParse> {

    private InheritableThreadLocal<Map<ActiveDescriptor<?>, Object>> localContext = new InheritableThreadLocal<>();

    public void startContext() {
        localContext.set(new HashMap<>());
    }

    public void endContext() {
        localContext.remove();
    }

    public Map<ActiveDescriptor<?>, Object> getCurrentContext() {
        Map<ActiveDescriptor<?>, Object> context = localContext.get();
        assert context != null : "No current parsing context!";
        return context;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return PerParse.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> U findOrCreate(ActiveDescriptor<U> activeDescriptor, ServiceHandle<?> root) {
        Map<ActiveDescriptor<?>, Object> context = getCurrentContext();
        Object value = context.get(activeDescriptor);
        if (value == null) {
            value = activeDescriptor.create(root);
            context.put(activeDescriptor, value);
        }
        return (U) value;
    }

    @Override
    public boolean containsKey(ActiveDescriptor<?> descriptor) {
        return getCurrentContext().containsKey(descriptor);
    }

    @Override
    public void destroyOne(ActiveDescriptor<?> descriptor) {
        getCurrentContext().remove(descriptor);
    }

    @Override
    public boolean supportsNullCreation() {
        return true;
    }

    @Override
    public boolean isActive() {
        return localContext.get() != null;
    }

    @Override
    public void shutdown() {
        endContext();
    }
}
