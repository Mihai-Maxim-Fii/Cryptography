package org.example;

import java.math.BigInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       RSA rsa=new RSA(true);
       /*
        BigInteger cypher=rsa.Encrypt(BigInteger.valueOf(123));
        long tStart = System.nanoTime();
        System.out.println(rsa.Decrypt(cypher));
        long tEnd = System.nanoTime();
        System.out.println((tEnd-tStart));

        tStart = System.nanoTime();
        System.out.println(rsa.Decrypt32(cypher));
        tEnd = System.nanoTime();
        System.out.println((tEnd-tStart));
*/

        System.out.println("decryption key:"+rsa.d);
        System.out.println(rsa.wienerAttack());


    }
}
