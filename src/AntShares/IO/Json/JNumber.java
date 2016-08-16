package AntShares.IO.Json;

import java.io.Reader;

public class JNumber extends JObject
{
    private double value;
    public double getValue() { return value; }

    public JNumber(double val)
    {
        this.value = val;
    }

    @Override
    public boolean AsBoolean()
    {
        if (value == 0)
            return false;
        return true;
    }

// TODO
//    public override T AsEnum<T>(bool ignoreCase = false)
//    {
//        Type t = typeof(T);
//        if (!t.IsEnum)
//            throw new InvalidCastException();
//        if (t.GetEnumUnderlyingType() == typeof(byte))
//            return (T)Enum.ToObject(t, (byte)Value);
//        if (t.GetEnumUnderlyingType() == typeof(int))
//            return (T)Enum.ToObject(t, (int)Value);
//        if (t.GetEnumUnderlyingType() == typeof(long))
//            return (T)Enum.ToObject(t, (long)Value);
//        if (t.GetEnumUnderlyingType() == typeof(sbyte))
//            return (T)Enum.ToObject(t, (sbyte)Value);
//        if (t.GetEnumUnderlyingType() == typeof(short))
//            return (T)Enum.ToObject(t, (short)Value);
//        if (t.GetEnumUnderlyingType() == typeof(uint))
//            return (T)Enum.ToObject(t, (uint)Value);
//        if (t.GetEnumUnderlyingType() == typeof(ulong))
//            return (T)Enum.ToObject(t, (ulong)Value);
//        if (t.GetEnumUnderlyingType() == typeof(ushort))
//            return (T)Enum.ToObject(t, (ushort)Value);
//        throw new InvalidCastException();
//    }

    @Override
    public double AsNumber()
    {
        return value;
    }

    @Override
    public String AsString()
    {
        return String.valueOf(value);
    }

    @Override
    public boolean CanConvertTo(Class<?> type)
    {
        if (type.equals(boolean.class))
            return true;
// TODO
//        if (type.IsEnum && Enum.IsDefined(type, Convert.ChangeType(Value, type.GetEnumUnderlyingType())))
//            return true;
        if (type.equals(double.class))
            return true;
        if (type.equals(String.class))
            return true;
        return false;
    }

    public static JNumber Parse(Reader reader)
    {
//    	 TODO
//        SkipSpace(reader);
//        StringBuilder sb = new StringBuilder();
//        while (true)
//        {
//            char c = (char)reader.Peek();
//            if (c >= '0' && c <= '9' || c == '.' || c == '-')
//            {
//                sb.Append(c);
//                reader.Read();
//            }
//            else
//            {
//                break;
//            }
//        }
//        return new JNumber(double.Parse(sb.ToString()));
    	return new JNumber(0);
    }

    @Override
    public String toString()
    {
        return AsString();
    }

}