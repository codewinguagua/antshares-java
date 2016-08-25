package AntShares.Core.Scripts;

import java.io.IOException;

import AntShares.UInt160;
import AntShares.IO.*;

/**
 *  脚本
 */
public class Script implements Serializable
{
    public byte[] stackScript;
    public byte[] redeemScript;

    @Override
    public void deserialize(BinaryReader reader) throws IOException
    {
        stackScript = reader.readVarBytes();
        redeemScript = reader.readVarBytes();
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException
    {
        writer.writeVarBytes(stackScript);
        writer.writeVarBytes(redeemScript);
    }

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

    public static UInt160 toScriptHash(byte[] script)
    {
    	//TODO
    	return new UInt160();
    }
}
