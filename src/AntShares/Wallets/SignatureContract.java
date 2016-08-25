package AntShares.Wallets;

import java.io.IOException;

import org.bouncycastle.math.ec.ECPoint;

import AntShares.Core.Scripts.*;
import AntShares.IO.*;

/**
 *  简单签名合约，该合约只需要一个指定账户的签名即可生效
 */
public class SignatureContract extends Contract
{
    private ECPoint publicKey;

    public SignatureContract(ECPoint publicKey2) {
        RedeemScript = CreateSignatureRedeemScript(publicKey2);
        // TODO
        //PublicKeyHash = publicKey.EncodePoint(true).ToScriptHash(),
        publicKey = publicKey2;
    }

    /**
     *  合约的形式参数列表
     */
    @Override
    public ContractParameterType[] getParameterList()
    {
        return new ContractParameterType[] { ContractParameterType.Signature };
    }

    /**
     *  用指定的公钥创建一个SignatureContract实例
     *  <param name="publicKey">用于创建SignatureContract实例的公钥</param>
     *  <returns>返回一个简单签名合约</returns>
     */
    public static SignatureContract Create(ECPoint publicKey)
    {
        return new SignatureContract(publicKey);
    }

    /**
     *  用指定的公钥创建一段SignatureContract合约的脚本
     *  <param name="publicKey">用于创建SignatureContract合约脚本的公钥</param>
     *  <returns>返回一段简单签名合约的脚本代码</returns>
     */
    public static byte[] CreateSignatureRedeemScript(ECPoint publicKey)
    {
        try (ScriptBuilder sb = new ScriptBuilder())
        {
	        sb.push(publicKey.getEncoded(true));
	        sb.add(ScriptOp.OP_CHECKSIG);
	        return sb.toArray();
        }
    }

    /**
     *  反序列化
     *  <param name="reader">反序列化的数据来源</param>
     */
    @Override public void deserialize(BinaryReader reader)
    {
        // TODO
        //publicKey = ECPoint.DeserializeFrom(reader, ECCurve.Secp256r1);
        //RedeemScript = CreateSignatureRedeemScript(publicKey);
        //PublicKeyHash = publicKey.EncodePoint(true).ToScriptHash();
    }

    /**
     *  序列化
     *  <param name="writer">存放序列化后的结果</param>
     * @throws IOException 
     */
    @Override public void serialize(BinaryWriter writer) throws IOException
    {
    	writer.writeECPoint(publicKey);
    }
}
