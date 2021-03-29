package com.fhnw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/*
1. One can generate an RSA key pair of realistic size. Therefore, the following steps are
        necessary:
        - (a) Two different primes are created and multiplied using the class BigInteger.
        - (b) A suitable e is chosen and the corresponding d is computed. In particular, you have
        to implement the Extended Euclidean Algorithm.
        - (c) The private key is stored in a file sk.txt in the form (n, d) with n and d in decimal
        representation. The public key is stored in a file pk.txt in the form (n, e).
*/


public class RsaGenerator {
    private BigInteger n;
    private BigInteger q;
    private BigInteger p;
    private BigInteger e;
    private BigInteger d;
    private BigInteger one = BigInteger.ONE;
    private BigInteger zero = BigInteger.ZERO;
    private BigInteger phiN;

    public RsaGenerator () {
        System.out.println("1");

        p = BigInteger.probablePrime(1024, new Random());
        q = BigInteger.probablePrime(1024, new Random());
        // q and p should be two different primes
        while (q == p) {
            q = BigInteger.probablePrime(1024, new Random());
        }

        n = p.multiply(q);

        phiN = p.subtract(one).multiply(q.subtract(one));
        generateE(n, phiN);
        euclid(e, phiN);

        System.out.println("asdf");


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

    private void generateE(BigInteger n, BigInteger phiN) {
        e = n.subtract(phiN);
//        do {
//            e = BigInteger.probablePrime(1024, new Random());
//        } while (e.compareTo(one) == 1
//                && e.compareTo(phiN) == -1
//                && e.gcd(phiN).equals(one)
//        );
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

        while (b.compareTo(BigInteger.ZERO) != 0) {
            q = a.divide(b);
            r = a.mod(b);
            a = b;
            b = r;

            // bigint that are needed temporary for new x1' and y1'
            BigInteger oldX0 = x0;
            BigInteger oldY0 = y0;
            x0 = x1;
            y0 = y1;
            x1 = oldX0.subtract(q.multiply(x1));
            y1 = oldY0.subtract(q.multiply(y1));
        }

        if (y0.compareTo(zero) < 0) {
            d = y0.add(phiN);
        } else {
            d = y0;
        }

    }
}
