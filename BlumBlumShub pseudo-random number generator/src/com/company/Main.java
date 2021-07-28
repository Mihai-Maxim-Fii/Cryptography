package com.company;


import java.math.BigInteger;

public class Main {
    static int jacobi(BigInteger a,BigInteger b)
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

    public static void main(String[] args) {

        BBSJ bbs=new BBSJ();
        bbs.jacobiGenerate();

    }
}
