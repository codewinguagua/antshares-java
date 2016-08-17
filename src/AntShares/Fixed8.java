package AntShares;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 精确到10^-8的64位定点数，将舍入误差降到最低。
 * 通过控制乘数的精度，可以完全消除舍入误差。
 */
public class Fixed8 implements Comparable<Fixed8>, Serializable
{
    private static final long serialVersionUID = 6881908032750414861L;
    private static final long D = 100000000L;
    long value;

    public static final Fixed8 MAX_VALUE = new Fixed8(Long.MAX_VALUE);

    public static final Fixed8 MIN_VALUE = new Fixed8(Long.MIN_VALUE);

    public static final Fixed8 ONE = new Fixed8(D);

    public static final Fixed8 SATOSHI = new Fixed8(1);

    public static final Fixed8 ZERO = new Fixed8(0);

    public Fixed8(long data)
    {
        this.value = data;
    }

    public Fixed8 abs()
    {
        if (value >= 0) return this;
        return new Fixed8(-value);
    }

    @Override
    public int compareTo(Fixed8 other)
    {
        return Long.compare(value, other.value);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Fixed8)) return false;
        return value == ((Fixed8) obj).value;
    }

    public static Fixed8 fromDecimal(BigDecimal val)
    {
        return new Fixed8(val.multiply(new BigDecimal(D)).longValueExact());
    }

    public long getData() { return value; }

    @Override
    public int hashCode()
    {
        return Long.valueOf(value).hashCode();
    }

    public static Fixed8 max(Fixed8 first, Fixed8 ...others)
    {
        for (Fixed8 other : others)
        {
            if (first.compareTo(other) < 0)
                first = other;
        }
        return first;
    }

    public static Fixed8 min(Fixed8 first, Fixed8 ...others)
    {
        for (Fixed8 other : others)
        {
            if (first.compareTo(other) > 0)
                first = other;
        }
        return first;
    }

    public static Fixed8 parse(String s)
    {
        return fromDecimal(new BigDecimal(s));
    }

    @Override
    public String toString()
    {
        BigDecimal v = new BigDecimal(value);
        v = v.divide(new BigDecimal(D), 8, BigDecimal.ROUND_UNNECESSARY);
        return v.toPlainString();
    }

    public static boolean tryParse(String s, Fixed8 result)
    {
        try
        {
            BigDecimal val = new BigDecimal(s);
            result.value = val.longValueExact();
            return true;
        }
        catch(NumberFormatException | ArithmeticException ex)
        {
            return false;
        }
    }

    public long toLong()
    {
        return value / D;
    }

//TODO
//    public static Fixed8 Multiply(Fixed8 x, Fixed8 y)
//    {
//        const ulong QUO = (1ul << 63) / (D >> 1);
//        const ulong REM = (1ul << 63) % (D >> 1);
//        int sign = Math.Sign(x.value) * Math.Sign(y.value);
//        ulong ux = (ulong)Math.Abs(x.value);
//        ulong uy = (ulong)Math.Abs(y.value);
//        ulong xh = ux >> 32;
//        ulong xl = ux & 0x00000000fffffffful;
//        ulong yh = uy >> 32;
//        ulong yl = uy & 0x00000000fffffffful;
//        ulong rh = xh * yh;
//        ulong rm = xh * yl + xl * yh;
//        ulong rl = xl * yl;
//        ulong rmh = rm >> 32;
//        ulong rml = rm << 32;
//        rh += rmh;
//        rl += rml;
//        if (rl < rml)
//            ++rh;
//        if (rh >= D)
//            throw new OverflowException();
//        ulong r = rh * QUO + (rh * REM + rl) / D;
//        x.value = (long)r * sign;
//        return x;
//    }

    public static Fixed8 multiply(Fixed8 x, long y)
    {
        return new Fixed8(x.value * y);
    }

    public static Fixed8 divide(Fixed8 x, long y)
    {
        return new Fixed8(x.value / y);
    }

    public static Fixed8 add(Fixed8 x, Fixed8 y)
    {
        // TODO checked(...);
        return new Fixed8(x.value + y.value);
    }

    public static Fixed8 subtract(Fixed8 x, Fixed8 y)
    {
        // TODO checked(...);
        return new Fixed8(x.value - y.value);
    }

    public static Fixed8 minus(Fixed8 value)
    {
        return new Fixed8(-value.value);
    }
}