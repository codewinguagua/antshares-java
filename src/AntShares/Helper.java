package AntShares;

import java.math.BigInteger;
import java.util.*;

public class Helper
{

    private static int BitLen(int w)
    {
        return (w < 1 << 15 ? (w < 1 << 7
            ? (w < 1 << 3 ? (w < 1 << 1
            ? (w < 1 << 0 ? (w < 0 ? 32 : 0) : 1)
            : (w < 1 << 2 ? 2 : 3)) : (w < 1 << 5
            ? (w < 1 << 4 ? 4 : 5)
            : (w < 1 << 6 ? 6 : 7)))
            : (w < 1 << 11
            ? (w < 1 << 9 ? (w < 1 << 8 ? 8 : 9) : (w < 1 << 10 ? 10 : 11))
            : (w < 1 << 13 ? (w < 1 << 12 ? 12 : 13) : (w < 1 << 14 ? 14 : 15)))) : (w < 1 << 23 ? (w < 1 << 19
            ? (w < 1 << 17 ? (w < 1 << 16 ? 16 : 17) : (w < 1 << 18 ? 18 : 19))
            : (w < 1 << 21 ? (w < 1 << 20 ? 20 : 21) : (w < 1 << 22 ? 22 : 23))) : (w < 1 << 27
            ? (w < 1 << 25 ? (w < 1 << 24 ? 24 : 25) : (w < 1 << 26 ? 26 : 27))
            : (w < 1 << 29 ? (w < 1 << 28 ? 28 : 29) : (w < 1 << 30 ? 30 : 31)))));
    }

    static int GetBitLength(BigInteger i)
    {
        byte[] b = i.toByteArray();
        return (b.length - 1) * 8 + BitLen(i.signum() > 0 ? b[b.length - 1] : 255 - b[b.length - 1]);
    }

    static int GetLowestSetBit(BigInteger i)
    {
        if (i.signum() == 0)
            return -1;
        byte[] b = i.toByteArray();
        int w = 0;
        while (b[w] == 0)
            w++;
        for (int x = 0; x < 8; x++)
            if ((b[w] & 1 << x) > 0)
                return x + w * 8;
        throw new RuntimeException();
    }

    public static byte[] HexToBytes(String value)
    {
        if (value == null || value.length() == 0)
            return new byte[0];
        if (value.length() % 2 == 1)
            throw new FormatException();
        byte[] result = new byte[value.length() / 2];
        for (int i = 0; i < result.length; i++)
            result[i] = (byte) Integer.parseInt(value.substring(i * 2, 2), 16);
        return result;
    }
    
    public static byte[] reverse(byte[] v)
    {
    	byte[] result = new byte[v.length];
    	for (int i = 0; i < v.length; i++)
    	{
    		result[i] = v[v.length - i - 1];
    	}
    	return result;
    }

    static BigInteger Mod(BigInteger x, BigInteger y)
    {
    	return x.mod(y);
    }

    static BigInteger ModInverse(BigInteger a, BigInteger n)
    {
    	return a.modInverse(n);
    }

    static BigInteger NextBigInteger(Random rand, int sizeInBits)
    {
        if (sizeInBits < 0)
            throw new IllegalArgumentException("sizeInBits must be non-negative");
	    if (sizeInBits == 0)
	        return new BigInteger("0");
	    byte[] b = new byte[sizeInBits / 8 + 1];
	    rand.nextBytes(b);
	    if (sizeInBits % 8 == 0)
	        b[b.length - 1] = 0;
	    else
	        b[b.length - 1] &= (byte)((1 << sizeInBits % 8) - 1);
	    return new BigInteger(b);
	}

// TODO
//    internal static BigInteger NextBigInteger(this RNGCryptoServiceProvider rng, int sizeInBits)
//	{
//	    if (sizeInBits < 0)
//	        throw new ArgumentException("sizeInBits must be non-negative");
//	    if (sizeInBits == 0)
//	        return 0;
//	    byte[] b = new byte[sizeInBits / 8 + 1];
//	    rng.GetNonZeroBytes(b);
//	    if (sizeInBits % 8 == 0)
//	        b[b.Length - 1] = 0;
//	    else
//	        b[b.Length - 1] &= (byte)((1 << sizeInBits % 8) - 1);
//	    return new BigInteger(b);
//	}
	
	public static Fixed8 Sum(Iterable<Fixed8> source)
	{
	    long sum = 0;
	    // TODO checked
        for (Fixed8 item : source)
        {
            sum += item.value;
        }
	    return new Fixed8(sum);
	}
	
// TODO
//	public static Fixed8 Sum<TSource>(this IEnumerable<TSource> source, Func<TSource, Fixed8> selector)
//	{
//	    return source.Select(selector).Sum();
//	}
	
	static boolean TestBit(BigInteger i, int index)
	{
		return i.testBit(index);
	}
	
	/**
	 * @param timestamp in seconds
	 * @return
	 */
	public static Date ToDateTime(int timestamp)
	{
	    return new Date(timestamp * 1000L);
	}
	
	public static Date ToDateTime(long timestamp)
	{
	    return new Date(timestamp * 1000L);
	}
	
	public static String ToHexString(Iterable<Byte> value)
	{
	    StringBuilder sb = new StringBuilder();
	    for (byte b : value)
	    {
	    	int v = (b + 256) & 0xff;
	        sb.append(Integer.toHexString(v));
	    }
        return sb.toString();
    }

    public static int ToTimestamp(Date time)
    {
        return (int) (time.getTime() / 1000);
    }

// TODO
//    static long WeightedAverage<T>(Iterable<T> source, Func<T, long> valueSelector, Func<T, long> weightSelector)
//    {
//        long sum_weight = 0;
//        long sum_value = 0;
//        foreach (T item in source)
//        {
//            long weight = weightSelector(item);
//            sum_weight += weight;
//            sum_value += valueSelector(item) * weight;
//        }
//        if (sum_value == 0) return 0;
//        return sum_value / sum_weight;
//    }

//    internal static IEnumerable<TResult> WeightedFilter<T, TResult>(this IList<T> source, double start, double end, Func<T, long> weightSelector, Func<T, long, TResult> resultSelector)
//    {
//        if (source == null) throw new ArgumentNullException(nameof(source));
//        if (start < 0 || start > 1) throw new ArgumentOutOfRangeException(nameof(start));
//        if (end < start || start + end > 1) throw new ArgumentOutOfRangeException(nameof(end));
//        if (weightSelector == null) throw new ArgumentNullException(nameof(weightSelector));
//        if (resultSelector == null) throw new ArgumentNullException(nameof(resultSelector));
//        if (source.Count == 0 || start == end) yield break;
//        double amount = source.Sum(weightSelector);
//        long sum = 0;
//        double current = 0;
//        foreach (T item in source)
//        {
//            if (current >= end) break;
//            long weight = weightSelector(item);
//            sum += weight;
//            double old = current;
//            current = sum / amount;
//            if (current <= start) continue;
//            if (old < start)
//            {
//                if (current > end)
//                {
//                    weight = (long)((end - start) * amount);
//                }
//                else
//                {
//                    weight = (long)((current - start) * amount);
//                }
//            }
//            else if (current > end)
//            {
//                weight = (long)((end - old) * amount);
//            }
//            yield return resultSelector(item, weight);
//        }
//    }
}
