package AntShares.IO.Json;

import java.io.*;

public class JBoolean extends JObject
{
    private boolean value;
    
    public boolean getValue() { return value; }

    public JBoolean(boolean val)
    {
        this.value = val;
    }

    @Override
    public boolean AsBoolean()
    {
        return value;
    }

    @Override
    public String AsString()
    {
        return String.valueOf(value).toLowerCase();
    }

    @Override
    public boolean CanConvertTo(Class<?> type)
    {
        if (type.equals(boolean.class))
            return true;
        if (type.equals(String.class))
            return true;
        return false;
    }

    public static JBoolean Parse(Reader reader) throws IOException
    {
        // TODO
        //SkipSpace(reader);
        char firstChar = (char)reader.read();
        if (firstChar == 't')
        {
            int c2 = reader.read();
            int c3 = reader.read();
            int c4 = reader.read();
            if (c2 == 'r' && c3 == 'u' && c4 == 'e')
            {
                return new JBoolean(true);
            }
        }
        else if (firstChar == 'f')
        {
            int c2 = reader.read();
            int c3 = reader.read();
            int c4 = reader.read();
            int c5 = reader.read();
            if (c2 == 'a' && c3 == 'l' && c4 == 's' && c5 == 'e')
            {
                return new JBoolean(false);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString()
    {
        return AsString();
    }
}