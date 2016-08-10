package AntShares;

public class UInt256 extends UIntBase implements Comparable<UInt256>
{
	private static final long serialVersionUID = -5460860333759761748L;

	public static final UInt256 Zero = new UInt256();

    public UInt256()
    {
    	this(null);
    }

    public UInt256(byte[] value)
    {
        super(32, value);
    }

    @Override
    public int compareTo(UInt256 other)
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

    public static UInt256 Parse(String s)
    {
        if (s == null)
            throw new NullPointerException();
        if (s.startsWith("0x"))
            s = s.substring(2);
        if (s.length() != 64)
            throw new FormatException(String.format("字符串\"{0}\"无法识别为正确的UInt256。", s));
        byte[] v = Helper.HexToBytes(s);
        return new UInt256(Helper.reverse(v));
    }

    public static boolean TryParse(String s, UInt256 result)
    {
    	try {
    		UInt256 v = Parse(s);
    		result.data_bytes = v.data_bytes;
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }

    public static boolean greaterThan(UInt256 left, UInt256 right)
    {
        return left.compareTo(right) > 0;
    }

    public static boolean greaterOrEqual(UInt256 left, UInt256 right)
    {
        return left.compareTo(right) >= 0;
    }

    public static boolean lessThan(UInt256 left, UInt256 right)
    {
        return left.compareTo(right) < 0;
    }

    public static boolean lessOrEqual(UInt256 left, UInt256 right)
    {
        return left.compareTo(right) <= 0;
    }
}