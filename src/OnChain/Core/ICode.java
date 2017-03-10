package OnChain.Core;

import OnChain.UInt160;
import OnChain.Wallets.ContractParameterType;

public interface ICode {
	public byte[] getScript(); 
	public ContractParameterType[] getParameterList();
	public ContractParameterType getReturnType();
	public UInt160 getScriptHash();
}
