package AntShares.Core;

import java.io.IOException;

import AntShares.*;
import AntShares.Core.Scripts.*;
import AntShares.IO.*;
import AntShares.IO.Json.JObject;
import AntShares.Network.*;

/**
 *  区块或区块头
 */
public class Block extends Inventory
{
    /**
     *  区块版本
     */
    public int Version;
    /**
     *  前一个区块的散列值
     */
    public UInt256 PrevBlock;
    /**
     *  该区块中所有交易的Merkle树的根
     */
    public UInt256 MerkleRoot;
    /**
     *  时间戳
     */
    public int Timestamp;
    /**
     *  区块高度
     */
    public int Height;
    /**
     *  随机数
     */
    public long Nonce;
    /**
     *  下一个区块的记账合约的散列值
     */
    public UInt160 NextMiner;
    /**
     *  用于验证该区块的脚本
     */
    public Script Script;
    /**
     *  交易列表，当列表中交易的数量为0时，该Block对象表示一个区块头
     */
    public Transaction[] Transactions;

// TODO
//    [NonSerialized]
    private Block _header = null;
    /**
     *  该区块的区块头
     */
    public Block getHeader()
    {
        if (IsHeader()) return this;
        if (_header == null)
        {
            _header = new Block();
            _header.PrevBlock = PrevBlock;
            _header.MerkleRoot = this.MerkleRoot;
            _header.Timestamp = this.Timestamp;
            _header.Height = this.Height;
            _header.Nonce = this.Nonce;
            _header.NextMiner = this.NextMiner;
            _header.Script = this.Script;
            _header.Transactions = new Transaction[0];
        }
        return _header;
    }

    /**
     *  资产清单的类型
     */
    @Override public InventoryType getInventoryType() { return InventoryType.Block; }

    //@Override 
    public Script[] getScripts()
    {
        return new Script[] { Script };
    }
    
    //@Override
    public void setScripts(Script[] value)
    {
        if (value.length != 1) throw new IllegalArgumentException();
        Script = value[0];
    }

    /**
     *  返回当前Block对象是否为区块头
     */
    public boolean IsHeader() { return Transactions.length == 0; }

    public static Fixed8 CalculateNetFee(Iterable<Transaction> transactions)
    {
//        Transaction[] ts = transactions.Where(p => p.Type != TransactionType.MinerTransaction && p.Type != TransactionType.ClaimTransaction).ToArray();
//        Fixed8 amount_in = ts.SelectMany(p => p.References.Values.Where(o => o.AssetId == Blockchain.AntCoin.Hash)).Sum(p => p.Value);
//        Fixed8 amount_out = ts.SelectMany(p => p.Outputs.Where(o => o.AssetId == Blockchain.AntCoin.Hash)).Sum(p => p.Value);
//        Fixed8 amount_sysfee = ts.Sum(p => p.SystemFee);
//        return amount_in - amount_out - amount_sysfee;
        return new Fixed8(0);
    }

