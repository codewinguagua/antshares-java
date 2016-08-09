package AntShares;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 精确到10^-8的64位定点数，将舍入误差降到最低。
 * 通过控制乘数的精度，可以完全消除舍入误差。
 */
public class Fixed8 implements Comparable<Fixed8>, Serializable // TODO IFormattable
{
	private static final long serialVersionUID = 6881908032750414861L;
	private static final long D = 100000000L;
    long value;

    public static final Fixed8 MaxValue = new Fixed8(Long.MAX_VALUE);

    public static final Fixed8 MinValue = new Fixed8(Long.MIN_VALUE);

    public static final Fixed8 One = new Fixed8(D);

    public static final Fixed8 Satoshi = new Fixed8(1);

    public static final Fixed8 Zero = new Fixed8(0);

    public Fixed8(long data)
    {
        this.value = data;
    }

    public Fixed8 Abs()
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

    public static Fixed8 FromDecimal(double val)
    {
        val *= D;
        if (val < Long.MIN_VALUE || val > Long.MAX_VALUE)
            throw new OverflowException();
        return new Fixed8((long)val);
    }

    public long GetData() { return value; }

    @Override
    public int hashCode()
    {
        return Long.valueOf(value).hashCode();
    }

    public static Fixed8 Max(Fixed8 first, Fixed8[] others)
    {
        for (Fixed8 other : others)
        {
            if (first.compareTo(other) < 0)
                first = other;
        }
        return first;
    }

    public static Fixed8 Min(Fixed8 first, Fixed8[] others)
    {
        for (Fixed8 other : others)
        {
            if (first.compareTo(other) > 0)
                first = other;
        }
        return first;
    }

    public static Fixed8 Parse(String s)
    {
        return FromDecimal(Double.parseDouble(s));
    }

    @Override
    public String toString()
    {
        return Double.valueOf(decimal()).toString();
    }

    public String toString(String format)
    {
    	NumberFormat nf = new DecimalFormat(format);
        return nf.format(decimal());
    }

// TODO
//    public string ToString(string format, IFormatProvider formatProvider)
//    {
//        return ((decimal)this).ToString(format, formatProvider);
//    }

    public static boolean TryParse(String s, Fixed8 result)
    {
    	double d;
    	try {
    		d = Double.parseDouble(s);
    	} catch (NumberFormatException nfe) {
    		return false;
    	}
        d *= D;
        if (d < Long.MIN_VALUE || d > Long.MAX_VALUE)
        {
            return false;
        }
        result = new Fixed8((long)d);
        return true;
    }

    // TODO Use double to replace decimal. It might not work.
    public double decimal()
    {
        return value / (double)D;
    }

    public long toLong()
    {
        return value / D;
    }

    public static boolean isEqual(Fixed8 x, Fixed8 y)
    {
        return x.equals(y);
    }

    public static boolean isNotEqual(Fixed8 x, Fixed8 y)
    {
        return !x.equals(y);
    }

    public static boolean isGreaterThan(Fixed8 x, Fixed8 y)
    {
        return x.compareTo(y) > 0;
    }

    public static boolean isLessThan(Fixed8 x, Fixed8 y)
    {
        return x.compareTo(y) < 0;
    }

    public static boolean isGreaterOrEqual(Fixed8 x, Fixed8 y)
    {
        return x.compareTo(y) >= 0;
    }

    public static boolean isLessOrEqual(Fixed8 x, Fixed8 y)
    {
        return x.compareTo(y) <= 0;
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

    public static Fixed8 Multiply(Fixed8 x, long y)
    {
        x.value *= y;
        return x;
    }

    public static Fixed8 Divide(Fixed8 x, long y)
    {
        x.value /= y;
        return x;
    }

    public static Fixed8 Add(Fixed8 x, Fixed8 y)
    {
    	// TODO checked(...);
        x.value = x.value + y.value;
        return x;
    }

    public static Fixed8 Subtract(Fixed8 x, Fixed8 y)
    {
    	// TODO checked(...);
        x.value = x.value - y.value;
        return x;
    }

    public static Fixed8 Minus(Fixed8 value)
    {
        value.value = -value.value;
        return value;
    }
}