package AntShares.Wallets;

import java.math.BigInteger;
import java.util.Arrays;

import org.bouncycastle.math.ec.ECPoint;

import AntShares.UInt160;
import AntShares.Core.Scripts.ScriptBuilder;
import AntShares.IO.*;

/**
 *  多方签名合约，该合约需要指定的N个账户中至少M个账户签名后才能生效
 */
public class MultiSigContract extends Contract
{
    private int m;
    private ECPoint[] publicKeys;

    public MultiSigContract(UInt160 publicKeyHash, int m2, ECPoint[] publicKeys2) {
        // TODO Auto-generated constructor stub
        RedeemScript = CreateMultiSigRedeemScript(m, publicKeys);
        PublicKeyHash = publicKeyHash;
        m = m2;
        publicKeys = publicKeys2;
    }

    /**
     *  合约的形式参数列表
     */
    @Override public ContractParameterType[] getParameterList() {
        // TODO
        //return Enumerable.Repeat(ContractParameterType.Signature, m).ToArray();
        return null;
    }

    /**
     *  用指定的N个公钥创建一个MultiSigContract实例，并指定至少需要M个账户的签名
     *  <param name="publicKeyHash">合约所属的账户</param>
     *  <param name="m">一个整数，该合约至少需要包含此数量的签名才能生效</param>
     *  <param name="publicKeys">公钥列表，该合约需要此列表中至少m个账户签名后才能生效</param>
     *  <returns>返回一个多方签名合约</returns>
     */
    public static MultiSigContract Create(UInt160 publicKeyHash, int m, ECPoint[] publicKeys)
    {
        return new MultiSigContract(publicKeyHash, m, publicKeys);
    }

    /**
     *  用指定的N个公钥创建一段MultiSigContract合约的脚本，并指定至少需要M个账户的签名
     *  <param name="m">一个整数，该合约至少需要包含此数量的签名才能生效</param>
     *  <param name="publicKeys">公钥列表，该合约需要此列表中至少m个账户签名后才能生效</param>
     *  <returns>返回一段多方签名合约的脚本代码</returns>
     */
    public static byte[] CreateMultiSigRedeemScript(int m, ECPoint[] publicKeys)
    {
        if (!(1 <= m && m <= publicKeys.length && publicKeys.length <= 1024))
            throw new IllegalArgumentException();
        ScriptBuilder sb = new ScriptBuilder();
        sb.push(BigInteger.valueOf(m));
        for (ECPoint publicKey : Arrays.stream(publicKeys).sorted().toArray(ECPoint[]::new))
        {
            sb.push(publicKey.getEncoded(true));
        }
        sb.push(BigInteger.valueOf(publicKeys.length));
        // TODO
        //sb.Add(ScriptOp.OP_CHECKMULTISIG);
        return sb.toArray();
    }

    /**
     *  反序列化
     *  <param name="reader">反序列化的数据来源</param>
     */
    @Override public void deserialize(BinaryReader reader)
    {
        // TODO
//        m = (int)reader.ReadVarInt(Integer.MAX_VALUE);
//        publicKeys = new ECPoint[reader.ReadVarInt(0x10000000)];
//        for (int i = 0; i < publicKeys.Length; i++)
//        {
//            publicKeys[i] = ECPoint.DeserializeFrom(reader, ECCurve.Secp256r1);
//        }
//        RedeemScript = CreateMultiSigRedeemScript(m, publicKeys);
//        PublicKeyHash = reader.ReadSerializable<UInt160>();
    }

    /**
     *  序列化
     *  <param name="writer">存放序列化后的结果</param>
     */
    @Override public void serialize(BinaryWriter writer)
    {
//        writer.WriteVarInt(m);
//        writer.Write(publicKeys);
//        writer.Write(PublicKeyHash);
    }
}