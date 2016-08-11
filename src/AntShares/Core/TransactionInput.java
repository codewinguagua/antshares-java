package AntShares.Core;

import java.io.Serializable;

import AntShares.UInt256;

/**
 *  交易输入
 */
public class TransactionInput implements Serializable
{
	private static final long serialVersionUID = -1788728962124438893L;
	/**
     *  引用交易的散列值
     */
    public UInt256 PrevHash;
    /**
     *  引用交易输出的索引
     */
    public short PrevIndex; // TODO ushort -> short ?

    /**
     *  比较当前对象与指定对象是否相等
     *  <param name="obj">要比较的对象</param>
     *  <returns>返回对象是否相等</returns>
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (null == obj) return false;
        if (!(obj instanceof TransactionInput)) return false;
        TransactionInput other = (TransactionInput) obj;
        return PrevHash.equals(other.PrevHash) && PrevIndex == other.PrevIndex;
    }

    /**
     *  获得对象的HashCode
     *  <returns>返回对象的HashCode</returns>
     */
    @Override
    public int hashCode()
    {
        return PrevHash.hashCode() + PrevIndex;
    }

    /**
     *  将交易输入转变为json对象
     *  <returns>返回json对象</returns>
     */
// TODO
//    public JObject ToJson()
//    {
//        JObject json = new JObject();
//        json["txid"] = PrevHash.ToString();
//        json["vout"] = PrevIndex;
//        return json;
//    }
}