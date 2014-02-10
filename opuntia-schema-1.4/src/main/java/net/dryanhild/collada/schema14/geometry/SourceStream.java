package net.dryanhild.collada.schema14.geometry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.collada.x2005.x11.colladaSchema.AccessorDocument.Accessor;
import org.collada.x2005.x11.colladaSchema.FloatArrayDocument.FloatArray;
import org.collada.x2005.x11.colladaSchema.ParamDocument.Param;
import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;

import com.google.common.base.Preconditions;

public class SourceStream {

    private final Map<String, AccessorStream> accessors;

    public SourceStream(Source... sources) {

        Map<String, Source> namedSources = new HashMap<>();
        for (Source s : sources) {
            namedSources.put(s.getId(), s);
        }

        accessors = new HashMap<>();
        for (Source s : sources) {
            Accessor a = s.getTechniqueCommon().getAccessor();

            accessors.put("#" + s.getId(), new AccessorStream(a, namedSources));
        }
    }

    public float[] getElement(String source, int accessIndex) {
        AccessorStream stream = accessors.get(source);

        Preconditions.checkArgument(stream != null, "Source " + source + " not in this stream!");

        return stream.getElement(accessIndex);
    }

    public int getMaximumIndex() {
        int maxIndex = 0;

        for (AccessorStream stream : accessors.values()) {
            maxIndex = Math.max(maxIndex, stream.count - 1);
        }

        return maxIndex;
    }

    public int getElementSize(String source) {
        AccessorStream stream = accessors.get(source);

        Preconditions.checkArgument(stream != null, "Source " + source + " not in this stream!");

        return stream.accessSize;
    }

    private class AccessorStream {
        private final int offset;
        private final int count;
        private final int stride;
        private final List<?> sourceOfData;
        private final boolean[] namedParam;
        private final int accessSize;

        public AccessorStream(Accessor accessor, Map<String, Source> namedSources) {
            count = accessor.getCount().intValue();
            stride = accessor.getStride().intValue();

            String sourceRef = accessor.getSource();
            List<?> sourceData = null;
            for (Source source : namedSources.values()) {
                FloatArray array = source.getFloatArray1();
                if ((array != null) && sourceRef.equals("#" + array.getId())) {
                    sourceData = array.getListValue();
                    break;
                }
            }
            sourceOfData = sourceData;
            Preconditions.checkState(sourceOfData != null, "Unable to locate the source array " + sourceRef);

            offset = accessor.getOffset().intValue();

            Param[] params = accessor.getParamArray();
            namedParam = new boolean[params.length];
            int size = 0;
            for (int i = 0; i < params.length; i++) {
                if (params[i].getName() != null) {
                    namedParam[i] = true;
                    size++;
                } else {
                    namedParam[i] = false;
                }
            }
            accessSize = size;
        }

        public float[] getElement(int accessIndex) {
            Preconditions.checkArgument(accessIndex < count, "Index " + accessIndex + " is too big for data size "
                    + count);

            int sourceOffset = offset + (accessIndex * stride);

            float[] values = new float[accessSize];
            int i = 0;
            for (boolean access : namedParam) {
                if (access) {
                    values[i++] = ((Double) sourceOfData.get(sourceOffset)).floatValue();
                }
                sourceOffset++;
            }

            return values;
        }
    }

}
