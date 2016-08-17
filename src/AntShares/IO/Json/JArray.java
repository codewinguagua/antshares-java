package AntShares.IO.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class JArray extends JObject // TODO, IList<JObject>
{
    private List<JObject> items = new ArrayList<JObject>();

    public JArray(JObject[] items)
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
    
    public void set(int index, JObject jobj)
    {
        items.set(index, jobj);
    }

    public int count()
    {
        return items.size();
    }

    public boolean isReadOnly()
    {
        return false;
    }

    public void add(JObject item)
    {
        items.add(item);
    }

    public void clear()
    {
        items.clear();
    }

    public boolean contains(JObject item)
    {
        return items.contains(item);
    }

    public void copyTo(JObject[] array, int arrayIndex)
    {
        for (JObject jobj : items) {
            array[arrayIndex++] = jobj;
            if (arrayIndex >= array.length) {
                break;
            }
        }
    }

    public Iterator<JObject> GetEnumerator()
    {
        return items.iterator();
    }

    public int IndexOf(JObject item)
    {
        return items.indexOf(item);
    }

    public void Insert(int index, JObject item)
    {
        items.add(index, item);
    }

    // TODO
//    internal new static JArray Parse(TextReader reader)
//    {
//        SkipSpace(reader);
//        if (reader.Read() != '[') throw new FormatException();
//        SkipSpace(reader);
//        JArray array = new JArray();
//        while (reader.Peek() != ']')
//        {
//            if (reader.Peek() == ',') reader.Read();
//            JObject obj = JObject.Parse(reader);
//            array.items.Add(obj);
//            SkipSpace(reader);
//        }
//        reader.Read();
//        return array;
//    }

    public boolean Remove(JObject item)
    {
        return items.remove(item);
    }

    public void RemoveAt(int index)
    {
        items.remove(index);
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
