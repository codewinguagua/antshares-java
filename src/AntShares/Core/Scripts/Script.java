package AntShares.Core.Scripts;

import java.io.Serializable;

/**
 *  脚本
 */
public class Script implements Serializable
{
    private static final long serialVersionUID = 6681506622785202897L;
    public byte[] StackScript;
    public byte[] RedeemScript;

//TODO
//    void ISerializable.Deserialize(BinaryReader reader)
//    {
//        StackScript = reader.ReadVarBytes();
//        RedeemScript = reader.ReadVarBytes();
//    }
//
//    void ISerializable.Serialize(BinaryWriter writer)
//    {
//        writer.WriteVarBytes(StackScript);
//        writer.WriteVarBytes(RedeemScript);
//    }
//
//    /**
//     *  变成json对象
//     *  <returns>返回json对象</returns>
//     */
//    public JObject ToJson()
//    {
//        JObject json = new JObject();
//        json["stack"] = StackScript.ToHexString();
//        json["redeem"] = RedeemScript.ToHexString();
//        return json;
//    }

}
