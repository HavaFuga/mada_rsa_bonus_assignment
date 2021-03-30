package com.fhnw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task4 {
    public Task4() {
        String encryptedText = getText();
        BigInteger n = getKeys("n");
        BigInteger d = getKeys("d");

        String[] bytesString = encryptedText.split(",");

        String decryptedText = "";

        BigInteger text;
        for (String b : bytesString) {
            text = new BigInteger(b).modPow(d, n);
            BigInteger h = fastExponentiation(n, text, d);
            decryptedText += Character.toString((char) text.intValue());
        }

        System.out.println(decryptedText);

        // 3. One can decrypt a file cipher.txt with a private key in sk.txt and store the resulting plain text in text-d.txt
        File file = new File("doc_rsa/task_4/text-d.txt");

        try {
            FileWriter w = new FileWriter(file);
            w.write(decryptedText);
            w.flush();
            w.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private BigInteger fastExponentiation(BigInteger modulo, BigInteger number, BigInteger exponent) {
        int i = exponent.bitLength() - 1;
        BigInteger h = BigInteger.ONE;
        BigInteger k = number;
        String bA = exponent.toString(2);
        char[] ch = bA.toCharArray();

        while (i >= 0) {
            if (ch[i] == '1') {
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
            file = new String(Files.readAllBytes(Paths.get("doc_rsa/task_4/sk.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] values = file.split(",");

        switch (x) {
            case "n":
                return new BigInteger(values[0].replaceAll("\\p{P}", ""));
            case "d":
                return new BigInteger(values[1].replaceAll("\\p{P}", ""));
            default:
                System.out.println("cry on the floor");
                return null;
        }
    }

    private String getText() {
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get("doc_rsa/task_4/cipher.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
