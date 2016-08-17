package AntShares.Wallets;

import AntShares.UInt160;
import AntShares.IO.*;

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
    public void deserialize(BinaryReader reader)
    {
        // TODO
        //parameterList = reader.ReadVarBytes().Cast<ContractParameterType>().ToArray();
        //RedeemScript = reader.ReadVarBytes();
        //PublicKeyHash = reader.ReadSerializable<UInt160>();
    }

    @Override
    public void serialize(BinaryWriter writer)
    {
        // TODO
//        writer.WriteVarBytes(parameterList.Cast<byte>().ToArray());
//        writer.WriteVarBytes(RedeemScript);
//        writer.Write(PublicKeyHash);
    }
}
