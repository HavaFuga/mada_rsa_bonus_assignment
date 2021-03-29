package com.fhnw;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class RsaEncrypter {



    private void encrypt(String text) {

    }


    public RsaEncrypter() {
        BigInteger n = getKeys("n");
        BigInteger e = getKeys("e");

        String text = getText();

        encrypt(text);


        BigInteger message = new BigInteger(text.getBytes());
        BigInteger b = message.modPow(e,n);

        byte[] asciiText = text.getBytes(StandardCharsets.US_ASCII);
        String encryptedText = "";
        for (byte x : asciiText) {
            encryptedText += new BigInteger(String.valueOf(x)).modPow(e,n) + ",";
        }

        File file = new File("doc_rsa/task_2/cipher.txt");

        try {
            FileWriter w = new FileWriter(file);
            w.write(b.toString());
            w.flush();
            w.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

    private BigInteger getKeys(String x) {
        String file = "";
        try {
            file = new String(Files.readAllBytes(Paths.get("doc_rsa/task_1/pk.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] values = file.split(",");

        switch (x) {
            case "n":
                return new BigInteger(values[0].replaceAll("\\p{P}",""));
            case "e":
                return new BigInteger(values[1].replaceAll("\\p{P}",""));
            default:
                System.out.println("cry on the floor");
                return null;
        }
    }

    private String getText() {
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get("doc_rsa/task_2/text.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }



}
