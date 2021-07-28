package org.example;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class App
{
   static String h0 = "01100111010001010010001100000001";
   static String h1 = "11101111110011011010101110001001";
   static String h2 = "10011000101110101101110011111110";
   static String h3 = "00010000001100100101010001110110";
   static String h4 = "11000011110100101110000111110000";


    public static String hextoBin(String input) {
    int n = (int) input.length() * 4;
    String s=new BigInteger(input,16).toString(2);
    for(int i=s.length();i<n;i++) {
        s = '0' + s;
    }
    return s;
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
    public static String stringTo8Bin(String string,int addOne)
    {
        char[] input;
        String output="";
        String temp="";
        input=string.toCharArray();
        for(char c:input)
        {
          int tmp= Integer.valueOf(c);
          temp=new BigInteger(String.valueOf(tmp)).toString(2);
          while(temp.length()!=8)
          {
              temp="0"+temp;
          }

          output+=temp;

        }
        if(addOne==1)
        return output+"1";
        else
            return output;
    }
    public static String[] split512(String input)
    {
        return input.split("(?<=\\G................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................)");
    }
    public static String[] split32(String input)
    {
        return input.split("(?<=\\G................................)");
    }
    public static Vector<String> make32BitChunks(String  input)
    {
      Vector<String> output=new Vector<String>();

          String[] temp=split32(input);
          for(String p:temp)
          {
              output.add(p);
          }
      return output;
    }
    public static String xor(String a, String b) {
        String out = "";
        while(a.length()<max(a,b))
            a="0"+a;
        while(b.length()<max(a,b))
            b="0"+b;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i))
                out += "0";
            else
                out += "1";
        }
        return out;
    }
    public static int max(String a,String b)
    {
        return a.length()>b.length()?a.length():b.length();
    }
    public static void equiv(String a,String b)
    {

    }
    public static String and(String a, String b) {
        String out = "";
        while(a.length()<max(a,b))
            a="0"+a;
        while(b.length()<max(a,b))
            b="0"+b;
        for (int i = 0; i < max(a,b); i++) {
            if ((a.charAt(i) == '1')&&(b.charAt(i)=='1'))
                out += "1";
            else
                out += "0";
        }
        return out;
    }

    public static String or(String a, String b) {
        String out = "";
        while(a.length()<max(a,b))
            a="0"+a;
        while(b.length()<max(a,b))
            b="0"+b;
        for (int i = 0; i < a.length(); i++) {
            if ((a.charAt(i) == '1')||(b.charAt(i)=='1'))
                out += "1";
            else
                out += "0";
        }
        return out;
    }
    public static String not(String a) {
        String out = "";
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '1')
                out += "0";
            else
                out += "1";
        }
        return out;
    }
    public static String trunc(String input,int number)
    {
        while(input.length()>number)
        {
            input=input.substring(1);

        }
        return input;
    }
    public static String add(String a,String b) {

        String out = "";
        int len=max(a,b);
        String x=new BigInteger(a,2).toString(10);
        String y=new BigInteger(b,2).toString(10);
        BigInteger c=new BigInteger(x).add(new BigInteger(y));
        out=c.toString(2);
        while(out.length()<len)
        {
            out="0"+out;

        }

        return out;
    }

    public static String moveLeft(String input,int numOfBits)
    {


        String right=input.substring(0,numOfBits);
        String left=input.substring(numOfBits);
        return left+right;
    }

    public static void extend32BitChunksAndHash(Vector<String> input)
    {
        String[] strings = input.toArray(new String[input.size()+64]);
        for(int i=16;i<80;i++)
        {
           String A = strings[i - 3];
           String B = strings[i - 8];
           String C = strings[i - 14];
           String D = strings[i - 16];
           String xorA = xor(A, B);
           String xorB = xor(xorA, C);
           String xorC = xor(xorB, D);
           String result=moveLeft(xorC,1);
           strings[i]=result;
        }
        String a = h0;
        String b=  h1;
        String c = h2;
        String d = h3;
        String e = h4;
        for(int i=0;i<80;i++)
        {
            String f="";
            String k="";
            if(i<20)
            {
                f=or(and(b,c),and(not(b),d));
                k="01011010100000100111100110011001";
            }
            else
                if((i<40)&&(i>19))
                {

                    f=xor(xor(b,c),d);
                    k="01101110110110011110101110100001";
                }
                else
                    if((i<60)&&(i>39))
                    {

                        f=or(or(and(c,d),and(b,d)),and(b,c));
                        k="10001111000110111011110011011100";
                    }
                    else
                    {

                        f=xor(xor(b,c),d);
                        k="11001010011000101100000111010110";
                    }
                    String tmp=add(add(add(add((moveLeft(a,5)),f),e),k),strings[i]);
                    tmp=trunc(tmp,32);
                    e = d;
                    d = c;
                    c = moveLeft(b, 30);
                    b = a;
                    a = tmp;

        }
       h0= trunc(add(h0,a),32);
       h1= trunc(add(h1,b),32);
       h2= trunc(add(h2,c),32);
       h3=trunc(add(h3,d),32);
       h4=trunc(add(h4,e),32);


    }
    public static String[] make448BitChunks(String input)
    {
        String inputLen="";

        inputLen=nrOfInputBitsToBinary(input);
        input=padString512(stringTo8Bin(input,1));
        input+=inputLen;

        String[] parts=split512(input);

        return parts;
    }
    public static  String padString512(String string)
    {
        while(string.length()%512!=448)
        {
            string+="0";
        }
        return string;

    }
    public static String padString64(String string)
    {
        while(string.length()!=64)
        {
            string="0"+string;
        }
        return string;
    }
    public static String nrOfInputBitsToBinary(String string)
    {
      int temp=Integer.valueOf(string.length()*8);
      String output=padString64(new BigInteger(String.valueOf(temp)).toString(2));
      return  output;
    }
    public static String randomString() {

        int leftLimit = 65;
        int rightLimit = 90;
        int targetStringLength = 20;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
    public static String SHA1(String input)
    {
        h0 = "01100111010001010010001100000001";
        h1 = "11101111110011011010101110001001";
        h2 = "10011000101110101101110011111110";
        h3 = "00010000001100100101010001110110";
        h4 = "11000011110100101110000111110000";
        String[] chunks512=make448BitChunks(input);
        for(String chunk512:chunks512)
        {
            Vector<String> chunks32=make32BitChunks(chunk512);
            extend32BitChunksAndHash(chunks32);
        }

          h0=trunc(h0,32);
          h1=trunc(h1,32);
          h2=trunc(h2,32);
          h3=trunc(h3,32);
          h4=trunc(h4,32);

        return binToHex(h0)+binToHex(h1)+binToHex(h2)+binToHex(h3)+binToHex(h4);
        //return binToHex(h0);
    }
    public static void birthDayAttack()
    {   HashMap<String,String> map =new HashMap<String,String >();
        HashMap<String,Integer> temp=new HashMap<String, Integer>();
        String input=randomString();

        BigInteger b=new BigInteger("0");
        boolean running=true;
        while (running) {

            b = b.add(BigInteger.ONE);
            String aux = SHA1(input);

            if (map.containsKey(aux)) {
                System.out.println(map.get(aux));
                System.out.println(input);
                running = false;
            }
            map.put(aux, input);
            temp.put(input,1);

            boolean unique=false;
            while(!unique){
            String nextInput = randomString();
            if(!temp.containsKey(nextInput))
            {
                input=nextInput;
                unique=true;
            }
            }
        }

        }
        public static void hamming(String a,String b)
        {
            a=hextoBin(a);
            b=hextoBin(b);
            int count=0;
            char[] a1=a.toCharArray();
            char[] b1=b.toCharArray();
            for(int i=0;i<a1.length;i++)
            {
                if(a1[i]!=b1[i])count++;
            }
            System.out.println(a);
            System.out.println(b);
            System.out.println(count);
        }



    public static void main( String[] args )
    {



        //System.out.println(SHA1("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq"));
        //EQXWRUGRSKOBAMCZOSLK
        //YCTZXIYNSHTDHIHWWEFW

          birthDayAttack();

          String input1=SHA1("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");
          String input2=SHA1("bbcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");
          System.out.println(input1);
          System.out.println(input2);
          hamming(input1,input2);
      //  System.out.println(SHA1("EQXWRUGRSKOBAMCZOSLK"));
      //  System.out.println(SHA1("YCTZXIYNSHTDHIHWWEFW"));







    }
}
