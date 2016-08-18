package AntShares.Core;

import java.io.IOException;

import AntShares.UInt160;
import AntShares.IO.*;

/**
 *  为需要签名的数据提供一个接口
 */
public interface Signable extends Serializable
{
    /**
     *  反序列化未签名的数据
     *  <param name="reader">数据来源</param>
     * @throws IOException 
     */
    void deserializeUnsigned(BinaryReader reader) throws IOException;

    /**
     *  序列化未签名的数据
     *  <param name="writer">存放序列化后的结果</param>
     * @throws IOException 
     */
    void serializeUnsigned(BinaryWriter writer) throws IOException;

    /**
     *  获得需要校验的脚本Hash值
     *  <returns>返回需要校验的脚本Hash值</returns>
     */
    UInt160[] getScriptHashesForVerifying();
}
