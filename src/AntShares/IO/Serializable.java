package AntShares.IO;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *  为序列化提供一个接口
 */
public interface Serializable
{
    /**
     *  序列化
     *  <param name="writer">存放序列化后的结果</param>
     */
    void Serialize(OutputStream writer);
    
    /**
     *  反序列化
     *  <param name="reader">数据来源</param>
     */
    void Deserialize(InputStream reader);
}