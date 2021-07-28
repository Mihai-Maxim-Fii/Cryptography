package com.company;

import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class BBSJ {
    private  BigInteger p,q,m,seed;

    private BigInteger getViablePrime()
    {
        BigInteger prime;
        BigInteger four=new BigInteger("4");
        BigInteger three=new BigInteger("3");
        prime=BigInteger.probablePrime(512,new Random());
        while(prime.mod(four).compareTo(three)!=0)
        {
            prime=BigInteger.probablePrime(512,new Random());
        }
        return prime;
    }
   private void setPQMS()
    {
        p=getViablePrime();
        q=getViablePrime();
        while(p.compareTo(q)==0)
        {
            q=getViablePrime();
        }
        m=p.multiply(q);
        Date today= Time.valueOf(LocalTime.now());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        seed=new BigInteger(String.valueOf(calendar.getTimeInMillis()));
        seed=seed.mod(m);
    }
    private BigInteger generateNumber(int bitLength)
    {   BigInteger number=new BigInteger("0");
        for(int i=0;i<bitLength;i++)
        {
            seed= seed.pow(2).mod(m);
            if(seed.mod(BigInteger.TWO).compareTo(BigInteger.ONE)==0)
            {

               number= number.add(BigInteger.TWO.pow(i));
            }

        }
        return number;
    }
    public void bbsGenerate()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("How many bits?:");
        int myint1 = keyboard.nextInt();
        System.out.print("How many numbers?:");
        int myint2= keyboard.nextInt();
        setPQMS();
        for(int i=0;i<myint2;i++)
        {
            System.out.println(generateNumber(myint1));
        }
    }
    private BigInteger jacobiGenerateNumber(int bitLength)
    {
        BigInteger number=new BigInteger("0");
        for(int i=0;i<bitLength;i++)
        {
            seed= seed.add(BigInteger.valueOf(i)).mod(m);
            if(jacobi(seed,m)==1)
            {

                number= number.add(BigInteger.TWO.pow(i));
            }

        }
        return number;

    }
    private int jacobi(BigInteger a,BigInteger b)
    {
        if(b.compareTo(BigInteger.valueOf(0))==0)return 0;
        if(b.mod(BigInteger.TWO).compareTo(BigInteger.valueOf(0))==0)return 0;
        BigInteger j=new BigInteger("1");
        if(a.compareTo(BigInteger.valueOf(2))<0)
        {
            a=a.multiply(BigInteger.valueOf(-1));
            if(b.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3))==0)
                j=j.multiply(BigInteger.valueOf(-1));
        }

        while(a.compareTo(BigInteger.valueOf(0))!=0) {
            while (a.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
                a = a.divide(BigInteger.valueOf(2));
                if ((b.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(3)) == 0)
                        || (b.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(5)) == 0)) {
                    j = j.multiply(BigInteger.valueOf(-1));
                }
            }
            BigInteger c = a;
            a = b;
            b = c;
            if ((a.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0) && (b.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0))
                j = j.multiply(BigInteger.valueOf(-1));
            a = a.mod(b);
        }

        if (b.compareTo(BigInteger.valueOf(1)) == 0) return Integer.parseInt(j.toString());
        else
            return 0;
    }
    public void jacobiGenerate()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("How many bits?:");
        int myint1 = keyboard.nextInt();
        System.out.print("How many numbers?:");
        int myint2= keyboard.nextInt();
        setPQMS();
        for(int i=0;i<myint2;i++)
        {
            System.out.println(jacobiGenerateNumber(myint1));
        }
    }


}
