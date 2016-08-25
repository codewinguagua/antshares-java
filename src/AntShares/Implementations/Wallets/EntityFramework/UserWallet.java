package AntShares.Implementations.Wallets.EntityFramework;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import AntShares.Fixed8;
import AntShares.UInt160;
import AntShares.UInt256;
import AntShares.Core.Block;
import AntShares.Wallets.SignatureContract;
import AntShares.Wallets.Wallet;

public class UserWallet extends Wallet
{
    // TODO
    //public event EventHandler<Iterable<TransactionInfo>> TransactionsChanged;
    // TODO
    String DbPath = "DbPath";

    protected UserWallet(String path, String password, boolean create) throws BadPaddingException, IllegalBlockSizeException
    {
        super(path, password, create);
    }

    // TODO
//    protected UserWallet(String path, SecureString password, boolean create)
//    {
//        super(path, password, create);
//    }

    @Override public void addContract(AntShares.Wallets.Contract contract)
    {
        super.addContract(contract);
        WalletDataContext ctx = new WalletDataContext(DbPath);
//        Contract db_contract = ctx.Contracts.FirstOrDefault(p => p.ScriptHash.SequenceEqual(contract.ScriptHash.ToArray()));
//        if (db_contract == null)
//        {
//            db_contract = ctx.Contracts.Add(new Contract
//            {
//                Type = contract.GetType().ToString(),
//                RawData = contract.ToArray(),
//                ScriptHash = contract.ScriptHash.ToArray(),
//                PublicKeyHash = contract.PublicKeyHash.ToArray()
//            }).Entity;
//        }
//        else
//        {
//            db_contract.PublicKeyHash = contract.PublicKeyHash.ToArray();
//        }
//        ctx.SaveChanges();
    }

    @Override
    protected void buildDatabase()
    {
        WalletDataContext ctx = new WalletDataContext(DbPath);
//        ctx.Database.EnsureDeleted();
//        ctx.Database.EnsureCreated();
    }

    public static UserWallet Create(String path, String password)
    {
        UserWallet wallet;
		try
		{
			wallet = new UserWallet(path, password, true);
		}
		catch (BadPaddingException | IllegalBlockSizeException ex)
		{
			throw new RuntimeException(ex);
		}
        wallet.createAccount();
        return wallet;
    }

    // TODO
//    public static UserWallet Create(String path, SecureString password)
//    {
//        UserWallet wallet = new UserWallet(path, password, true);
//        wallet.CreateAccount();
//        return wallet;
//    }

    @Override public AntShares.Wallets.Account createAccount(byte[] privateKey)
    {
        AntShares.Wallets.Account account = super.createAccount(privateKey);
        OnCreateAccount(account);
        addContract(SignatureContract.Create(account.PublicKey));
        return account;
    }

    @Override public boolean deleteAccount(UInt160 publicKeyHash)
    {
        boolean flag = super.deleteAccount(publicKeyHash);
        if (flag)
        {
            WalletDataContext ctx = new WalletDataContext(DbPath);
            // TODO
//            Account account = ctx.Accounts.FirstOrDefault(p => p.PublicKeyHash.SequenceEqual(publicKeyHash.ToArray()));
//            if (account != null)
//            {
//                ctx.Accounts.Remove(account);
//                ctx.SaveChanges();
//            }
        }
        return flag;
    }

    @Override public boolean deleteContract(UInt160 scriptHash)
    {
        boolean flag = super.deleteContract(scriptHash);
        if (flag)
        {
            WalletDataContext ctx = new WalletDataContext(DbPath);
//            Contract contract = ctx.Contracts.FirstOrDefault(p => p.ScriptHash.SequenceEqual(scriptHash.ToArray()));
//            if (contract != null)
//            {
//                ctx.Contracts.Remove(contract);
//                ctx.SaveChanges();
//            }
        }
        return flag;
    }

    @Override public AntShares.Wallets.Coin[] findUnspentCoins(UInt256 asset_id, Fixed8 amount)
    {
        //return FindUnspentCoins(FindUnspentCoins().Where(p => GetContract(p.ScriptHash) is SignatureContract), asset_id, amount) ?? base.FindUnspentCoins(asset_id, amount);
    	// TODO
    	return null;
    }

    private static Iterable<TransactionInfo> GetTransactionInfo(Iterable<Transaction> transactions)
    {
//        return transactions.Select(p => new TransactionInfo
//        {
//            Transaction = AntShares.Core.Transaction.DeserializeFrom(p.RawData),
//            Height = p.Height,
//            Time = p.Time
//        });
    	// TODO
    	return null;
    }

    public static Version GetVersion(String path)
    {
//        byte[] buffer;
//        using (WalletDataContext ctx = new WalletDataContext(path))
//        {
//            buffer = ctx.Keys.FirstOrDefault(p => p.Name == "Version")?.Value;
//        }
//        if (buffer == null) return new Version();
//        int major = BitConverter.ToInt32(buffer, 0);
//        int minor = BitConverter.ToInt32(buffer, 4);
//        int build = BitConverter.ToInt32(buffer, 8);
//        int revision = BitConverter.ToInt32(buffer, 12);
//        return new Version(major, minor, build, revision);
    	return new Version();
    }

