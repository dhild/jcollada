# JCollada - Java COLLADA loading

[![Build Status](http://mcsrv.dryanhild.net:8080/jenkins/job/JCollada/badge/icon)](http://mcsrv.dryanhild.net:8080/jenkins/job/JCollada/)


The aim of this project is to provide functionality to load COLLADA files into Java for rendering, specifically using OpenGL.

The algorithms within are written for this transformation, since not all of them translate directly to OpenGL. For example, in COLLADA, the geometries have per-type element indices, whereas OpenGL expects a per-vertex element index. Optunia provides this translation.

