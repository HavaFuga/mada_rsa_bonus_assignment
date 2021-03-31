package com.fhnw;

public class Main {
    public static void main(String[]args){
        System.out.println("The tasks will be executed automatically.");

        // Task 1
        System.out.println("Task 1");
        new RsaGenerator();

        // Task 2
        System.out.println("Task 2");
        new RsaEncrypter();

        // Task 3
        System.out.println("Task 3");
        new RsaDecrypter("doc_rsa/task_2/cipher.txt", "doc_rsa/task_1/sk.txt", "doc_rsa/task_3/d-text.txt");

        // Task 4
        System.out.println("Task 4");
        new RsaDecrypter("doc_rsa/task_4/cipher.txt", "doc_rsa/task_4/sk.txt", "doc_rsa/task_4/d-text.txt");
    }
}