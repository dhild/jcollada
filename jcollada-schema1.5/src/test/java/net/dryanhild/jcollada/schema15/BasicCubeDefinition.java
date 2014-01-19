package net.dryanhild.jcollada.schema15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class BasicCubeDefinition {

    private static String cubeContent;

    private BasicCubeDefinition() {
    }

    public static String getCubeContent() {
        if (cubeContent == null) {
            loadCubeContent();
        }
        return cubeContent;
    }

    private static void loadCubeContent() {
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("test-files/cube.dae")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder content = new StringBuilder();
            String nextLine;
            nextLine = reader.readLine();
            while (nextLine != null) {
                content.append(nextLine);
                nextLine = reader.readLine();
            }

            cubeContent = content.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
