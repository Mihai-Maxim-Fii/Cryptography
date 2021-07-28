package com.company;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;

public class Main {
    public static int transition(int[] registers, int[] booleanConstants) {
        int newRegister = 0;
        for (int i = 0; i < registers.length; i++) {
            newRegister += registers[i] * booleanConstants[i];
        }
        newRegister %= 2;
        System.arraycopy(registers, 1, registers, 0, registers.length - 1);
        registers[registers.length - 1] = newRegister;
        return newRegister;
    }

    public static void display(int[] registers) {
        for (int v : registers) {
            System.out.print(v + " ");
        }
        System.out.println();

    }

    public static int[] randomBitString(int length) {
        byte[] array = new byte[length];
        int[] randomString = new int[array.length];
        boolean ok = false;
        while (!ok) {
            ok = true;
            new Random().nextBytes(array);
            int count = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] % 2 == 1) {
                    randomString[i] = 1;
                } else {
                    randomString[i] = 0;
                    count++;
                }
            }
            if (count == array.length) ok = false;
        }
        return randomString;
    }

    public static void setCustomConstants(int[] booleanConstants) {
        System.out.println("There are " + booleanConstants.length + " positions");
        System.out.println("If you want to assign the constants type the positions you want true,or type random:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.compareTo("random") != 0) {
            input += " ";
            String[] tokens = input.split(" ");
            for (String s : tokens) {
                int position = Integer.parseInt(s);
                booleanConstants[position] = 1;
            }
        } else
            System.arraycopy(randomBitString(booleanConstants.length), 0, booleanConstants, 0, booleanConstants.length);
    }

    public static int setNumberOfRegisters() {
        int numOfRegisters;
        System.out.println("Type how many registers you want:");
        Scanner scanner = new Scanner(System.in);
        numOfRegisters = scanner.nextInt();
        return numOfRegisters;
    }

    public static void generateNumbers(int[] registers, int[] booleanConstants) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bit length of each number:");
        int keyLength = scanner.nextInt();
        System.out.println("How many numbers:");
        int howManyNumbers=scanner.nextInt();
        for(int k=0;k<howManyNumbers;k++) {
            BigInteger bigInteger=new BigInteger("0");
            Vector<Integer> key = new Vector<Integer>();
            for (int i = 0; i < keyLength; i++) {
                key.add(transition(registers, booleanConstants));
            }
            Collections.reverse(key);
            int b=0;
            for (int i : key) {
                if(i==1)
                {
                    bigInteger=bigInteger.add(BigInteger.TWO.pow(b));
                }
                b++;
            }
            System.out.println(bigInteger.toString());
        }

    }

    public static void main(String[] args) {

       // int[] registers = randomBitString(setNumberOfRegisters());
        int[] registers={1,0,0};
        int[] booleanConstants = new int[registers.length];
        Arrays.fill(booleanConstants, 0);
        setCustomConstants(booleanConstants);
        display(registers);
        generateNumbers(registers, booleanConstants);

	   /*
	    String text=""
	    String key="1234";

	    RC4 test=new RC4(text.toCharArray(),key.toCharArray());
        char[] result=test.getOutput();
        for(char c:result)
        {
            System.out.print(c);
        }
        System.out.println();
        RC4 test1=new RC4(result,key.toCharArray());
        char[] result2=test1.getOutput();
        for(char d:result2)
        {
            System.out.print(d);
        }
    }

	    */
    }
}
