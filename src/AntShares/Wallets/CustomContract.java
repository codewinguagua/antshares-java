package AntShares.Wallets;

import java.io.InputStream;
import java.io.OutputStream;

import AntShares.UInt160;

public class CustomContract extends Contract
{
    private ContractParameterType[] parameterList;

    public CustomContract(ContractParameterType[] parameterList2, byte[] redeemScript, UInt160 publicKeyHash) {
        this.parameterList = parameterList2;
        RedeemScript = redeemScript;
        PublicKeyHash = publicKeyHash;
	}

	@Override
    public ContractParameterType[] getParameterList() {
    	return parameterList;
    }

    public static CustomContract Create(UInt160 publicKeyHash, ContractParameterType[] parameterList, byte[] redeemScript)
    {
        return new CustomContract(parameterList, redeemScript, publicKeyHash);
    }

    @Override
    public void Deserialize(InputStream reader)
    {
    	// TODO
        //parameterList = reader.ReadVarBytes().Cast<ContractParameterType>().ToArray();
        //RedeemScript = reader.ReadVarBytes();
        //PublicKeyHash = reader.ReadSerializable<UInt160>();
    }

    @Override
    public void Serialize(OutputStream writer)
    {
    	// TODO
//        writer.WriteVarBytes(parameterList.Cast<byte>().ToArray());
//        writer.WriteVarBytes(RedeemScript);
//        writer.Write(PublicKeyHash);
    }
}
