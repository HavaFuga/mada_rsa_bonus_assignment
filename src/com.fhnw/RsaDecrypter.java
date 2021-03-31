package com.fhnw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RsaDecrypter {
    private String fileText;
    private String fileKeys;
    private String fileDecryptedText;
    private BigInteger one = BigInteger.ONE;

    public RsaDecrypter(String fileText, String fileKeys, String fileDecryptedText) {
        this.fileText = fileText;
        this.fileKeys = fileKeys;
        this.fileDecryptedText = fileDecryptedText;

        String encryptedText = getText(fileText);
        BigInteger n = getKeys("n");
        BigInteger d = getKeys("d");

        String[] bytesString = encryptedText.split(",");

        String decryptedText = "";

        BigInteger h;
        for (String b : bytesString) {
            h = new BigInteger(b).modPow(d,n);
            BigInteger hWithFastExp = fastExponentiation(n, new BigInteger(b), d);
            decryptedText += Character.toString((char) h.intValue());
        }

        System.out.println(decryptedText);

        // 3. One can decrypt a file cipher.txt with a private key in sk.txt and store the resulting plain text in text-d.txt
        File file = new File(fileDecryptedText);

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
        int i = exponent.bitLength() - 1; // b(l)
        BigInteger h = one;
        BigInteger k = number;

        while (i >= 0) {
            if (exponent.testBit(i)) {     // only multiply if 1
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
            file = new String(Files.readAllBytes(Paths.get(fileKeys)));
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

    private String getText(String file) {
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
