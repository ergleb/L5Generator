package com.generator;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by hlib on 15.01.17.
 */
public class BillingGenerator {
    private List<String> billingTypes = Arrays.asList("call", "sms", "data");
    private List<String> clients;
    private final Random random = new Random();
    void fillClients (String fileName) throws IOException{
        clients = Files.readAllLines(Paths.get(fileName));
    }

    String getRandomClientUuid(){
        return clients.get(random.nextInt(clients.size())).split(",")[0];
    }

    String generateRandomBilling(){
        String type = billingTypes.get(random.nextInt(billingTypes.size()));
        int quantity = 0;
        double price = 0d;
        switch (type){
            case "call": {
                quantity = random.nextInt(30*60) + 1;
                price = quantity * 0.01;
                break;
            }
            case "sms": {
                quantity = random.nextInt(10) + 1;
                price = quantity * 0.05;
                break;
            }
            case "data": {
                quantity = random.nextInt(100000) + 1;
                price = quantity * 0.0001;
                break;
            }
        }
        price = (double) Math.round(price * 100)/100;
        return type + ',' + quantity + ',' + price;
    }

    void generateBilling (String filePath, int number) throws IOException {
        Path file = Paths.get(filePath);
        List<String> billings = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            String uuid = UUID.randomUUID().toString();
            billings.add(uuid + ',' + getRandomClientUuid() + ',' + generateRandomBilling());
        }
        Files.write(file, billings, UTF_8, APPEND, CREATE);
    }

    public static void main(String[] args) {
        BillingGenerator billingGenerator = new BillingGenerator();
        try{
            billingGenerator.fillClients("names");
            for (int i = 0; i < 60; i++) {
                for (int j = 0; j < 1000; j++) {
                    billingGenerator.generateBilling("billings/billing"+i, 1000);
                }
            }
        } catch (IOException e){
            System.out.println(e);
            return;
        }
    }
}
