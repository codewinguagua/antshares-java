package AntShares.IO.Json;

import java.io.*;
import java.util.*;

public class JObject
{
    public static final JObject Null = null;
    private Map<String, JObject> properties = new HashMap<String, JObject>();

    public JObject get(String name)
    {
        if (!properties.containsKey(name))
            return null;
        return properties.get(name);
    }

    public void set(String name, JObject value)
    {
        properties.put(name, value);
    }

    public boolean AsBoolean()
    {
        throw new UnsupportedOperationException();
    }

    public boolean AsBooleanOrDefault(boolean value)
    {
        if (!CanConvertTo(boolean.class))
            return value;
        return AsBoolean();
    }

    public <T> T AsEnum(boolean ignoreCase)
    {
        throw new UnsupportedOperationException();
    }

    public <T> T AsEnumOrDefault(T value, boolean ignoreCase)
    {
        if (!CanConvertTo(value.getClass()))
            return value;
        return AsEnum(ignoreCase);
    }

    public double AsNumber()
    {
        throw new UnsupportedOperationException();
    }

    public double AsNumberOrDefault(double value)
    {
        if (!CanConvertTo(double.class))
            return value;
        return AsNumber();
    }

    public String AsString()
    {
        throw new UnsupportedOperationException();
    }

    public String AsStringOrDefault(String value)
    {
        if (!CanConvertTo(String.class))
            return value;
        return AsString();
    }

    public boolean CanConvertTo(Class<?> type)
    {
        return false;
    }

    public boolean ContainsProperty(String key)
    {
        return properties.containsKey(key);
    }

    public static JObject Parse(Reader reader) throws IOException
    {
    // TODO
        return new JObject();
//        SkipSpace(reader);
//        char firstChar = (char)reader.Peek();
//        if (firstChar == '\"' || firstChar == '\'')
//        {
//            return JString.Parse(reader);
//        }
//        if (firstChar == '[')
//        {
//            return JArray.Parse(reader);
//        }
//        if ((firstChar >= '0' && firstChar <= '9') || firstChar == '-')
//        {
//            return JNumber.Parse(reader);
//        }
//        if (firstChar == 't' || firstChar == 'f')
//        {
//            return JBoolean.Parse(reader);
//        }
//        if (firstChar == 'n')
//        {
//            return ParseNull(reader);
//        }
//        if (reader.Read() != '{') throw new FormatException();
//        SkipSpace(reader);
//        JObject obj = new JObject();
//        while (reader.Peek() != '}')
//        {
//            if (reader.Peek() == ',') reader.Read();
//            SkipSpace(reader);
//            string name = JString.Parse(reader).Value;
//            SkipSpace(reader);
//            if (reader.Read() != ':') throw new FormatException();
//            JObject value = Parse(reader);
//            obj.properties.Add(name, value);
//            SkipSpace(reader);
//        }
//        reader.Read();
//        return obj;
    }

    public static JObject Parse(String value) throws IOException
    {
        StringReader reader = new StringReader(value);
        return Parse(reader);
    }

    static JObject ParseNull(Reader reader) throws IOException
    {
        char firstChar = (char)reader.read();
        if (firstChar == 'n')
        {
            int c2 = reader.read();
            int c3 = reader.read();
            int c4 = reader.read();
            if (c2 == 'u' && c3 == 'l' && c4 == 'l')
            {
                return null;
            }
        }
        throw new IllegalArgumentException();
    }

    // TODO
//    protected static void SkipSpace(TextReader reader)
//    {
//        while (reader.Peek() == ' ' || reader.Peek() == '\r' || reader.Peek() == '\n')
//        {
//            reader.Read();
//        }
//    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (Map.Entry<String, JObject> pair : properties.entrySet())
        {
            sb.append('"');
            sb.append(pair.getKey());
            sb.append('"');
            sb.append(':');
            if (pair.getValue() == null)
            {
                sb.append("null");
            }
            else
            {
                sb.append(pair.getValue());
            }
            sb.append(',');
        }
        if (properties.size() == 0)
        {
            sb.append('}');
        }
        else
        {
            sb.setCharAt(sb.length() - 1, '}');
        }
        return sb.toString();
    }

//  TODO
//    public static implicit operator JObject(Enum value)
//    {
//        return new JString(value.ToString());
//    }
//
//    public static implicit operator JObject(JObject[] value)
//    {
//        return new JArray(value);
//    }
//
//    public static implicit operator JObject(bool value)
//    {
//        return new JBoolean(value);
//    }
//
//    public static implicit operator JObject(double value)
//    {
//        return new JNumber(value);
//    }
//
//    public static implicit operator JObject(string value)
//    {
//        return new JString(value);
//    }
}
