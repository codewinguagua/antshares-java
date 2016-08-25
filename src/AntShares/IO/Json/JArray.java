package AntShares.IO.Json;

import java.io.*;
import java.util.*;

public class JArray extends JObject implements Iterable<JObject>
{
    private List<JObject> items = new ArrayList<JObject>();

    public JArray(JObject ...items)
    {
        this.items.addAll(Arrays.asList(items));
    }

    public JArray(Collection<JObject> items)
    {
        this.items.addAll(items);
    }

    public JObject get(int index)
    {
        return items.get(index);
    }
    
    public JObject set(int index, JObject jobj)
    {
        return items.set(index, jobj);
    }

    public int size()
    {
        return items.size();
    }

    public boolean isReadOnly()
    {
        return false;
    }

    public boolean add(JObject item)
    {
        return items.add(item);
    }

    public void clear()
    {
        items.clear();
    }

    public boolean contains(JObject item)
    {
        return items.contains(item);
    }

	@Override
	public Iterator<JObject> iterator()
	{
		return items.iterator();
	}

    static JArray parseArray(BufferedReader reader) throws IOException
    {
        skipSpace(reader);
        if (reader.read() != '[') throw new IOException();
        skipSpace(reader);
        JArray array = new JArray();
        while (true)
        {
        	reader.mark(1);
        	int c = reader.read();
        	if (c == ']') break;
        	if (c != ',') reader.reset();
            JObject obj = JObject.parse(reader);
            array.items.add(obj);
            skipSpace(reader);
        }
        return array;
    }

    public boolean remove(JObject item)
    {
        return items.remove(item);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (JObject item : items)
        {
            if (item == null)
                sb.append("null");
            else
                sb.append(item);
            sb.append(',');
        }
        if (items.size() == 0)
        {
            sb.append(']');
        }
        else
        {
            sb.setCharAt(sb.length() - 1, ']');
        }
        return sb.toString();
    }
}
