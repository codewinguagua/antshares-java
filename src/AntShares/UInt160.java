package AntShares;

public class UInt160 extends UIntBase implements Comparable<UInt160>
{
    private static final long serialVersionUID = -8298908044339492750L;

    public static final UInt160 Zero = new UInt160();

    public UInt160()
    {
        this(null);
    }

    public UInt160(byte[] value)
    {
        super(20, value);
    }

    @Override
    public int compareTo(UInt160 other)
    {
        byte[] x = ToArray();
        byte[] y = other.ToArray();
        for (int i = x.length - 1; i >= 0; i--)
        {
            if (x[i] > y[i])
                return 1;
            if (x[i] < y[i])
                return -1;
        }
        return 0;
    }

    public static UInt160 Parse(String value)
    {
        if (value == null)
            throw new NullPointerException();
        if (value.startsWith("0x"))
            value = value.substring(2);
        if (value.length() != 40)
            throw new FormatException();
        byte[] v = Helper.HexToBytes(value);
        return new UInt160(Helper.reverse(v));
    }

    public static boolean TryParse(String s, UInt160 result)
    {
        try {
            UInt160 v = Parse(s);
            result.data_bytes = v.data_bytes;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean greaterThan(UInt160 left, UInt160 right)
    {
        return left.compareTo(right) > 0;
    }

    public static boolean greaterOrEqual(UInt160 left, UInt160 right)
    {
        return left.compareTo(right) >= 0;
    }

    public static boolean lessThan(UInt160 left, UInt160 right)
    {
        return left.compareTo(right) < 0;
    }

    public static boolean lessOrEqual(UInt160 left, UInt160 right)
    {
        return left.compareTo(right) <= 0;
    }
}