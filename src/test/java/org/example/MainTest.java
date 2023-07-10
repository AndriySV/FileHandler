package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

    private Main main;
    private String[] args;
    private String dirFrom;

    @BeforeEach
    public void setUp() {
        main = new Main();
        args = new String[1];
        dirFrom = "C:\\Users\\asvyryd\\IdeaProjects\\FileHandler\\dir From";
    }

    @Test
    public void absPath() throws IOException {
        args[0] = "C:\\Users\\asvyryd\\IdeaProjects\\FileHandler\\cfg\\args.properties";

        main.readeProps(args);
        String dirFrom = main.getOriginal().getAbsolutePath();

        assertEquals(this.dirFrom, dirFrom);
    }

    @Test
    public void absPathIncorrect() {
        args[0] = "C:\\wrongPath\\args.properties";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> main.readeProps(args));

        String expectedMessage = "Wrong path to the properties file. Path: " + args[0];
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void rltPath() throws IOException {
        List<String> paths = new ArrayList<>();
        paths.add("cfg/args.properties");
        paths.add("./cfg/args.properties");

        for (String path : paths) {
            args[0] = path;
            main.readeProps(args);
            String dirFrom = main.getOriginal().getAbsolutePath();

            assertEquals(this.dirFrom, dirFrom);
        }
    }

    @Test
    public void rltPathIncorrect() {
        List<String> paths = new ArrayList<>();
        paths.add("wrongPath/args.properties");
        paths.add("/cfg/args.properties");

        for (String pathName : paths) {
            args[0] = pathName;
            Exception exception = assertThrows(IllegalArgumentException.class, () -> main.readeProps(args));

            String expectedMessage = "Wrong path to the properties file. Path: " + Paths.get(pathName);
            String actualMessage = exception.getMessage();

            assertEquals(expectedMessage, actualMessage);
        }
    }

}