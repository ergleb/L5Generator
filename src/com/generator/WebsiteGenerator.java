package com.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by hlib on 16.01.17.
 */
public class WebsiteGenerator {
    private List<String> billingTypes = Arrays.asList("call", "sms", "data");
    private List<String> clients;
    private List<String> websites;
    private Random random = new Random();

    void fillClients (String fileName) throws IOException {
        clients = Files.readAllLines(Paths.get(fileName));
    }

    void fillWebsites (String fileName) throws IOException {
        websites = Files.readAllLines(Paths.get(fileName));
    }

    String getRandomClientUuid(){
        return clients.get(random.nextInt(clients.size())).split(",")[0];
    }
    String getRandomWebsite() {return  websites.get(random.nextInt(websites.size())).split(",")[1];}

    void generate (String filePath, int number) throws IOException{
        Path file = Paths.get(filePath);
        List<String> billings = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            String uuid = UUID.randomUUID().toString();
            billings.add(getRandomClientUuid() + ',' + getRandomWebsite() + ',' + random.nextInt(3600) + ','+ random.nextInt(500000));
        }
        Files.write(file, billings, UTF_8, APPEND, CREATE);
    }

    public static void main(String[] args) {
        WebsiteGenerator generator = new WebsiteGenerator();
        try{
            generator.fillClients("names");
            generator.fillWebsites("websites");
            for (int i = 0; i < 60; i++) {
                for (int j = 0; j < 1000; j++) {
                    generator.generate("webinfo/webinfo"+i, 1000);
                }
            }
        } catch (IOException e){
            System.out.println(e);
            return;
        }
    }
}
