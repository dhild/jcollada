# JCollada - Java COLLADA loading

[![Build Status](http://mcsrv.dryanhild.net:8080/jenkins/job/JCollada/badge/icon)](http://mcsrv.dryanhild.net:8080/jenkins/job/JCollada/)


The aim of this project is to provide functionality to load COLLADA files into Java for rendering, specifically using OpenGL.

The algorithms within are written for this transformation, since not all of them translate directly to OpenGL. For
example, in COLLADA, the geometries have per-type element indices, whereas OpenGL expects a per-vertex element index.
JCollada provides this translation.

JCollada aims to be lightweight, rather than a fully featured COLLADA parser.

## Releases

### V1.1
Reorganized a lot of internal code & restructured some of the API. Internally, now using the HK2 library to perform
some of the dependency injection. Version detection and support is still done through the java service provider
framework.

- Can load simple <polylist> and <triangles> elements in a geometry.
- Resulting document structure is now easier and cleaner to use.
- Currently supported formats are 1.4.0 and 1.4.1.
- Gradle is now used in lieu of maven for building the projects.

### V1.0
Initial implementation for loading COLLADA files.

Features:

- Ability to load a single .dae file when given a Reader to that file.
- Parsing geometry meshes that contain polylist elements which have vcounts of 3.
- Loading the basic visual scene / node hierarchy so that these elements can be accessed.
- Ability to reference geometries, nodes, and visual scenes through URI addressing.
