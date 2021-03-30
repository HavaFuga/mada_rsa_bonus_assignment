package com.fhnw;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RsaEncrypter {
    private BigInteger one = BigInteger.ONE;

    public RsaEncrypter() {
        BigInteger n = getKeys("n");
        BigInteger e = getKeys("e");

        // 2. (a) One reads in a public key from a file pk.txt
        String text = getText();

        // 2. (b) Every character of text.txt is converted to its ASCII code (number between 0 and 127).
        byte[] bytes = text.getBytes();

        String encryptedText = "";

        // 2. (c) Every such number is encrypted with the RSA scheme.
        for (byte b : bytes) {
            BigInteger h = fastExponentiation(n, b, e);
            BigInteger hEncrypted = BigInteger.valueOf(b).modPow(e,n);
            encryptedText += hEncrypted + ",";
        }

        File file = new File("doc_rsa/task_2/cipher.txt");

        // 2. (d) The encryptions are stored in a file cipher.txt (in decimal representation and separated by a comma)
        try {
            FileWriter w = new FileWriter(file);
            w.write(encryptedText);
            w.flush();
            w.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private BigInteger fastExponentiation(BigInteger modulo, byte number, BigInteger exponent) {
        int i = exponent.bitLength() - 1;
        BigInteger h = one;
        BigInteger k = new BigInteger(String.valueOf(number));
        String ascii = exponent.toString(2);    // binary
        char[] ch = ascii.toCharArray();

        while (i >= 0) {
            if (ch[i] == '1') {     // only multiply if 1
                h = h.multiply(k).mod(modulo);
            }
            k = k.sqrt().mod(modulo);
            i--;
        }

        return h;
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
