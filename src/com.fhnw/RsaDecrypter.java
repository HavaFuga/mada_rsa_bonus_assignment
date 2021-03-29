package com.fhnw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

public class RsaDecrypter {


    public RsaDecrypter() {
        Charset c = StandardCharsets.UTF_8;
        RsaEncrypter r = new RsaEncrypter();

        String encryptedText = getText();
        BigInteger n = getKeys("n");
        BigInteger d = getKeys("d");


        String[] bytesString = encryptedText.split(",");
        String s = "";

        BigInteger b = new BigInteger(encryptedText);

        BigInteger decryptedText = b.modPow(d, n);

        System.out.println(""+new String(decryptedText.toByteArray()));

        for (String x: bytesString) {
            BigInteger decription = new BigInteger(x).modPow(d,n);

            System.out.println("asdf");
            s += String.valueOf(decription);
        }

        File file = new File("doc_rsa/task_3/text-d.txt");

        try {
            FileWriter w = new FileWriter(file);
            w.write(s);
            w.flush();
            w.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private BigInteger getKeys(String x) {
        String file = "";
        try {
            file = new String(Files.readAllBytes(Paths.get("doc_rsa/task_1/sk.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] values = file.split(",");

        switch (x) {
            case "n":
                return new BigInteger(values[0].replaceAll("\\p{P}",""));
            case "d":
                return new BigInteger(values[1].replaceAll("\\p{P}",""));
            default:
                System.out.println("cry on the floor");
                return null;
        }
    }

    private String getText() {
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get("doc_rsa/task_2/cipher.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
