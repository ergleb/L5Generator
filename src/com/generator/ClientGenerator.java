package com.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by hlib on 15.01.17.
 */
public class ClientGenerator {
    private List<String> firstNames;
    private List<String> lastNames;
    private final Random random = new Random();
    void fillFirstNames (String filePath) throws IOException{
        firstNames = Files.readAllLines(Paths.get(filePath));
    }

    void fillLastNames (String filePath) throws IOException{
        lastNames = Files.readAllLines(Paths.get(filePath));
    }

    void generateNames (String filePath, int number) throws IOException {
        Path file = Paths.get(filePath);
        List<String> names = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            String uuid = UUID.randomUUID().toString();
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            names.add(uuid + ',' + firstName + ',' + lastName);
        }
        Files.write(file, names, UTF_8, APPEND, CREATE);
        System.out.println("generated " + number);
    }

    public static void main(String[] args) {
        ClientGenerator generator = new ClientGenerator();
        try {
            generator.fillFirstNames("first");
            generator.fillLastNames("last");
            for (int i = 0; i < 100; i++) {
                generator.generateNames("names", 100);
            }
        } catch (IOException ex){
            System.out.println("Can't open some files");
            return;
        }

    }
}
