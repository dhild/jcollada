package net.dryanhild.jcollada.data.scene;

import java.util.List;

import net.dryanhild.jcollada.data.AddressableType;

public interface VisualScene extends AddressableType {

    List<? extends Node> getNodes();

}
