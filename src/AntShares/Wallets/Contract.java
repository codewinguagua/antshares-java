package AntShares.Wallets;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.UInt160;
import AntShares.IO.Serializable;

/**
 *  所有合约的基类
 */
public abstract class Contract implements Serializable
{
    /**
     *  合约脚本代码
     */
    public byte[] RedeemScript;
    /**
     *  公钥散列值，用于标识该合约在钱包中隶属于哪一个账户
     */
    public UInt160 PublicKeyHash;

    private String _address;
    /**
     *  合约地址
     */
    public String getAddress()
    {
        if (_address == null)
        {
            // TODO
            //_address = Wallet.ToAddress(ScriptHash);
        }
        return _address;
    }

    /**
     *  合约的形式参数列表
     */
    public abstract ContractParameterType[] getParameterList();

    private UInt160 _scriptHash;
    /**
     *  脚本散列值
     */
    public UInt160 getScriptHash()
    {
        if (_scriptHash == null)
        {
            // TODO
            //_scriptHash = RedeemScript.ToScriptHash();
        }
        return _scriptHash;
    }

    /**
     *  反序列化
     *  <param name="reader">数据来源</param>
     */
    @Override
    public abstract void Deserialize(InputStream reader);

    /**
     *  比较与另一个对象是否相等
     *  <param name="obj">另一个对象</param>
     *  <returns>返回比较的结果</returns>
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Contract)) return false;
        return getScriptHash().equals(((Contract) obj).getScriptHash());
    }

    /**
     *  获得HashCode
     *  <returns>返回HashCode</returns>
     */
    @Override
    public int hashCode()
    {
        return getScriptHash().hashCode();
    }

    /**
     *  序列化
     *  <param name="writer">存放序列化后的结果</param>
     */
    public abstract void Serialize(OutputStream writer);
}