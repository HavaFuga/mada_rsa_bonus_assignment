package com.fhnw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class RsaGenerator {
    private BigInteger d;
    private final BigInteger one = BigInteger.ONE;
    private final BigInteger zero = BigInteger.ZERO;
    private BigInteger phiN;

    public RsaGenerator () {
        generateVariables();
    }

    private void generateVariables() {
        // 1. (a) Two different primes are created and multiplied using the class BigInteger
        BigInteger p = BigInteger.probablePrime(1024, new Random()); // 1024 Bit = 128 Byte

        BigInteger q = BigInteger.probablePrime(1024, new Random());

        // q and p should be two different primes
        while (q.equals(p)) {
            q = BigInteger.probablePrime(1024 , new Random());
        }

        phiN = p.subtract(one).multiply(q.subtract(one));
        BigInteger n = p.multiply(q);

        // 1. (b) A suitable e is chosen
        BigInteger e = n.subtract(phiN);

        // the corresponding d is computed. In particular, you have to implement the Extended Euclidean Algorithm.
        euclid(e, phiN);

        // 1. (c) The private key is stored in a file sk.txt in the form (n, d) with n and d in decimal representation.
        // The public key is stored in a file pk.txt in the form (n, e).
        storeKey("public", n, e);
        storeKey("private", n, d);
    }

    private void storeKey(String type, BigInteger n, BigInteger k) {
        File file;
        String text;
        switch (type) {
            case "public":
                file = new File("doc_rsa/task_1/pk.txt");
                text = "(" + n.toString() + "," + k.toString() +")";
                break;

            case "private":
                file = new File("doc_rsa/task_1/sk.txt");
                text = "(" + n.toString() + "," + k.toString() +")";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        try {
            FileWriter w = new FileWriter(file);
            w.write(text);
            w.flush();
            w.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void euclid(BigInteger e, BigInteger phiN) {
        BigInteger a = phiN;
        BigInteger b = e;
        BigInteger x0 = one;
        BigInteger y0 = zero;
        BigInteger x1 = zero;
        BigInteger y1 = one;
        BigInteger q;
        BigInteger r;

        while (b.compareTo(zero) != 0) {
            q = a.divide(b);
            r = a.mod(b);
            a = b;
            b = r;

            // variables that are needed temporary for new x1' and y1'
            BigInteger oldX0 = x0;
            BigInteger oldY0 = y0;
            x0 = x1;
            y0 = y1;
            x1 = oldX0.subtract(q.multiply(x1));
            y1 = oldY0.subtract(q.multiply(y1));
        }

        if (y0.compareTo(zero) < 0) { // if y0 is negative add phi(n)
            d = y0.add(phiN);
        } else {
            d = y0;
        }

    }
}
