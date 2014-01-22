package net.dryanhild.jcollada.schema15.geometry;

import java.math.BigInteger;
import java.util.List;

import org.collada.x2008.x03.colladaSchema.AccessorType;
import org.collada.x2008.x03.colladaSchema.FloatArrayType;
import org.collada.x2008.x03.colladaSchema.FloatType;
import org.collada.x2008.x03.colladaSchema.InputLocalOffsetType;
import org.collada.x2008.x03.colladaSchema.InputLocalType;
import org.collada.x2008.x03.colladaSchema.ListOfFloatsType;
import org.collada.x2008.x03.colladaSchema.MeshType;
import org.collada.x2008.x03.colladaSchema.PType;
import org.collada.x2008.x03.colladaSchema.ParamType;
import org.collada.x2008.x03.colladaSchema.PolygonsType;
import org.collada.x2008.x03.colladaSchema.SourceType;
import org.collada.x2008.x03.colladaSchema.UintType;
import org.collada.x2008.x03.colladaSchema.VerticesType;

public class MeshHandlerTests {

    public MeshType createCubeMeshType() {
        MeshType mesh = MeshType.Factory.newInstance();

        createPosArray(mesh.addNewSource());
        createNormalArray(mesh.addNewSource());

        createVertices(mesh.addNewVertices());

        createPolygons(mesh.addNewPolygons());

        return mesh;
    }

    public void createPosArray(SourceType source) {
        ListOfFloatsType floats = ListOfFloatsType.Factory.newInstance();

        addFloats(floats, -0.5, 0.5, 0.5);
        addFloats(floats, 0.5, 0.5, 0.5);
        addFloats(floats, -0.5, -0.5, 0.5);
        addFloats(floats, 0.5, -0.5, 0.5);
        addFloats(floats, -0.5, 0.5, -0.5);
        addFloats(floats, 0.5, 0.5, -0.5);
        addFloats(floats, -0.5, -0.5, -0.5);
        addFloats(floats, 0.5, -0.5, -0.5);

        createSource(source, "box-Pos-array", "box-Pos", floats);
    }

    public void createNormalArray(SourceType source) {
        ListOfFloatsType floats = ListOfFloatsType.Factory.newInstance();

        addFloats(floats, 1, 0, 0);
        addFloats(floats, -1, 0, 0);
        addFloats(floats, 0, 1, 0);
        addFloats(floats, 0, -1, 0);
        addFloats(floats, 0, 0, 1);
        addFloats(floats, 0, 0, -1);

        createSource(source, "box-0-Normal-array", "box-0-Normal", floats);
    }

    public void addFloats(ListOfFloatsType floats, double... values) {
        for (double value : values) {
            addFloat(floats, value);
        }
    }

    @SuppressWarnings("unchecked")
    public void addFloat(ListOfFloatsType floats, double value) {
        FloatType floatValue = FloatType.Factory.newInstance();
        floatValue.setDoubleValue(1.0);
        floats.getListValue().add(floatValue);
    }

    public void createSource(SourceType source, String arrayString, String sourceId, ListOfFloatsType values) {
        List<?> floats = values.getListValue();
        FloatArrayType posArray = FloatArrayType.Factory.newInstance();
        posArray.setCount(BigInteger.valueOf(floats.size()));
        posArray.setId(arrayString);
        posArray.setListValue(floats);

        source.setId(sourceId);

        SourceType.TechniqueCommon technique = createCommonTechnique("#" + arrayString, floats.size() / 3);

        source.setTechniqueCommon(technique);
    }

    public SourceType.TechniqueCommon createCommonTechnique(String accessorString, int count) {
        AccessorType accessor = AccessorType.Factory.newInstance();
        accessor.setSource(accessorString);
        accessor.setCount(BigInteger.valueOf(count));
        accessor.setStride(BigInteger.valueOf(3));
        ParamType x = accessor.addNewParam();
        x.setName("X");
        x.setType("float");
        ParamType y = accessor.addNewParam();
        y.setName("Y");
        y.setType("float");
        ParamType z = accessor.addNewParam();
        z.setName("Z");
        z.setType("float");
        SourceType.TechniqueCommon technique = SourceType.TechniqueCommon.Factory.newInstance();
        technique.setAccessor(accessor);
        return technique;
    }

    public VerticesType createVertices(VerticesType vertices) {
        vertices.setId("box-Vtx");

        InputLocalType input = vertices.addNewInput();
        input.setSemantic("POSITION");
        input.setSource("#box-Pos");

        return vertices;
    }

    public void createPolygons(PolygonsType polys) {
        polys.setCount(BigInteger.valueOf(6));
        polys.setMaterial("WHITE");

        InputLocalOffsetType vertex = polys.addNewInput();
        vertex.setOffset(BigInteger.valueOf(0));
        vertex.setSemantic("VERTEX");
        vertex.setSource("#box-Vtx");

        InputLocalOffsetType normal = polys.addNewInput();
        normal.setOffset(BigInteger.valueOf(1));
        normal.setSemantic("NORMAL");
        normal.setSource("#box-0-Normal");

        addIndices(polys.addNewP(), 0, 4, 2, 4, 3, 4, 1, 4);
        addIndices(polys.addNewP(), 0, 1, 1, 2, 5, 2, 4, 2);
        addIndices(polys.addNewP(), 6, 7, 7, 3, 3, 3, 2, 3);
        addIndices(polys.addNewP(), 0, 4, 4, 1, 6, 1, 2, 1);
        addIndices(polys.addNewP(), 3, 0, 7, 0, 5, 0, 1, 0);
        addIndices(polys.addNewP(), 5, 5, 7, 5, 6, 5, 4, 5);
    }

    public void addIndices(PType p, long... values) {
        for (long value : values) {
            addIndex(p, value);
        }
    }

    public void addIndex(PType p, long value) {
        UintType uint = UintType.Factory.newInstance();
        uint.setBigIntegerValue(BigInteger.valueOf(value));
    }

}