    @Override protected AntShares.Wallets.Account[] loadAccounts()
    {
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            foreach (byte[] encryptedPrivateKey in ctx.Accounts.Select(p => p.PrivateKeyEncrypted))
//            {
//                byte[] decryptedPrivateKey = DecryptPrivateKey(encryptedPrivateKey);
//                AntShares.Wallets.Account account = new AntShares.Wallets.Account(decryptedPrivateKey);
//                Array.Clear(decryptedPrivateKey, 0, decryptedPrivateKey.Length);
//                yield return account;
//            }
//        }
    	return null;
    }

    @Override protected AntShares.Wallets.Coin[] loadCoins()
    {
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            foreach (Coin coin in ctx.Coins)
//            {
//                yield return new AntShares.Wallets.Coin
//                {
//                    Input = new TransactionInput
//                    {
//                        PrevHash = new UInt256(coin.TxId),
//                        PrevIndex = coin.Index
//                    },
//                    AssetId = new UInt256(coin.AssetId),
//                    Value = new Fixed8(coin.Value),
//                    ScriptHash = new UInt160(coin.ScriptHash),
//                    State = coin.State
//                };
//            }
//        }
    	return null;
    }

    @Override protected AntShares.Wallets.Contract[] loadContracts()
    {
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            foreach (Contract contract in ctx.Contracts)
//            {
//                Type type = Type.GetType(contract.Type, false);
//                if (type == null || !typeof(AntShares.Wallets.Contract).IsAssignableFrom(type)) continue;
//                yield return (AntShares.Wallets.Contract)contract.RawData.AsSerializable(type);
//            }
//        }
    	return null;
    }

    @Override protected byte[] loadStoredData(String name)
    {
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            return ctx.Keys.FirstOrDefault(p => p.Name == name)?.Value;
//        }
    	return null;
    }

    public Iterable<TransactionInfo> LoadTransactions()
    {
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            return GetTransactionInfo(ctx.Transactions.ToArray());
//        }
    	return null;
    }

    public static void Migrate(String path_old, String path_new)
    {
//        Version current_version = Assembly.GetExecutingAssembly().GetName().Version;
//        using (WalletDataContext ctx_old = new WalletDataContext(path_old))
//        using (WalletDataContext ctx_new = new WalletDataContext(path_new))
//        {
//            ctx_new.Database.EnsureCreated();
//            ctx_new.Accounts.AddRange(ctx_old.Accounts);
//            ctx_new.Contracts.AddRange(ctx_old.Contracts);
//            ctx_new.Keys.AddRange(ctx_old.Keys.Where(p => p.Name != "Height" && p.Name != "Version"));
//            SaveStoredData(ctx_new, "Height", BitConverter.GetBytes(0));
//            SaveStoredData(ctx_new, "Version", new[] { current_version.Major, current_version.Minor, current_version.Build, current_version.Revision }.Select(p => BitConverter.GetBytes(p)).SelectMany(p => p).ToArray());
//            ctx_new.SaveChanges();
//        }
    }

    private void OnCoinsChanged(WalletDataContext ctx, Iterable<AntShares.Wallets.Coin> added, Iterable<AntShares.Wallets.Coin> changed, Iterable<AntShares.Wallets.Coin> deleted)
    {
//        foreach (AntShares.Wallets.Coin coin in added)
//        {
//            ctx.Coins.Add(new Coin
//            {
//                TxId = coin.Input.PrevHash.ToArray(),
//                Index = coin.Input.PrevIndex,
//                AssetId = coin.AssetId.ToArray(),
//                Value = coin.Value.GetData(),
//                ScriptHash = coin.ScriptHash.ToArray(),
//                State = CoinState.Unspent
//            });
//        }
//        foreach (AntShares.Wallets.Coin coin in changed)
//        {
//            ctx.Coins.First(p => p.TxId.SequenceEqual(coin.Input.PrevHash.ToArray()) && p.Index == coin.Input.PrevIndex).State = coin.State;
//        }
//        foreach (AntShares.Wallets.Coin coin in deleted)
//        {
//            Coin unspent_coin = ctx.Coins.FirstOrDefault(p => p.TxId.SequenceEqual(coin.Input.PrevHash.ToArray()) && p.Index == coin.Input.PrevIndex);
//            ctx.Coins.Remove(unspent_coin);
//        }
    }

