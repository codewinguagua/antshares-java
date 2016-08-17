package AntShares.Core;

import AntShares.UInt160;
import AntShares.Core.Scripts.Script;
import AntShares.IO.*;

/**
 *  为需要签名的数据提供一个接口
 */
public interface Signable extends Serializable
{
    /**
     *  用于验证该对象的脚本列表
     */
    Script[] getScripts();

    /**
     *  反序列化未签名的数据
     *  <param name="reader">数据来源</param>
     */
    void deserializeUnsigned(BinaryReader reader);

    /**
     *  序列化未签名的数据
     *  <param name="writer">存放序列化后的结果</param>
     */
    void serializeUnsigned(BinaryWriter writer);

    /**
     *  获得需要校验的脚本Hash值
     *  <returns>返回需要校验的脚本Hash值</returns>
     */
    UInt160[] getScriptHashesForVerifying();

}
