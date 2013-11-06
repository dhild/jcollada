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
package net.dryanhild.jcollada.schema141.geometry;

import net.dryanhild.jcollada.schema141.gen.Accessor;
import net.dryanhild.jcollada.schema141.gen.BoolArray;
import net.dryanhild.jcollada.schema141.gen.FloatArray;
import net.dryanhild.jcollada.schema141.gen.IntArray;
import net.dryanhild.jcollada.schema141.gen.Source;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class SourceUtil {

    private final Logger logger = LogManager.getLogger(SourceUtil.class);

    double[] doubleData;
    boolean[] booleanData;
    int[] intData;
    String name;
    String id;
    int dataCount;
    Accessor common;
    String[] parameters;

    public SourceUtil(Source source) {
        name = source.getName();
        id = source.getId();
        common = source.getTechniqueCommon().getAccessor();
        if (source.getFloatArray() != null) {
            FloatArray data = source.getFloatArray();
            dataCount = data.getCount().intValue();
            doubleData = new double[dataCount];
            int index = 0;
            for (Double d : data.getValues()) {
                doubleData[index++] = d.doubleValue();
            }
        } else if (source.getBoolArray() != null) {
            BoolArray data = source.getBoolArray();
            dataCount = data.getCount().intValue();
            booleanData = new boolean[dataCount];
            int index = 0;
            for (Boolean b : data.getValues()) {
                booleanData[index++] = b.booleanValue();
            }
        } else if (source.getIntArray() != null) {
            IntArray data = source.getIntArray();
            dataCount = data.getCount().intValue();
            intData = new int[dataCount];
            int index = 0;
            for (Long l : data.getValues()) {
                intData[index++] = l.intValue();
            }
        }
        parameters = new String[common.getParams().size()];
        for (int index = 0; index < common.getParams().size(); index++) {
            parameters[index] = common.getParams().get(index).getName();
            if (parameters[index] == null) {
                parameters[index] = "";
            }
        }
    }

    public int getSize(String... paramNames) {
        int size = 0;
        for (String param : parameters) {
            for (String p : paramNames) {
                if (param.equals(p)) {
                    size += 1;
                }
            }
        }
        return size;
    }

    public double[] getParamsAsDoubles(String... paramNames) {
        logger.entry(paramNames);
        int paramCount = 0;
        for (String param : parameters) {
            for (String p : paramNames) {
                if (param.equals(p)) {
                    paramCount += 1;
                }
            }
        }

        final int offset = common.getOffset() == null ? 0 : common.getOffset().intValue();
        final int stride = common.getStride() == null ? 1 : common.getStride().intValue();
        final int count = common.getCount().intValue();
        double[] values = new double[paramCount * count];
        int valuesIndex = 0;
        for (int access = 0; access < count; access++) {
            int dataIndex = offset + (access * stride);
            for (String p : paramNames) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].equals(p)) {
                        values[valuesIndex++] = doubleData[dataIndex + i];
                    }
                }
            }
        }
        return logger.exit(values);
    }

    public float[] getParamsAsFloats(String... paramNames) {
        logger.entry(paramNames);
        int paramCount = 0;
        for (String param : parameters) {
            for (String p : paramNames) {
                if (param.equals(p)) {
                    paramCount += 1;
                }
            }
        }

        final int offset = common.getOffset() == null ? 0 : common.getOffset().intValue();
        final int stride = common.getStride() == null ? 1 : common.getStride().intValue();
        final int count = common.getCount().intValue();
        float[] values = new float[paramCount * count];
        int valuesIndex = 0;
        for (int access = 0; access < count; access++) {
            int dataIndex = offset + (access * stride);
            for (String p : paramNames) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].equals(p)) {
                        values[valuesIndex++] = (float) doubleData[dataIndex + i];
                    }
                }
            }
        }
        return logger.exit(values);
    }

    public int[] getParamsAsInts(String... paramNames) {
        logger.entry(paramNames);
        int paramCount = 0;
        for (String param : parameters) {
            for (String p : paramNames) {
                if (param.equals(p)) {
                    paramCount += 1;
                }
            }
        }

        final int offset = common.getOffset() == null ? 0 : common.getOffset().intValue();
        final int stride = common.getStride() == null ? 1 : common.getStride().intValue();
        final int count = common.getCount().intValue();
        int[] values = new int[paramCount * count];
        int valuesIndex = 0;
        for (int access = 0; access < count; access++) {
            int dataIndex = offset + (access * stride);
            for (String p : paramNames) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].equals(p)) {
                        values[valuesIndex++] = intData[dataIndex + i];
                    }
                }
            }
        }
        return logger.exit(values);
    }

    public boolean[] getParamsAsBooleans(String... paramNames) {
        logger.entry(paramNames);
        int paramCount = 0;
        for (String param : parameters) {
            for (String p : paramNames) {
                if (param.equals(p)) {
                    paramCount += 1;
                }
            }
        }

        final int offset = common.getOffset() == null ? 0 : common.getOffset().intValue();
        final int stride = common.getStride() == null ? 1 : common.getStride().intValue();
        final int count = common.getCount().intValue();
        boolean[] values = new boolean[paramCount * count];
        int valuesIndex = 0;
        for (int access = 0; access < count; access++) {
            int dataIndex = offset + (access * stride);
            for (String p : paramNames) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].equals(p)) {
                        values[valuesIndex++] = booleanData[dataIndex + i];
                    }
                }
            }
        }
        return logger.exit(values);
    }

}
