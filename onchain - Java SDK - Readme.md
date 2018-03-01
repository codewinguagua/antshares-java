

 

 

 

 

# 技术说明书

 

 

 

Java SDK

Draft v0.1

 

2016年9月7日

07 Sep 2016

    

#  [目录](Undefined)

 

[TOC]

# 1    目的

本设计说明书由onchain为内部技术团队制作。

本设计说明书旨在对Java SDK的基本结构与典型操作进行说明。

(注：因为SDK的类与功能较多，此文档未对SDK所有功能做详细说明）

本文档内容由onchain所有，第三方未经授权不得传播与使用。

 

# 2    主要Package介绍

| Package                                  | 说明                      |
| ---------------------------------------- | ----------------------- |
| *AntShares.Core*                         | SDK核心包，包括了区块管理代码与各类交易类型 |
| *AntShares.Cryptography*                 | 包含部分加密算法实现              |
| *AntShares.Implementations.Wallets.EntityFrameWork* | 钱包结构的实体定义               |
| *AntShares.IO*                           | 输入输出与序列化处理              |
| *AntShares.Network*                      | 网络通信封装                  |
| *AntShares.Wallets*                      | 钱包账户管理与合约实现             |
|                                          |                         |

 

# 3    主要类介绍

| 包                       | 类                     | 说明                        |
| ----------------------- | :-------------------- | ------------------------- |
| *AntShares.Core*        | *Transaction*         | 交易的基类，衍生各类交易子类            |
|                         | *RegisterTransaction* | 资产登记交易类型                  |
|                         | *IssueTransaction*    | 资产发行交易类型                  |
|                         | *ContractTransaction* | 普通交易类型                    |
|                         | *SignatureContext*    | 对各类交易进行签名的封装              |
| *AntShares.Implementat* | *UserWallet*          | 客户端钱包应用，用于交易发起等各类操作       |
| *AntShares.Wallets*     | *Wallet*              | 钱包的抽象类，定义的客户端钱包的属性结构与操作类型 |
|                         | *Contract*            | 交易合约的基类                   |
|                         | *SignatureContract*   | 单方签名的交易类型                 |
|                         | *MultiSigContract*    | 多方签名的交易类型                 |
|                         | *CustomContract*      | 客户定制的交易类型                 |





# 4    SDK系统运行流程

(1)创建/打开钱包

(2)创建/获取账户、合约和资产

(3)开启数据同步守护线程

(4)发起实时交易（eg:注册资产/分发资产/转账）

(5)构建、生成、封装交易

(6)签名、入库

(7)发送交易

  

​    ![AntShares](D:\Data\AntShares.jpg)

  ![AntShares](D:\Data\AntShares.jpg)

 

# 5    主要应用场景示例

## [5.1       资产登记](undefined)

**Step1**：先创建一个自己业务相关的Wallet类（如，myWallet），继承AntShares.Wallets.wallet抽象类，当前SDK中给出有一个默认的自定义钱包类UserWallet，如无特殊操作，则可以直接使用该钱包类。



**Step2**：myWallet 实现对本地数据库的增删改查的方法 （实现Wallet类中的抽象方法）和对外提供的获取云端中current_height区块链当前高度的方法。



**Step3**： 创建本地钱包，传递参数为：钱包路径+用户密码

`Wallet user = UserWallet.create(“d:\ant.db3”,“12345”);`



**Step4**： 等待同步云端区块链数据到本地钱包完成。该步骤主要是获取云端区块链中与当前钱包相关的资产信息，以便在后续步骤中作为支付使用。具体同步数据方法是：实例化RpcBlockchain用以获取云端区块链数据，然后将其注入到Blockchain中，最后在一个循环中判断本地钱包高度userWallet.current_height是否达到云端区块链高度Blockchain.current().headerHeight();若到达则同步完成，若没有达到则等待/继续循环判断。

 

**Step 5**： 实例化 RegisterTranscation 类，设置参数：

参数说明：

 ![AntShares-reg](D:\Data\AntShares-reg.jpg)`

**资产类型**：AssetType.Share和AssetType.Token

**资产名称**：可任意

**总量**：注册资产的数量，比如10000

**发行者公钥**：默认为当前钱包当前用户中的publicKey

**资产管理员**：默认为当前钱包当前合约中的地址散列值

Eg:

 ````
// 构造RegTx
RegisterTransaction regTx = new RegisterTransaction();
regTx.assetType = AssetType.Share;	// 资产类别
regTx.name = regTx.assetType == AssetType.Share ? "share" : "token";	// 资产名称
regTx.amount = Fixed8.parse("10000");	// 资产总量
regTx.issuer = Arrays.stream(userWallet.getAccounts()).findFirst().get().publicKey;	//发行者公钥
regTx.admin =Wallet.toScriptHash(Arrays.stream(
	userWallet.getContracts()).findFirst().get().address()); // 资产管理员的合约散列值
regTx.outputs = new TransactionOutput[0];
System.out.println("Transaction created");
 ````



**Step 6**:  调用UsersWallet里的makeTranscation(registerTrancation) 方法,返回构造完毕的Transaction，该步骤主要是初始化将该笔Transaction的资金来源TransactionInput和资金去处TransactionOutput。该资金主要是由于注册产生注册费用小蚁币，一般为100小蚁币，比如：注册一亿资产，需要100小蚁币。当前默认是注册所有资产所耗费用为0小蚁币。

````
// 生成RegTx
RegisterTransaction signedTransaction = userWallet.makeTransaction(regTx, Fixed8.ZERO);
````



 **Step 7**:  构造签名上下文类SignatureContext (上步返回的交易作为参数传入)

````
// 包装RegTx
SignatureContext context = new SignatureContext(signedTransaction);
````



**Step 8**: 调用UsersWallet中的Sign方法(将SignatureContext作为参数传入)，返回签完名的Transaction

```
// 签名
userWallet.sign(context);
```



**Step 9**: 调用签名完成之后的transactoin中的合约脚本方法，获取合约脚本到Transaction中。

```
// 获取签名合约脚本
signedTransaction.scripts = context.getScripts();
```



**Step 10**: 调用UsersWallet的saveTransaction方法,保存交易到本地钱包

```
// 入库
userWallet.saveTransaction(signedTransaction);
```

 

 **Step 11**: 广播交易：  

```
方式一：JSON RPC发送交易
```

1)   构造 RpcClient（url）, url为JSON PRC的http地址与端口链接

2)   构造 RpcNode (rpcClient)

3)    调用RpcNode.sendRawTransaction(Transaction), transcation为前步骤构造好的交易。

注：Transaction.hash() 为交易hash 或交易id , 可以通过 RpcNode.getRawTransaction(transaction id)从区块链获取已生成的交易。

```
// rpc发送
rpcNode.sendRawTransaction(signedTransaction);
```



```
方式二：调用LocalNode.Reply广播交易（LocalNode是本地节点实例，LocalNode机制另文叙述）
```

 

## [5.1       资产发行](undefined)

```
与“资产登记”类似，Step 2 中构造的是 IssueTransaction 类，参数结构：
```

  ![AntShares-iss](D:\Data\AntShares-iss.jpg)

 

**交易类型**：TransactionType.IssueTransaction

**额外特性**：可以存放各种交易说明/交易详情等信息，此处默认为null

**交易输入**：资产的来源，对于刚刚创建的钱包，此处默认为null

**交易输出**：表示发行资产的去处，该发行资产可以是小蚁股Antshare,可以是小蚁币Antcoin，还可以是先前注册的资产。构造发行资产TransactionOutput（tx）：

tx.assetId：表示资产编号，对于小蚁币和小蚁股，该资产编号为Hash()值，对于注册资产，该资产编号为注册资产的交易编号txid。在获取该交易编号的时候，对于已知该txid的资产，则可以直接将该txid传入，对于未知txid的资产，则可以从本地钱包中过滤筛选出来，钱包中包含资产包括：小蚁币/小蚁股和注册资产，过滤掉小蚁币和小蚁股，剩下的就是注册资产。

tx.value：表示金额，比如：1000

tx.scriptHash:表示收款人的地址，该地址可以形象理解为钱包中的银行卡，一个钱包中可以拥有多张银行卡。创建钱包时默认已经生成一张银行卡，该卡的地址信息可以通过获取钱包中的合约找到该卡的地址信息，转换格式即可获取到收款人的地址。发行资产给自己，则可以通过刚刚这种方式直接从钱包中拿取，发行资产该对方，则只需要知道对方的地址信息，然后通过Wallet.toAddress方法转换格式即可得到。

**脚本列表**：验证所需的脚本信息，后续步骤中通过签名完整之后的transaction获得。

Eg：

 ````
// 构建issTx
IssueTransaction issTx = new IssueTransaction();
TransactionOutput tx = new TransactionOutput();
tx.assetId = Arrays.stream(userWallet.findUnspentCoins()).filter(
			p -> !p.assetId.equals(Blockchain.ANTCOIN.hash())
				&&!p.assetId.equals(Blockchain.ANTSHARE.hash()))
			.map(p -> p.assetId).findAny().get();
tx.value = Fixed8.parse("30");
tx.scriptHash = Wallet.toScriptHash(toAddr);
issTx.outputs = new TransactionOutput[]{tx};
System.out.println("Transaction created, send to txid:"+tx.assetId);
 ````



 

## [5.2       普通交易](undefined)

```
与“资产登记”类似，Step 2 中构造的是 ContractTranscation类，参数结构：
```

  ![AntShares-con](D:\Data\AntShares-con.jpg)

 

**交易类型**：TransactionType.ContractTransaction

**额外特性**：可以存放各种交易说明/交易详情等信息，此处默认为null

**交易输入**：资产的来源，该资产可以时小蚁币/小蚁股，也可以是注册资产，还可以是从别处接收到的其他资产，构造交易输入TransactionInput(in)：

​	in.prevHash: 引用交易的散列值，即交易标号txid

​	in.index: 交易输出的索引值。

此处TransactionInput默认为null，即表示只能转账该钱包中已有的资产小蚁币/小蚁股/自己注册的资产。

**交易输出**：表示转账的接收方，构造该交易输出TransactionOutput与注册过程中构造该交易输出一样，具体可以参考“注册资产”。

Eg:

````
// 构建conTx
ContractTransaction conTx = new ContractTransaction();
TransactionOutput tx = new TransactionOutput();
tx.assetId = Arrays.stream(userWallet.findUnspentCoins()).filter(
				p -> !p.assetId.equals(Blockchain.ANTCOIN.hash()) || 
				!p.equals(Blockchain.ANTSHARE.hash())).findAny().get().assetId;
tx.value = Fixed8.parse("1");
tx.scriptHash = Wallet.toScriptHash(toAddr);
conTx.outputs = new TransactionOutput[] {tx};
System.out.println("Transaction created,txid:"+tx.assetId);
````



 