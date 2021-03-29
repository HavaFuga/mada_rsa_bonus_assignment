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

    public RsaEncrypter() {
        BigInteger n = getKeys("n");
        BigInteger e = getKeys("e");

        String text = getText();

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

    private void convertStringToAscii(String text) {
//        StringBuilder sb = new StringBuilder();
//        for (char c : text.toCharArray()) {
//            encrypt((int) c);
//
//            // convert every character into array
//            sb.append(" " + (int) c);
//        }
//
////        BigInteger mInt = new BigInteger(sb.toString());
//        System.out.println(sb);

    }

    private void encrypt(String text, BigInteger e) {

//        BigInteger n = new BigInteger("9516311845790656153499716760847001433441357");
//        BigInteger e = new BigInteger("65537");
//        BigInteger d = new BigInteger("5617843187844953170308463622230283376298685");
//        Charset c = StandardCharsets.UTF_8;
//        String plainText = "Rosetta Code";
//        System.out.println("PlainText : " + plainText);
//        byte[] bytes = plainText.getBytes();
//        BigInteger plainNum = new BigInteger(bytes);
//        System.out.println("As number : " + plainNum);
//        BigInteger Bytes = new BigInteger(bytes);
//        if (Bytes.compareTo(n) == 1) {
//            System.out.println("Plaintext is too long");
//            return;
//        }
//        BigInteger enc = plainNum.modPow(e, n);
//        System.out.println("Encoded: " + enc);
//        BigInteger dec = enc.modPow(d, n);
//        System.out.println("Decoded: " + dec);
//        String decText = new String(dec.toByteArray(), c);
//        System.out.println("As text: " + decText);
    }

}
