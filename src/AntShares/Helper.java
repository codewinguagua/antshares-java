package AntShares;

import java.math.BigInteger;
import java.util.*;

public class Helper
{
    public static byte[] hexToBytes(String value)
    {
        if (value == null || value.length() == 0)
            return new byte[0];
        if (value.length() % 2 == 1)
            throw new IllegalArgumentException();
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
//    {
//        if (sizeInBits < 0)
//            throw new ArgumentException("sizeInBits must be non-negative");
//        if (sizeInBits == 0)
//            return 0;
//        byte[] b = new byte[sizeInBits / 8 + 1];
//        rng.GetNonZeroBytes(b);
//        if (sizeInBits % 8 == 0)
//            b[b.Length - 1] = 0;
//        else
//            b[b.Length - 1] &= (byte)((1 << sizeInBits % 8) - 1);
//        return new BigInteger(b);
//    }
    
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
    
    public static String toHexString(Iterable<Byte> value)
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
