package AntShares.IO;

import java.io.IOException;

/**
 *  为序列化提供一个接口
 */
public interface Serializable
{
    /**
     *  序列化
     *  <param name="writer">存放序列化后的结果</param>
     * @throws IOException 
     */
    void serialize(BinaryWriter writer) throws IOException;
    
    /**
     *  反序列化
     *  <param name="reader">数据来源</param>
     * @throws IOException 
     */
    void deserialize(BinaryReader reader) throws IOException;
}