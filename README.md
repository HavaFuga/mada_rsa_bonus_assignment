# Programming assignment RSA
_Mathematics for Data Communication Class 2iCeng_

Write a program in Java that is capable of the following:
1. One can generate an RSA key pair of realistic size. Therefore, the following steps are
necessary:
    - (a) Two different primes are created and multiplied using the class BigInteger.
    - (b) A suitable e is chosen and the corresponding d is computed. In particular, you have
to implement the Extended Euclidean Algorithm.
    - (c) The private key is stored in a file sk.txt in the form (n, d) with n and d in decimal
representation. The public key is stored in a file pk.txt in the form (n, e).

2. One can read in a text file (ASCII) text.txt into a string and encrypt this string in the
following way:
    - (a) One reads in a public key from a file pk.txt.
    - (b) Every character of text.txt is converted to its ASCII code (number between 0 and
127).
    - (c) Every such number is encrypted with the RSA scheme. In particular, you have to
implement the fast exponentiation algorithm.
    - (d) The encryptions are stored in a file cipher.txt (in decimal representation and separated by a comma).
Remark: This character by character encryption is not secure as statistical analyzes are
possible. (The letter frequencies of the plain text coincide with those of the cipher text.)
RSA is in practice hence not used in that way but in a clever way in combination with a
symmetric scheme. This is part of the cryptography courses.

3. One can decrypt a file cipher.txt (that has been constructed in the way described above)
with a private key in sk.txt and store the resulting plain text in text-d.txt.

4. Decrypt the file cipher.txt with the secret key in sk.txt.
