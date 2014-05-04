package net.dryanhild.collada.schema14;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.dryanhild.collada.NoSuchElementIdException;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.data.fx.Effect;
import net.dryanhild.collada.data.fx.Material;
import net.dryanhild.collada.data.geometry.Geometry;
import net.dryanhild.collada.data.scene.Node;
import net.dryanhild.collada.data.scene.VisualScene;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ColladaDocument15 implements ColladaDocument {

    private final List<Effect> effects = Lists.newArrayList();
    private final Map<String, Effect> effectsById = Maps.newHashMap();

    @Override
    public Iterable<Geometry> getGeometries() {
        return Collections.emptyList();
    }

    @Override
    public Geometry getGeometry(String id) {
        throw new NoSuchElementIdException(id);
    }

    @Override
    public Iterable<Node> getNodes() {
        return Collections.emptyList();
    }

    @Override
    public Node getNode(String id) {
        throw new NoSuchElementIdException(id);
    }

    @Override
    public Iterable<VisualScene> getVisualScenes() {
        return Collections.emptyList();
    }

    @Override
    public VisualScene getVisualScene(String id) {
        throw new NoSuchElementIdException(id);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
        if (effect.getId() != null) {
            effectsById.put("#" + effect.getId(), effect);
        }
    }

    @Override
    public Iterable<? extends Effect> getEffects() {
        return effects;
    }

    @Override
    public Effect getEffect(String id) {
        return effectsById.get(id);
    }

    @Override
    public Iterable<? extends Material> getMaterials() {
        return Collections.emptyList();
    }

    @Override
    public Material getMaterial(String id) {
        throw new NoSuchElementIdException(id);
    }

    @Override
    public VisualScene getMainScene() {
        throw new NoSuchElementIdException("Main scene of the COLLADA file");
    }

}
