package com.company;

import java.util.Collections;

public class RC4 {
    private char[] input;
    private char[] key;
    private char[] output;
    RC4(char[] input,char[] key)
    {
       this.input=input;
       this.key=key;
       RC4();
    }
    private void RC4()
    {   int j=0;
        int swap;
        int[] permutation=new int[256];

        for(int i=0;i<256;i++)
        {
            permutation[i]=i;
        }
        for(int i=0;i<256;i++)
        {
            j=(key[i%key.length]+permutation[i]+j)%256;
            swap=permutation[i];
            permutation[i]=permutation[j];
            permutation[j]=swap;
        }
         int x=0,y=0;
         output=new char[input.length];
        for(int i=0;i<input.length;i++)
        {
          x=i%256;
          y=(permutation[x]+y)%256;
          swap=permutation[x];
          permutation[x]=permutation[y];
          permutation[y]=swap;
          int aux=permutation[(permutation[x]+permutation[y])%256];
            output[i]=(char)(input[i]^aux);
        }

    }
     public  char[] getOutput()
    {
        return output;
    }

}
