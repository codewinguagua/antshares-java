package AntShares.Core;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import org.bouncycastle.math.ec.ECPoint;

import AntShares.*;
import AntShares.Core.Scripts.*;
import AntShares.Cryptography.ECC;
import AntShares.IO.Json.*;
import AntShares.Wallets.*;

/**
 *  签名上下文
 */
public class SignatureContext
{
    /**
     *  要签名的数据
     */
    public final Signable signable;
    /**
     *  要验证的脚本散列值
     */
    public final UInt160[] scriptHashes;
    private final byte[][] redeemScripts;
    private final Map<ECPoint, byte[]>[] signatures;
    private final boolean[] completed;

    /**
     *  判断签名是否完成
     */
    public boolean isCompleted()
    {
        for (boolean b : completed)
        	if (!b)
        		return false;
        return true;
    }

    /**
     *  对指定的数据构造签名上下文
     *  <param name="signable">要签名的数据</param>
     */
    @SuppressWarnings("unchecked")
	public SignatureContext(Signable signable)
    {
        this.signable = signable;
        this.scriptHashes = signable.getScriptHashesForVerifying();
        this.redeemScripts = new byte[scriptHashes.length][];
        this.signatures = (Map<ECPoint, byte[]>[]) Array.newInstance(Map.class, scriptHashes.length);
        this.completed = new boolean[scriptHashes.length];
    }

    /**
     *  添加一个签名
     *  <param name="contract">该签名所对应的合约</param>
     *  <param name="pubkey">该签名所对应的公钥</param>
     *  <param name="signature">签名</param>
     *  <returns>返回签名是否已成功添加</returns>
     */
    public boolean add(Contract contract, ECPoint pubkey, byte[] signature)
    {
        for (int i = 0; i < scriptHashes.length; i++)
        {
            if (scriptHashes[i].equals(contract.getScriptHash()))
            {
                if (redeemScripts[i] == null)
                    redeemScripts[i] = contract.RedeemScript;
                if (signatures[i] == null)
                	signatures[i] = new HashMap<ECPoint, byte[]>();
                signatures[i].put(pubkey, signature);
                completed[i] |= 
                        contract.getParameterList().length == signatures[i].size()
                        && Arrays.stream(contract.getParameterList()).allMatch(
                                p -> p == ContractParameterType.Signature);
                return true;
            }
        }
        return false;
    }

    /**
     *  从指定的json对象中解析出签名上下文
     *  <param name="json">json对象</param>
     *  <returns>返回上下文</returns>
     */
    public static SignatureContext FromJson(JObject json)
    {
    	//TODO
//        String typename = String.Format("{0}.{1}", typeof(SignatureContext).Namespace, json["type"].AsString());
//        Signable signable = Assembly.GetExecutingAssembly().CreateInstance(typename) as Signable;
//        using (MemoryStream ms = new MemoryStream(json["hex"].AsString().HexToBytes(), false))
//        using (BinaryReader reader = new BinaryReader(ms, Encoding.UTF8))
//        {
//            signable.DeserializeUnsigned(reader);
//        }
//        SignatureContext context = new SignatureContext(signable);
//        JArray scripts = (JArray)json["scripts"];
//        for (int i = 0; i < scripts.Count; i++)
//        {
//            if (scripts[i] != null)
//            {
//                context.redeemScripts[i] = scripts[i]["redeem_script"].AsString().HexToBytes();
//                context.signatures[i] = new Dictionary<ECPoint, byte[]>();
//                JArray sigs = (JArray)scripts[i]["signatures"];
//                for (int j = 0; j < sigs.Count; j++)
//                {
//                    ECPoint pubkey = ECPoint.DecodePoint(sigs[j]["pubkey"].AsString().HexToBytes(), ECCurve.Secp256r1);
//                    byte[] signature = sigs[j]["signature"].AsString().HexToBytes();
//                    context.signatures[i].Add(pubkey, signature);
//                }
//                context.completed[i] = scripts[i]["completed"].AsBoolean();
//            }
//        }
//        return context;
        return null;
    }

    /**
     *  从签名上下文中获得完整签名的合约脚本
     *  <returns>返回合约脚本</returns>
     */
    public Script[] getScripts()
    {
        if (!isCompleted()) throw new IllegalStateException();
        Script[] scripts = new Script[signatures.length];
        for (int i = 0; i < scripts.length; i++)
        {
            try (ScriptBuilder sb = new ScriptBuilder())
            {
	            for (byte[] signature : signatures[i].entrySet().stream().sorted((a, b) -> ECC.compare(a.getKey(), b.getKey())).map(p -> p.getValue()).toArray(byte[][]::new))
	            {
	                sb.push(signature);
	            }
	            scripts[i] = new Script();
	            scripts[i].stackScript = sb.toArray();
	            scripts[i].redeemScript = redeemScripts[i];
            }
        }
        return scripts;
    }

    public static SignatureContext parse(String value)
    {
        //return FromJson(JObject.Parse(value));
    	//TODO
    	return null;
    }

    /**
     *  把签名上下文转为json对象
     *  <returns>返回json对象</returns>
     */
    public JObject json()
    {
        JObject json = new JObject();
        json.set("type", new JString(signable.getClass().getTypeName()));
        json.set("hex", new JString(Helper.toHexString(signable.getHashData())));
        JArray scripts = new JArray();
        for (int i = 0; i < signatures.length; i++)
        {
            if (signatures[i] == null)
            {
                scripts.add(null);
            }
            else
            {
                scripts.add(new JObject());
                scripts.get(i).set("redeem_script", new JString(Helper.toHexString(redeemScripts[i])));
                JArray sigs = new JArray();
                for (Entry<ECPoint, byte[]> pair : signatures[i].entrySet())
                {
                    JObject signature = new JObject();
                    signature.set("pubkey", new JString(Helper.toHexString(pair.getKey().getEncoded(true))));
                    signature.set("signature", new JString(Helper.toHexString(pair.getValue())));
                    sigs.add(signature);
                }
                scripts.get(i).set("signatures", sigs);
                scripts.get(i).set("completed", new JBoolean(completed[i]));
            }
        }
        json.set("scripts", scripts);
        return json;
    }

    @Override public String toString()
    {
        return json().toString();
    }
}
