# JCollada - Java COLLADA loading

[![Build Status](http://dev.dryanhild.net:8080/jenkins/job/JCollada/badge/icon)](http://dev.dryanhild.net:8080/jenkins/job/JCollada/)


The aim of this project is to provide functionality to load COLLADA files into Java for rendering, specifically using OpenGL.

The algorithms within are written for this transformation, since not all of them translate directly to OpenGL. For
example, in COLLADA, the geometries have per-type element indices, whereas OpenGL expects a per-vertex element index.
JCollada provides this translation.

JCollada aims to be lightweight, rather than a fully featured COLLADA parser.

## How to use

You are, of course, able to download the source, build it yourself, and use it however you see fit. The packaged gradle wrapper should be enough to get you started if this is your interest.

However, you would probably like to use a snapshot or released version that you know works. In that case, you have two urls which are available:

- Snapshots at [http://dev.dryanhild.net:8080/nexus/content/repositories/snapshots/](http://dev.dryanhild.net:8080/nexus/content/repositories/snapshots/)
- Releases at [http://dev.dryanhild.net:8080/nexus/content/repositories/releases/](http://dev.dryanhild.net:8080/nexus/content/repositories/releases/)

### Gradle example usage

    repositories {
        maven {
            url "http://dev.dryanhild.net:8080/nexus/content/repositories/releases/"
        }
    }
    dependencies {
        compile "net.dryanhild.jcollada:jcollada-schema-1.4:1.1"
    }

### Maven example usage

    <repositories>
      <repository>
        <id>sonatype-dryanhild</id>
        <name>sonatype-dryanhild</name>
        <url>http://dev.dryanhild.net:8080/nexus/content/repositories/releases/</url>
      </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>net.dryanhild.jcollada</groupId>
            <artifactId>jcollada-schema-1.4</artifactId>
            <version>1.1</version>
        </dependency>
    </dependencies>

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
