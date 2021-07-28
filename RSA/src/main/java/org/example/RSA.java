package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class RSA {
    BigInteger p;
    BigInteger q;
    BigInteger n;
    BigInteger o;
    BigInteger e;
    BigInteger d;
    Random rnd=new Random();
    private BigInteger randomNumber(BigInteger n)
    {  while (true){
        BigInteger maxLimit = n;
        BigInteger minLimit = new BigInteger("1");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);
        if(n.gcd(res).compareTo(BigInteger.ONE)==0)
       return res;
    }

    }
    private BigInteger getD(BigInteger o,BigInteger e)
    {
        for(BigInteger i=BigInteger.valueOf(2);i.compareTo(o)<0;i=i.add(BigInteger.ONE))
        {
           if(e.multiply(i).mod(o).compareTo(BigInteger.ONE)==0)
           {
               return i;
           }
        }
        return BigInteger.ONE;
    }
    public RSA(boolean max32)
    {



        p=BigInteger.probablePrime(512,rnd);
        q=BigInteger.probablePrime(512,rnd);
        n=p.multiply(q);
        o=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        if(max32!=true)
        { e=randomNumber(o);
        d=e.modInverse(o);}
        else
        do {
            do {
                d = new BigInteger(32, 100, rnd);
            }while((d.multiply(BigInteger.valueOf(3))).pow(4).compareTo(n)>=0);
            e = d.modInverse(o);
        }while (e.gcd(o).compareTo(BigInteger.valueOf(1))!=0);

    }

    public BigInteger Encrypt(BigInteger number)
    {
       BigInteger result=number.modPow(e,n);
       return result;
    }
    public BigInteger Decrypt(BigInteger number)
    {
        BigInteger result=number.modPow(d,n);
        long tEnd = System.nanoTime();

        return result;
    }
    public BigInteger Decrypt32(BigInteger number)
    {
        BigInteger n1=d.mod(p.subtract(BigInteger.ONE));
        BigInteger n2=d.mod(q.subtract(BigInteger.ONE));
        BigInteger inverse=p.modInverse(q);
        BigInteger x1=number.mod(p);

        x1=x1.modPow(n1,p);
        BigInteger x2=number.mod(q);
        x2=x2.modPow(n2,q);
        BigInteger x=x1.add(p.multiply(x2.subtract(x1).multiply(inverse).mod(q)));
        return x;
    }

    private  boolean tryAttack(BigInteger l,BigInteger d){
        if (l.compareTo(BigInteger.valueOf(0))>0) {
            if ((((e.multiply(d)).subtract(BigInteger.valueOf(1))).mod(l)).compareTo(BigInteger.valueOf(0)) == 0) {
                BigInteger tempFI = ((e.multiply(d)).subtract(BigInteger.valueOf(1))).divide(l);
                BigInteger minusB = n.add(BigInteger.valueOf(1)).subtract(tempFI);
                BigInteger delta = minusB.pow(2).add(n.multiply(BigInteger.valueOf(-4)));
                BigInteger sqrtOfDelta = delta.sqrt();
                BigInteger x1 = (minusB.add(sqrtOfDelta)).divide(BigInteger.valueOf(2));
                BigInteger x2 = (minusB.subtract(sqrtOfDelta)).divide(BigInteger.valueOf(2));
                if (x1.multiply(x2).equals(n)){
                    return true;
                }
            }
        }
        return false;
    }

    public BigInteger wienerAttack()
    {
        Vector<BigInteger> Q=new Vector<BigInteger>();
        Vector<BigInteger> alpha=new Vector<BigInteger>();
        Vector<BigInteger> beta=new Vector<BigInteger>();
        BigInteger q=e.divide(n);
        BigInteger r1=e.mod(n);
        BigInteger aux;
        BigInteger r2;
        Q.add(q);
        alpha.add(q);
        beta.add(BigInteger.ONE);
       if(tryAttack(q,beta.elementAt(0)))
       {
           return beta.elementAt(0);
       }

        q=n.divide(r1);
        r2=n.mod(r1);
        Q.add(q);
        alpha.add(Q.elementAt(0).multiply(Q.elementAt(1)).add(BigInteger.ONE));
        beta.add(Q.elementAt(1));
        if(tryAttack(q,beta.elementAt(1)))
        {
           beta.elementAt(1);
        }

        int i=2;
        while(true)
        {

           q=r1.divide(r2);
           Q.add(q);
           aux=r1.mod(r2);
           r1=r2;
           r2=aux;
           alpha.add(Q.elementAt(i).multiply(alpha.elementAt(i-1)).add(alpha.elementAt(i-2)));
           beta.add(Q.elementAt(i).multiply(beta.elementAt(i-1)).add(beta.elementAt(i-2)));

           if(tryAttack(alpha.elementAt(i),beta.elementAt(i)))
           {
               return beta.elementAt(i);

           }
           i++;
        }
    }


}
