package OnChain.Network;

/**
 *  定义清单中的对象类型
 */
public enum InventoryType
{
    /**
     *  交易
     */
    TX(0x01),
    /**
     *  区块
     */
    Block(0x02),
    /**
     *  共识数据
     */
    Consensus(0xe0),
    
    ;
    int value;
    private InventoryType(int v)
    {
        value = v;
    }
    
    public int get() {
        return value;
    }
    
    
    
//    private byte vv;
//    private InventoryType(byte vv) {
//    	this.vv = vv;
//    }
//    public byte getValue() {
//    	return vv;
//    }
    public static InventoryType from(byte b) {
    	for(InventoryType t: InventoryType.values()) {
    		if(t.get() == b) {
    			return t;
    		}
    	}
    	return null;
    }
}

