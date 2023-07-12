package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

public class Main {
    
    private File original;
    private File copied;

    public static void main(String[] args) throws IOException {
        System.out.println("Program started!");
        
        start(args);

        System.out.println("Program ended!");
    }

    public static void start(String[] args) throws IOException {
        System.out.println("Args:");
        Arrays.stream(args).forEach(System.out::println);

        Main main = new Main();

        main.readeProps(args);
        main.clear();
        main.copy();
    }

    void readeProps(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Path to the properties file with the program args is not passed." +
                    "Pass the path to the args.properties file");
        }
        Path propsPath = Paths.get(args[0]);
        Properties props = new Properties();

        if (!Files.exists(propsPath)) {
            throw new IllegalArgumentException("Wrong path to the properties file. Path: " + propsPath);
        }

        props.load(Files.newInputStream(propsPath));

        original = new File(props.getProperty("src"));
        copied = new File(props.getProperty("trg"));
    }

    private void clear() throws IOException {
        FileUtils.deleteDirectory(copied);
    }

    private void copy() throws IOException {
        System.out.println("Copying ... ");
        FileUtils.copyDirectory(original, copied);

    }

    public File getOriginal() {
        return original;
    }

    public File getCopied() {
        return copied;
    }
}