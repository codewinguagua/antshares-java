package AntShares.IO.Json;

import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;

public class JString extends JObject
{
    private String value;
    public String getValue() { return value; }

    public JString(String val)
    {
        if (value == null)
            throw new NullPointerException();
        this.value = val;
    }

    @Override
    public boolean AsBoolean()
    {
    	Collection<String> falseValues = new HashSet<String>();
    	falseValues.add("0");
        falseValues.add("0");
        falseValues.add("f");
        falseValues.add("false");
        falseValues.add("n");
        falseValues.add("no");
        falseValues.add("off");
    	return ! falseValues.contains(value.toLowerCase());
    }

// TODO
//    public override T AsEnum<T>(bool ignoreCase = false)
//    {
//        try
//        {
//            return (T)Enum.Parse(typeof(T), Value, ignoreCase);
//        }
//        catch
//        {
//            throw new InvalidCastException();
//        }
//    }
//
    @Override
    public double AsNumber()
    {
        return Double.parseDouble(value);
    }

    @Override
    public String AsString()
    {
        return value;
    }

    @Override
    public boolean CanConvertTo(Class<?> type)
    {
        if (type.equals(boolean.class))
            return true;
        // TODO
//        if (type.IsEnum && Enum.IsDefined(type, Value))
//            return true;
        if (type.equals(double.class))
            return true;
        if (type.equals(String.class))
            return true;
        return false;
    }

    public static JString Parse(Reader reader)
    {
    	// TODO
//        SkipSpace(reader);
//        char[] buffer = new char[4];
//        char firstChar = (char)reader.Read();
//        if (firstChar != '\"' && firstChar != '\'') throw new FormatException();
//        StringBuilder sb = new StringBuilder();
//        while (true)
//        {
//            char c = (char)reader.Read();
//            if (c == 65535) throw new FormatException();
//            if (c == firstChar) break;
//            if (c == '\\')
//            {
//                c = (char)reader.Read();
//                if (c == 'u')
//                {
//                    reader.Read(buffer, 0, 4);
//                    c = (char)short.Parse(new string(buffer), NumberStyles.HexNumber);
//                }
//            }
//            sb.Append(c);
//        }
//        return new JString(sb.ToString());
    	return new JString("");
    }

    @Override
    public String toString()
    {
    	// TODO
        //return HttpUtility.JavaScriptStringEncode(Value, true);
    	return value;
    }
}