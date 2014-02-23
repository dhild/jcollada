package net.dryanhild.collada.data.scene;

import java.util.List;
import net.dryanhild.collada.data.AddressableType;

public interface VisualScene extends AddressableType {

    List<? extends Node> getNodes();

}
