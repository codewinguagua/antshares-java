package AntShares.Network;

import java.io.Reader;
import java.io.Writer;

import AntShares.UInt160;
import AntShares.UInt256;
import AntShares.Core.Scripts.Script;

public abstract class Inventory // TODO : ISignable
{
    //[NonSerialized]
    private UInt256 _hash = null;
    public UInt256 get()
    {
        if (_hash == null)
        {
        	// TODO
            //_hash = new UInt256(GetHashData().Sha256().Sha256());
        	_hash = new UInt256();
        }
        return _hash;
    }

    public abstract Script[] getScripts();
    public abstract void setScripts(Script[] scripts);

    public abstract InventoryType getInventoryType();

    public abstract void Deserialize(Reader reader);

    public abstract void DeserializeUnsigned(Reader reader);

    // TODO
//    protected virtual byte[] GetHashData()
//    {
//        using (MemoryStream ms = new MemoryStream())
//        using (BinaryWriter writer = new BinaryWriter(ms))
//        {
//            SerializeUnsigned(writer);
//            writer.Flush();
//            return ms.ToArray();
//        }
//    }

    public abstract UInt160[] GetScriptHashesForVerifying();

    public abstract void Serialize(Writer writer);

    public abstract void SerializeUnsigned(Writer writer);

    public abstract boolean Verify();
}
