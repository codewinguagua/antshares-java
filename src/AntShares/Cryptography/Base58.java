package AntShares.Cryptography;

import java.math.BigInteger;

import AntShares.FormatException;
import AntShares.Helper;

public class Base58
{
    /**
     *  base58编码的字母表
     */
    public static final String Alphabet = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    /**
     *  解码
     *  <param name="input">要解码的字符串</param>
     *  <returns>返回解码后的字节数组</returns>
     */
    public static byte[] Decode(String input)
    {
        BigInteger bi = BigInteger.ZERO;
        for (int i = input.length() - 1; i >= 0; i--)
        {
            int index = Alphabet.indexOf(input.charAt(i));
            if (index == -1)
                throw new FormatException();
            // TODO
            //bi += index * BigInteger.Pow(58, input.Length - 1 - i);
        }
        byte[] bytes = bi.toByteArray();
        bytes = Helper.reverse(bytes);
        boolean stripSignByte = bytes.length > 1 && bytes[0] == 0 && bytes[1] >= 0x80;
        int leadingZeros = 0;
        for (int i = 0; i < input.length() && input.charAt(i) == Alphabet.charAt(0); i++)
        {
            leadingZeros++;
        }
        byte[] tmp = new byte[bytes.length - (stripSignByte ? 1 : 0) + leadingZeros];
        System.arraycopy(bytes, stripSignByte ? 1 : 0, tmp, leadingZeros, tmp.length - leadingZeros);
        return tmp;
    }

    /**
     *  编码
     *  <param name="input">要编码的字节数组</param>
     *  <returns>返回编码后的字符串</returns>
     */
    public static String Encode(byte[] input)
    {
        //BigInteger value = new BigInteger(new byte[1].Concat(input).Reverse().ToArray());
    	byte[] tmp = new byte[input.length + 1];
    	System.arraycopy(input, 0, tmp, 1, input.length);
    	byte[] rev = Helper.reverse(tmp);
    	BigInteger value = new BigInteger(rev);

        StringBuilder sb = new StringBuilder();
        BigInteger base58 = BigInteger.valueOf(58);
        while (value.compareTo(base58) >= 0)
        {
            BigInteger mod = value.mod(base58);
            sb.insert(0, Alphabet.charAt(mod.intValue()));
            value = value.divide(base58);
        }
        sb.insert(0, Alphabet.charAt(value.intValue()));
        for (byte b : input)
        {
            if (b == 0)
                sb.insert(0, Alphabet.charAt(0));
            else
                break;
        }
        return sb.toString();
    }
}