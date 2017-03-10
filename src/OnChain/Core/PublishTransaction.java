package OnChain.Core;

import java.io.IOException;
import java.util.Arrays;

import OnChain.Helper;
import OnChain.IO.BinaryReader;
import OnChain.IO.BinaryWriter;
import OnChain.IO.Json.JArray;
import OnChain.IO.Json.JNumber;
import OnChain.IO.Json.JObject;
import OnChain.IO.Json.JString;

public class PublishTransaction extends Transaction {
	private FunctionCode code;
	private String name;
	private String codeVersion;
	private String author;
	private String email;
	private String description;
	
	protected PublishTransaction() {
		super(TransactionType.PublishTransaction);
	}
	@Override
	protected void deserializeExclusiveData(BinaryReader reader) throws IOException {
		try {
			code = reader.readSerializable(FunctionCode.class);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IOException();
		}
		name = reader.readVarString();// 252
		codeVersion = reader.readVarString(); // 252
		author = reader.readVarString();// 252
		email = reader.readVarString();// 252
		description = reader.readVarString();// 65535
	}
	
	@Override
	protected void serializeExclusiveData(BinaryWriter writer) throws IOException {
		writer.writeSerializable(code);
		writer.writeVarString(name);
		writer.writeVarString(codeVersion);
		writer.writeVarString(author);
		writer.writeVarString(email);
		writer.writeVarString(description);
	}
	
	@Override
    public JObject json() {
		JObject json = new JObject();
		json.set("contract", new JObject());
		json.get("contract").set("hash", new JString(code.getScriptHash().toString()));
		json.get("contract").set("script", new JString(Helper.toHexString(code.getScript())));
		json.get("contract").set("parameters", new JArray(Arrays.stream(code.getParameterList()).map(p -> new JString(p.toString())).toArray(JObject[]::new)));
		json.get("contract").set("returntype", new JNumber(code.getReturnType().ordinal()));
		json.get("contract").set("name", new JString(name));
		json.get("contract").set("version", new JString(codeVersion));
		json.get("contract").set("author", new JString(author));
		json.get("contract").set("email", new JString(email));
		json.get("contract").set("description", new JString(description));
        return json;
	}
//{
//	public byte[][] contracts;
//	
//	@Override
//	public Fixed8 systemFee()
//	{
//        return Fixed8.fromLong(500 * contracts.length);
//	}
//	
//	public PublishTransaction()
//	{
//		super(TransactionType.PublishTransaction);
//	}
//	
//	@Override
//	protected void deserializeExclusiveData(BinaryReader reader) throws IOException
//	{
//		contracts = new byte[reader.readByte()][];
//		if (contracts.length == 0)
//			throw new IOException();
//		for (int i = 0; i < contracts.length; i++)
//			contracts[i] = reader.readVarBytes();
//	}
//	
//	@Override
//	protected void serializeExclusiveData(BinaryWriter writer) throws IOException
//	{
//        writer.writeByte((byte)contracts.length);
//        for (int i = 0; i < contracts.length; i++)
//            writer.writeVarBytes(contracts[i]);
//	}
//	
//	@Override
//    public JObject json()
//    {
//        JObject json = super.json();
//        json.set("contracts", new JArray(Arrays.stream(contracts).map(p -> new JString(Helper.toHexString(p))).toArray(JString[]::new)));
//        return json;
//    }
}
