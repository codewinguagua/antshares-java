package AntShares.Core;

import java.io.Serializable;

import AntShares.Fixed8;
import AntShares.UInt160;
import AntShares.UInt256;

/**
 *  交易输出
 */
public class TransactionOutput implements Serializable
{
	private static final long serialVersionUID = -8486024867292581586L;
	/**
     *  资产编号
     */
    public UInt256 AssetId;
    /**
     *  金额
     */
    public Fixed8 Value;
    /**
     *  收款地址
     */
    public UInt160 ScriptHash;

// TODO
//    /**
//     *  将交易输出转变为json对象
//     *  <param name="index">该交易输出在交易中的索引</param>
//     *  <returns>返回json对象</returns>
//     */
//    public JObject ToJson(ushort index)
//    {
//        JObject json = new JObject();
//        json["n"] = index;
//        json["asset"] = AssetId.ToString();
//        json["value"] = Value.ToString();
//        json["high"] = Value.GetData() >> 32;
//        json["low"] = Value.GetData() & 0xffffffff;
//        json["address"] = Wallet.ToAddress(ScriptHash);
//        return json;
//    }
}
