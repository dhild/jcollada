package net.dryanhild.jcollada.schema14.geometry;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.collada.x2005.x11.colladaSchema.AccessorDocument.Accessor;
import org.collada.x2005.x11.colladaSchema.FloatArrayDocument.FloatArray;
import org.collada.x2005.x11.colladaSchema.ParamDocument.Param;
import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;

import com.google.common.base.Preconditions;

public class SourceStream {

    private final Map<String, Source> namedSources;
    private final Map<String, AccessorStream> accessors;

    public SourceStream(Source... sources) {
        namedSources = new HashMap<>();
        accessors = new HashMap<>();

        for (Source s : sources) {
            namedSources.put(s.getId(), s);
        }

        for (Source s : sources) {
            Accessor a = s.getTechniqueCommon().getAccessor();

            accessors.put(s.getId(), new AccessorStream(a));
        }
    }

    public int fillElement(String source, int accessIndex, double[] copyTo, int copyOffset) {
        AccessorStream stream = accessors.get(source);

        Preconditions.checkArgument(stream != null, "Source " + source + " not in this stream!");

        return stream.fillElement(accessIndex, copyTo, copyOffset);
    }

    public int getMaximumIndex() {
        int maxIndex = 0;

        for (AccessorStream stream : accessors.values()) {
            maxIndex = Math.max(maxIndex, stream.count - 1);
        }

        return maxIndex;
    }

    private class AccessorStream {
        private final int offset;
        private final int count;
        private final int stride;
        private final List<?> sourceOfData;
        private final boolean[] namedParam;
        private final int accessSize;

        public AccessorStream(Accessor accessor) {
            count = accessor.getCount().intValue();
            stride = accessor.getStride().intValue();

            String sourceRef = accessor.getSource();
            List<?> sourceData = null;
            for (Source source : namedSources.values()) {
                FloatArray array = source.getFloatArray1();
                if ((array != null) && sourceRef.equals("#" + array.getName())) {
                    sourceData = array.getListValue();
                    break;
                }
            }
            sourceOfData = sourceData;
            Preconditions.checkState(sourceOfData != null, "Unable to locate the source array " + sourceRef);

            BigInteger off = accessor.getOffset();
            offset = off == null ? 0 : off.intValue();

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

        public int fillElement(int accessIndex, double[] copyTo, int copyOffset) {
            Preconditions.checkArgument(accessIndex < count, "Index " + accessIndex + " is too big for data size "
                    + count);

            int sourceOffset = offset + (accessIndex * stride);

            for (boolean access : namedParam) {
                if (access) {
                    copyTo[copyOffset++] = ((Double) sourceOfData.get(sourceOffset)).doubleValue();
                }
                sourceOffset++;
            }

            return accessSize;
        }
    }

}
