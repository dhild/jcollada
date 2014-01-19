package net.dryanhild.jcollada.schema15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class BasicCubeDefinition {

    public static final String TEST_FILE_LOCATION = "test-files/cube.dae";

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
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(TEST_FILE_LOCATION)) {
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
