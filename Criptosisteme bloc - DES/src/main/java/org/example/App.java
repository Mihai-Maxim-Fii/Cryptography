package org.example;






import javax.swing.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.SortedMap;

public class App {
    public static String constructInitialKey() {
        boolean done = false;
        String s = "";
        while (!done) {
            s = String.format("%x", (int) (Math.random() * 100));
            s += s;
            s += s;
            s += s;
            if (s.length() > 8) done = true;
        }
        return s.toUpperCase();
    }


    public static String binToHex(String input) {
        int n = (int) input.length() / 4;
        String s=new BigInteger(input,2).toString(16);
        for(int i=s.length();i<n;i++)
        {
            s='0'+s;
        }
        return s;
    }

    public static String hextoBin(String input) {
        int n = (int) input.length() * 4;
        String s=new BigInteger(input,16).toString(2);
        for(int i=s.length();i<n;i++)
        {
            s='0'+s;
        }
        return s;
    }


    public static String permute(String input, int[] permutation) {
        String out = "";
        input = hextoBin(input);
        for (int i = 0; i < permutation.length; i++) {
            out += input.charAt(permutation[i] - 1);
        }
        return binToHex(out);
    }

    public static String checkSbox(String input) {
        String out = "";
        input = hextoBin(input);
        int k = 0;
        for (int i = 0; i < input.toCharArray().length; i++) {
            if ((i + 1) % 6 == 0) {
                String temp = input.substring(k, i + 1);
                String tempColumn = "";
                int column;
                int row;
                for (int z = temp.toCharArray().length - 2; z > 0; z--) {
                    if (temp.charAt(z) == '0')
                        tempColumn = '0' + tempColumn;
                    else
                        tempColumn = '1' + tempColumn;
                }

                column = Integer.parseInt(tempColumn, 2);
                row = (temp.charAt(temp.length() - 1) - '0') * 1 + (temp.charAt(0) - '0') * 2;
                out += Integer.toHexString(Permutations.sBoxPermutation[k / 6][row][column]);

                k = i + 1;
            }
        }
        return out;
    }

    public static String iterateRound(String input, String key) {
        String left = input.substring(0, 8);
        String right = input.substring(8, 16);
        String tempRight = right;
        tempRight = permute(tempRight, Permutations.expansionPermutation);
        tempRight = xor(tempRight, key);
        tempRight = checkSbox(tempRight);
        tempRight = permute(tempRight, Permutations.simplePermutation);
        left = xor(left, tempRight);
        return right + left;
    }

    public static String xor(String a, String b) {
        String out = "";
        a = hextoBin(a);
        b = hextoBin(b);

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i))
                out += "0";
            else
                out += "1";
        }
        return binToHex(out);
    }

    public static String leftShift(String input, int bitNumber) {
        input = hextoBin(input);
        String p = input.substring(0, bitNumber);
        String q = input.substring(bitNumber);
        input = q + p;
        return binToHex(input);
    }

    public static String[] makeKeysForRounds(String key) {
        String keys[] = new String[16];
        key = permute(key, Permutations.removeParityBitsPermutation);
        for (int i = 0; i < 16; i++) {
            String p1 = leftShift(key.substring(0, 7), Permutations.bitsToShift[i]);
            String p2 = leftShift(key.substring(7, 14), Permutations.bitsToShift[i]);
            key = p1 + p2;
            keys[i] = permute(key, Permutations.nextUsedKeyPermutation);
        }
        return keys;
    }

    public static String crypt(String input, String key) {
        String[] keys = makeKeysForRounds(key);
        input = permute(input, Permutations.initialPermutation);
        for (int i = 0; i < 16; i++) {
            input = iterateRound(input, keys[i]);
        }
        String p1 = input.substring(0, 8);
        String p2 = input.substring(8, 16);
        input = p2 + p1;
        input = permute(input, Permutations.inverseInitialPermutation);
        return input;
    }

    public static String deCrypt(String input, String key) {
        String[] keys = makeKeysForRounds(key);
        input = permute(input, Permutations.initialPermutation);
        Collections.reverse(Arrays.asList(keys));
        for (int i = 0; i < 16; i++) {
            input = iterateRound(input, keys[i]);
        }
        String p1 = input.substring(0, 8);
        String p2 = input.substring(8, 16);
        input = p2 + p1;
        input = permute(input, Permutations.inverseInitialPermutation);
        return input;
    }

    public static void meetInTheMiddle(String plaintext1, String ciphertext1, String plaintext2, String ciphertext2) {
        String keys[] = new String[256];
        String resultSet1[]= new String[256];
        String resultSet2[]=new String[256];
        int count=0;
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++) {
                String s=Integer.toHexString(i)+Integer.toHexString(j);
                s += s;
                s += s;
                s += s;
                keys[count]=s;
                resultSet1[count]=crypt(plaintext1,s);
                count++;
            }
        for(int i=0;i<256;i++)
        {
            resultSet2[i]=deCrypt(ciphertext2,keys[i]);
            for(int j=0;j<256;j++)
            {
                if(resultSet2[i].compareTo(resultSet1[j])==0)
                {
                    System.out.println("Possible key pair: ");
                    {
                        System.out.println(keys[j]);
                        System.out.println(keys[i]);
                    }
                }
            }
        }


    }

    public static void main(String[] args) {
       String key1=constructInitialKey();
       String key2=constructInitialKey();
       String plaintext1="ABCDEF987654321F";
       String plaintext2= crypt(plaintext1,key1);
       String criptotext=crypt(plaintext2,key2);
       meetInTheMiddle(plaintext1,key1,plaintext2,criptotext);
        System.out.println();
        System.out.println("Actual results : ");
        System.out.println(plaintext1);
        System.out.println(plaintext2);
        System.out.println(criptotext);
        plaintext2=deCrypt(criptotext,key2);
        plaintext1=deCrypt(plaintext2,key1);
        System.out.println(plaintext1.toUpperCase());
        System.out.println("The keys:");
        System.out.println(key1);
        System.out.println(key2);





    }
}