    /**
     *  反序列化
     *  <param name="reader">数据来源</param>
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Override public void deserialize(BinaryReader reader) throws IOException
    {
        ((Signable)this).deserializeUnsigned(reader);
        if (reader.readByte() != 1) throw new FormatException();
        // TODO
        //Script = reader.readSerializable(Script.getClass());
        Transactions = new Transaction[(int) reader.readVarInt(0x10000000)];
        for (int i = 0; i < Transactions.length; i++)
        {
            Transactions[i] = Transaction.deserializeFrom(reader);
        }
        if (Transactions.length > 0)
        {
            // TODO
//            if (Transactions[0].type != TransactionType.MinerTransaction || Transactions.Skip(1).Any(p => p.Type == TransactionType.MinerTransaction))
//                throw new FormatException();
//            if (MerkleTree.ComputeRoot(Transactions.Select(p => p.Hash).ToArray()) != MerkleRoot)
//                throw new FormatException();
        }
    }

    @Override public void deserializeUnsigned(BinaryReader reader) throws IOException
    {
        Version = reader.readInt();
        // TODO
//        PrevBlock = reader.readSerializable(UInt256.class);
//        MerkleRoot = reader.readSerializable(UInt256.class);
        Timestamp = reader.readInt();
        Height = reader.readInt();
        Nonce = reader.readInt();
        // TODO
//        NextMiner = reader.readSerializable(UInt160.class);
        Transactions = new Transaction[0];
    }

    /**
     *  比较当前区块与指定区块是否相等
     *  <param name="obj">要比较的区块</param>
     *  <returns>返回对象是否相等</returns>
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Block)) return false;
        return this.hash().equals(((Block) obj).hash());
    }

    // TODO
    public static Block FromTrimmedData(byte[] data, int index /*, Func<UInt256, Transaction> txSelector = null*/)
    {
        Block block = new Block();
//        using (MemoryStream ms = new MemoryStream(data, index, data.Length - index, false))
//        using (BinaryReader reader = new BinaryReader(ms))
//        {
//            ((ISignable)block).DeserializeUnsigned(reader);
//            reader.ReadByte(); block.Script = reader.ReadSerializable<Script>();
//            if (txSelector == null)
//            {
//                block.Transactions = new Transaction[0];
//            }
//            else
//            {
//                block.Transactions = new Transaction[reader.ReadVarInt(0x10000000)];
//                for (int i = 0; i < block.Transactions.Length; i++)
//                {
//                    block.Transactions[i] = txSelector(reader.ReadSerializable<UInt256>());
//                }
//            }
//        }
        return block;
    }

    /**
     *  获得区块的HashCode
     *  <returns>返回区块的HashCode</returns>
     */
    @Override public int hashCode()
    {
        return hash().hashCode();
    }

    @Override public UInt160[] getScriptHashesForVerifying()
    {
        // TODO
//        if (PrevBlock == UInt256.ZERO)
//            return new UInt160[] { Script.RedeemScript.ToScriptHash() };
//        Block prev_header = Blockchain.Default.GetHeader(PrevBlock);
//        if (prev_header == null) throw new UnsupportedOperationException();
//        return new UInt160[] { prev_header.NextMiner };
        return null;
    }

    /**
     *  根据区块中所有交易的Hash生成MerkleRoot
     */
    public void RebuildMerkleRoot()
    {
        //MerkleRoot = MerkleTree.ComputeRoot(Transactions.Select(p => p.Hash).ToArray());
    }

    /**
     *  序列化
     *  <param name="writer">存放序列化后的数据</param>
     * @throws IOException 
     */
    @Override public void serialize(BinaryWriter writer) throws IOException
    {
        ((Signable)this).serializeUnsigned(writer);
        writer.writeByte((byte)1); writer.writeSerializable(Script);
        writer.writeSerializableArray(Transactions);
    }

    @Override public void serializeUnsigned(BinaryWriter writer) throws IOException
    {
        writer.writeInt(Version);
        writer.writeSerializable(PrevBlock);
        writer.writeSerializable(MerkleRoot);
        writer.writeInt(Timestamp);
        writer.writeInt(Height);
        writer.writeLong(Nonce);
        writer.writeSerializable(NextMiner);
    }

    /**
     *  变成json对象
     *  <returns>返回json对象</returns>
     */
    public JObject ToJson()
    {
        JObject json = new JObject();
//        json["hash"] = Hash.ToString();
//        json["version"] = Version;
//        json["previousblockhash"] = PrevBlock.ToString();
//        json["merkleroot"] = MerkleRoot.ToString();
//        json["time"] = Timestamp;
//        json["height"] = Height;
//        json["nonce"] = Nonce.ToString("x16");
//        json["nextminer"] = Wallet.ToAddress(NextMiner);
//        json["script"] = Script.ToJson();
//        json["tx"] = Transactions.Select(p => p.ToJson()).ToArray();
        return json;
    }

    /**
     *  把区块对象变为只包含区块头和交易Hash的字节数组，去除交易数据
     *  <returns>返回只包含区块头和交易Hash的字节数组</returns>
     */
    public byte[] Trim()
    {
//        using (MemoryStream ms = new MemoryStream())
//        using (BinaryWriter writer = new BinaryWriter(ms))
//        {
//            ((ISignable)this).SerializeUnsigned(writer);
//            writer.Write((byte)1); writer.Write(Script);
//            writer.Write(Transactions.Select(p => p.Hash).ToArray());
//            writer.Flush();
//            return ms.ToArray();
//        }
        return null;
    }

    /**
     *  验证该区块头是否合法
     *  <returns>返回该区块头的合法性，返回true即为合法，否则，非法。</returns>
     */
    @Override public boolean verify()
    {
        return Verify(false);
    }

    /**
     *  验证该区块头是否合法
     *  <param name="completely">是否同时验证区块中的每一笔交易</param>
     *  <returns>返回该区块头的合法性，返回true即为合法，否则，非法。</returns>
     */
    public boolean Verify(boolean completely)
    {
//        if (Hash == Blockchain.GenesisBlock.Hash) return true;
//        if (Blockchain.Default.ContainsBlock(Hash)) return true;
//        if (completely && IsHeader) return false;
//        if (!Blockchain.Default.Ability.HasFlag(BlockchainAbility.TransactionIndexes) || !Blockchain.Default.Ability.HasFlag(BlockchainAbility.UnspentIndexes))
//            return false;
//        Block prev_header = Blockchain.Default.GetHeader(PrevBlock);
//        if (prev_header == null) return false;
//        if (prev_header.Height + 1 != Height) return false;
//        if (prev_header.Timestamp >= Timestamp) return false;
//        if (!this.VerifySignature()) return false;
//        if (completely)
//        {
//            if (NextMiner != Blockchain.GetMinerAddress(Blockchain.Default.GetMiners(Transactions).ToArray()))
//                return false;
//            foreach (Transaction tx in Transactions)
//                if (!tx.Verify()) return false;
//            Transaction tx_gen = Transactions.FirstOrDefault(p => p.Type == TransactionType.MinerTransaction);
//            if (tx_gen?.Outputs.Sum(p => p.Value) != CalculateNetFee(Transactions)) return false;
//        }
        return true;
    }
}