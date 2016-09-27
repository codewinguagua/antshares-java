package AntShares.Wallets;

import java.io.IOException;

import AntShares.UInt160;
import AntShares.IO.*;

public class CustomContract extends Contract
{
    private ContractParameterType[] parameterList;
    
    @Override
    public ContractParameterType[] parameterList()
    {
        return parameterList;
    }

    public static CustomContract create(UInt160 publicKeyHash, ContractParameterType[] parameterList, byte[] redeemScript)
    {
    	CustomContract contract = new CustomContract();
    	contract.parameterList = parameterList;
    	contract.redeemScript = redeemScript;
    	contract.publicKeyHash = publicKeyHash;
        return contract;
    }

    @Override
    public void deserialize(BinaryReader reader) throws IOException
    {
    	byte[] buffer = reader.readVarBytes();
    	parameterList = new ContractParameterType[buffer.length];
    	for (int i = 0; i < parameterList.length; i++)
    		parameterList[i] = ContractParameterType.values()[buffer[i]];
        redeemScript = reader.readVarBytes();
        try
        {
			publicKeyHash = reader.readSerializable(UInt160.class);
		}
        catch (InstantiationException | IllegalAccessException ex)
        {
			throw new RuntimeException(ex);
		}
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException
    {
    	byte[] buffer = new byte[parameterList.length];
    	for (int i = 0; i < buffer.length; i++)
    		buffer[i] = (byte)parameterList[i].ordinal();
        writer.writeVarBytes(buffer);
        writer.writeVarBytes(redeemScript);
        writer.writeSerializable(publicKeyHash);
    }
}