    private void OnCreateAccount(AntShares.Wallets.Account account)
    {
//        byte[] decryptedPrivateKey = new byte[96];
//        Buffer.BlockCopy(account.PublicKey.EncodePoint(false), 1, decryptedPrivateKey, 0, 64);
//        using (account.Decrypt())
//        {
//            Buffer.BlockCopy(account.PrivateKey, 0, decryptedPrivateKey, 64, 32);
//        }
//        byte[] encryptedPrivateKey = EncryptPrivateKey(decryptedPrivateKey);
//        Array.Clear(decryptedPrivateKey, 0, decryptedPrivateKey.Length);
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            Account db_account = ctx.Accounts.FirstOrDefault(p => p.PublicKeyHash.SequenceEqual(account.PublicKeyHash.ToArray()));
//            if (db_account == null)
//            {
//                db_account = ctx.Accounts.Add(new Account
//                {
//                    PrivateKeyEncrypted = encryptedPrivateKey,
//                    PublicKeyHash = account.PublicKeyHash.ToArray()
//                }).Entity;
//            }
//            else
//            {
//                db_account.PrivateKeyEncrypted = encryptedPrivateKey;
//            }
//            ctx.SaveChanges();
//        }
    }

    @Override protected void onProcessNewBlock(Block block, AntShares.Core.Transaction[] transactions, AntShares.Wallets.Coin[] added, AntShares.Wallets.Coin[] changed, AntShares.Wallets.Coin[] deleted)
    {
        Transaction[] tx_changed;
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            foreach (Transaction db_tx in ctx.Transactions.Where(p => !p.Height.HasValue))
//                if (block.Transactions.Any(p => p.Hash == new UInt256(db_tx.Hash)))
//                    db_tx.Height = block.Height;
//            foreach (AntShares.Core.Transaction tx in transactions)
//            {
//                Transaction db_tx = ctx.Transactions.FirstOrDefault(p => p.Hash.SequenceEqual(tx.Hash.ToArray()));
//                if (db_tx == null)
//                {
//                    ctx.Transactions.Add(new Transaction
//                    {
//                        Hash = tx.Hash.ToArray(),
//                        Type = tx.Type,
//                        RawData = tx.ToArray(),
//                        Height = block.Height,
//                        Time = block.Timestamp.ToDateTime()
//                    });
//                }
//                else
//                {
//                    db_tx.Height = block.Height;
//                }
//            }
//            tx_changed = ctx.ChangeTracker.Entries<Transaction>().Where(p => p.State != EntityState.Unchanged).Select(p => p.Entity).ToArray();
//            OnCoinsChanged(ctx, added, changed, deleted);
//            ctx.Keys.First(p => p.Name == "Height").Value = BitConverter.GetBytes(WalletHeight);
//            ctx.SaveChanges();
//        }
//        if (tx_changed.Length > 0)
//            TransactionsChanged?.Invoke(this, GetTransactionInfo(tx_changed));
    }

    @Override protected void onSendTransaction(AntShares.Core.Transaction tx, AntShares.Wallets.Coin[] added, AntShares.Wallets.Coin[] changed)
    {
//        Transaction tx_changed;
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            tx_changed = ctx.Transactions.Add(new Transaction
//            {
//                Hash = tx.Hash.ToArray(),
//                Type = tx.Type,
//                RawData = tx.ToArray(),
//                Height = null,
//                Time = DateTime.Now
//            }).Entity;
//            OnCoinsChanged(ctx, added, changed, Enumerable.Empty<AntShares.Wallets.Coin>());
//            ctx.SaveChanges();
//        }
//        TransactionsChanged?.Invoke(this, GetTransactionInfo(new[] { tx_changed }));
    }

    public static UserWallet Open(String path, String password) throws BadPaddingException, IllegalBlockSizeException
    {
        return new UserWallet(path, password, false);
    }

    //TODO
//    public static UserWallet Open(String path, SecureString password)
//    {
//        return new UserWallet(path, password, false);
//    }

    @Override public void rebuild()
    {
        synchronized (syncroot)
        {
            super.rebuild();
//            using (WalletDataContext ctx = new WalletDataContext(DbPath))
//            {
//                ctx.Keys.First(p => p.Name == "Height").Value = BitConverter.GetBytes(0);
//                ctx.Database.ExecuteSqlCommand($"DELETE FROM [{nameof(Transaction)}]");
//                ctx.Database.ExecuteSqlCommand($"DELETE FROM [{nameof(Coin)}]");
//                ctx.SaveChanges();
//            }
        }
    }

    @Override protected void saveStoredData(String name, byte[] value)
    {
//        using (WalletDataContext ctx = new WalletDataContext(DbPath))
//        {
//            SaveStoredData(ctx, name, value);
//            ctx.SaveChanges();
//        }
    }

    private static void SaveStoredData(WalletDataContext ctx, String name, byte[] value)
    {
//        Key key = ctx.Keys.FirstOrDefault(p => p.Name == name);
//        if (key == null)
//        {
//            ctx.Keys.Add(new Key
//            {
//                Name = name,
//                Value = value
//            });
//        }
//        else
//        {
//            key.Value = value;
//        }
    }
}