package com.demo.service;

import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.core.asset.State;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.crypto.SignatureScheme;

import java.util.Base64;

public class ContractService {

    private final static ContractService contractService = new ContractService();

    public static String contractCode = "0132c56b6a00527ac46a51527ac46a00c304696e69749c6409006501096c7566616a00c3046e616d659c64090065d8086c7566616a00c30673796d626f6c9c64090065b1086c7566616a00c308646563696d616c739c640900658b086c7566616a00c30b746f74616c537570706c799c640900651d086c7566616a00c30962616c616e63654f669c6424006a51c3c0519e640700006c7566616a51c300c36a52527ac46a52c36576076c7566616a00c3087472616e736665729c6440006a51c3c0539e640700006c7566616a51c300c36a53527ac46a51c351c36a54527ac46a51c352c36a55527ac46a53c36a54c36a55c35272657d056c7566616a00c30d7472616e736665724d756c74699c640c006a51c365c5046c7566616a00c30c7472616e7366657246726f6d9c645f006a51c3c0549e640700006c7566616a51c300c36a56527ac46a51c351c36a53527ac46a51c352c36a54527ac46a51c353c36a55527ac46a56c36a53c36a54c36a55c3537951795572755172755279527954727552727565fd006c7566616a00c307617070726f76659c6440006a51c3c0539e640700006c7566616a51c300c36a57527ac46a51c351c36a56527ac46a51c352c36a55527ac46a57c36a56c36a55c3527265f9026c7566616a00c309616c6c6f77616e63659c6432006a51c3c0529e640700006c7566616a51c300c36a57527ac46a51c351c36a56527ac46a57c36a56c37c650b006c756661006c756658c56b6a00527ac46a51527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a52527ac401026a53527ac46a53c36a00c37e6a51c37e6a54527ac46a52c36a54c37c681253797374656d2e53746f726167652e476574616c7566011fc56b6a00527ac46a51527ac46a52527ac46a53527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a54527ac401016a55527ac401026a56527ac46a00c3c001149e6317006a51c3c001149e630d006a52c3c001149e641a00611461646472657373206c656e677468206572726f72f0616a00c3681b53797374656d2e52756e74696d652e436865636b5769746e65737361009c640700006c7566616a55c36a51c37e6a57527ac46a54c36a57c37c681253797374656d2e53746f726167652e476574616a58527ac46a53c36a58c3a0630b006a53c3009f64080061006c7566616a56c36a51c37e6a00c37e6a59527ac46a54c36a59c37c681253797374656d2e53746f726167652e476574616a5a527ac46a55c36a52c37e6a5b527ac46a53c36a5ac3a0640700006c7566616a53c36a5ac39c6449006a54c36a59c37c681553797374656d2e53746f726167652e44656c657465616a54c36a57c36a58c36a53c3945272681253797374656d2e53746f726167652e50757461624c00616a54c36a59c36a5ac36a53c3945272681253797374656d2e53746f726167652e507574616a54c36a57c36a58c36a53c3945272681253797374656d2e53746f726167652e50757461616a54c36a5bc37c681253797374656d2e53746f726167652e476574616a5c527ac46a54c36a5bc36a5cc36a53c3935272681253797374656d2e53746f726167652e507574616a51c36a52c36a53c35272087472616e7366657254c1681553797374656d2e52756e74696d652e4e6f74696679516c75660111c56b6a00527ac46a51527ac46a52527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a53527ac401026a54527ac46a51c3c001149e630d006a00c3c001149e641a00611461646472657373206c656e677468206572726f72f0616a00c3681b53797374656d2e52756e74696d652e436865636b5769746e65737361009c640700006c7566616a52c36a00c365ba02a0630b006a52c3009f64080061006c7566616a54c36a00c37e6a51c37e6a55527ac46a53c36a55c36a52c35272681253797374656d2e53746f726167652e507574616a00c36a51c36a52c3527208617070726f76616c54c1681553797374656d2e52756e74696d652e4e6f74696679516c756659c56b6a00527ac4006a52527ac46a00c3c06a53527ac4616a52c36a53c39f6473006a00c36a52c3c36a51527ac46a52c351936a52527ac46a51c3c0539e6420001b7472616e736665724d756c746920706172616d73206572726f722ef0616a51c300c36a51c351c36a51c352c35272652900009c64a2ff157472616e736665724d756c7469206661696c65642ef06288ff616161516c75660117c56b6a00527ac46a51527ac46a52527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a53527ac401016a54527ac46a51c3c001149e630d006a00c3c001149e641a00611461646472657373206c656e677468206572726f72f0616a00c3681b53797374656d2e52756e74696d652e436865636b5769746e65737361009c630b006a52c3009f64080061006c7566616a54c36a00c37e6a55527ac46a53c36a55c37c681253797374656d2e53746f726167652e476574616a56527ac46a52c36a56c3a0640700006c7566616a52c36a56c39c6425006a53c36a55c37c681553797374656d2e53746f726167652e44656c65746561622800616a53c36a55c36a56c36a52c3945272681253797374656d2e53746f726167652e50757461616a54c36a51c37e6a57527ac46a53c36a57c37c681253797374656d2e53746f726167652e476574616a58527ac46a53c36a57c36a58c36a52c3935272681253797374656d2e53746f726167652e507574616a00c36a51c36a52c35272087472616e7366657254c1681553797374656d2e52756e74696d652e4e6f74696679516c756658c56b6a00527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a51527ac401016a52527ac46a00c3c001149e6419001461646472657373206c656e677468206572726f72f0616a51c36a52c36a00c37e7c681253797374656d2e53746f726167652e476574616c756655c56b681953797374656d2e53746f726167652e476574436f6e74657874616a00527ac40b546f74616c537570706c796a51527ac46a00c36a51c37c681253797374656d2e53746f726167652e476574616c756654c56b586a00527ac46a00c36c756654c56b034d59546a00527ac46a00c36c756654c56b074d79546f6b656e6a00527ac46a00c36c75660113c56b681953797374656d2e53746f726167652e476574436f6e74657874616a00527ac40400e1f5056a51527ac42241557235515566654241447136424d593654703579754d73554e47707344376e4c5a75148f651d459b4f146380dab28e7cfb9d4bb9c3fcd16a52527ac40400ca9a3b6a53527ac401016a54527ac40b546f74616c537570706c796a55527ac46a52c3c001149e6432000e4f776e657220696c6c6567616c2151c176c9681553797374656d2e52756e74696d652e4e6f7469667961006c7566616a00c36a55c37c681253797374656d2e53746f726167652e4765746164340014416c726561647920696e697469616c697a656421681553797374656d2e52756e74696d652e4e6f7469667961006c7566616a53c36a51c3956a56527ac46a00c36a55c36a56c35272681253797374656d2e53746f726167652e507574616a00c36a54c36a52c37e6a56c35272681253797374656d2e53746f726167652e50757461006a52c36a56c35272087472616e7366657254c1681553797374656d2e52756e74696d652e4e6f74696679516c7566006c75665ec56b6a00527ac46a51527ac46a51c36a00c3946a52527ac46a52c3c56a53527ac4006a54527ac46a00c36a55527ac461616a00c36a51c39f6433006a54c36a55c3936a56527ac46a56c36a53c36a54c37bc46a54c351936a54527ac46a55c36a54c3936a00527ac462c8ff6161616a53c36c7566";
    public static String privatekey= "274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b";
    public static String privatekey0 = "67ae8a3731709d8c820c03b200b9552ec61e6634cbcaf8a6a1f9d8f9f0f608";
    public static String privatekey1 = "49855b16636e70f100cc5f4f42bc20a6535d7414fb8845e7310f8dd065a97221";
    public static String privatekey2 = "1094e90dd7c4fdfd849c14798d725ac351ae0d924b29a279a9ffa77d5737bd96";
    public static String privatekey3 = "bc254cf8d3910bc615ba6bf09d4553846533ce4403bc24f58660ae150a6d64cf";
    public static String privatekey4 = "06bda156eda61222693cc6f8488557550735c329bc7ca91bd2994c894cd3cbc8";
    public static String privatekey5 = "f07d5a2be17bde8632ec08083af8c760b41b5e8e0b5de3703683c3bdcfb91549";

    private ContractService(){

    }

    public static ContractService getInstance(){
        return contractService;
    }

    public static void testDemo(){
        try{
            OntSdk ontSdk = getOntSdk();

            String privateKey = Account.getGcmDecodedPrivateKey("Q85bbTpl67SoQ07DJ1Vbx/2UPH/+dRTqIX1AN2jO0T38jseU6ef8C4DiAxAulp8P","password","AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ",Base64.getDecoder().decode("OqeBJgNFmdPxy8uB4oP4gg=="),16384,SignatureScheme.SHA256WITHECDSA);
            System.out.println(privateKey);
            Account account = new Account(Helper.hexToBytes(privatekey),SignatureScheme.SHA256WITHECDSA);
            Account acct1 = new Account(Helper.hexToBytes(privatekey1),SignatureScheme.SHA256WITHECDSA);
            Account acct2 = new Account(Helper.hexToBytes(privatekey2),SignatureScheme.SHA256WITHECDSA);
            Account acct3 = new Account(Helper.hexToBytes(privatekey3),SignatureScheme.SHA256WITHECDSA);
            Account acct4 = new Account(Helper.hexToBytes(privatekey4),SignatureScheme.SHA256WITHECDSA);
            Account acct5 = new Account(Helper.hexToBytes(privatekey5),SignatureScheme.SHA256WITHECDSA);

            Account acct = new com.github.ontio.account.Account(Helper.hexToBytes(privatekey0), ontSdk.defaultSignScheme);

            // 部署OEP4数字资产合约
            if(false){
                System.out.println("ContractAddress:" + Address.AddressFromVmCode(contractCode).toHexString());

                ontSdk.vm().setCodeAddress(Address.AddressFromVmCode(contractCode).toHexString());
                Transaction tx = ontSdk.vm().makeDeployCodeTransaction(contractCode, true, "OEP4",
                        "v1.0", "AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ", "king@onchain.com", "OEP4 Description", account.getAddressU160().toBase58(),30000000L,0);
                ontSdk.signTx(tx, new Account[][]{{account}});
                String txHex = Helper.toHexString(tx.toArray());

                System.out.println(txHex);
                Object result = ontSdk.getConnect().syncSendRawTransaction(txHex);
                System.out.println(result);
                System.exit(0);
            }

            // 初始化
            if(true){
                showBalance(ontSdk,new Account[]{account});

//                String result = ontSdk.neovm().oep4().sendInit(acct,account,20000,500);
//                System.out.println(result);
//                Thread.sleep(6000);
//                System.out.println(ontSdk.getConnect().getSmartCodeEvent(result));

                System.out.println(ontSdk.neovm().oep4().queryDecimals());
                System.out.println(ontSdk.neovm().oep4().queryName());
                System.out.println(ontSdk.neovm().oep4().querySymbol());
                System.out.println(ontSdk.neovm().oep4().queryTotalSupply());
                showBalance(ontSdk,new Account[]{account});
            }


            // 查询余额
            if(false){
                showBalance(ontSdk,new Account[]{account, acct});
            }


            // 转账
            if(false){
                showBalance(ontSdk,new Account[]{account, acct});
                String txhash = ontSdk.neovm().oep4().sendTransfer(account, acct.getAddressU160().toBase58(),1000000000,account,20000,500);
                Thread.sleep(6000);
                showBalance(ontSdk,new Account[]{account, acct});
            }


            // 批量转账
            if(false){
                showBalance(ontSdk,new Account[]{account, acct, acct1, acct2});
                Account[] accounts = new Account[]{account, acct};
                State state = new State(account.getAddressU160(), acct1.getAddressU160(),10);
                State state2 = new State(account.getAddressU160(), acct2.getAddressU160(),10);
                String txhash = ontSdk.neovm().oep4().sendTransferMulti(accounts, new State[]{state,state2},account,20000,500);
                Thread.sleep(6000);
                showBalance(ontSdk,new Account[]{account, acct, acct1, acct2});
            }


            // 授权 + 查询 + 交易
            if(true){
                // account授权给acct1账户，额度为1000
                System.out.println(ontSdk.neovm().oep4().queryAllowance(account.getAddressU160().toBase58(), acct1.getAddressU160().toBase58()));
                String txhash0 = ontSdk.neovm().oep4().sendApprove(account, acct1.getAddressU160().toBase58(),1000000000,account,20000,500);
                Thread.sleep(6000);
                System.out.println("event: " + ontSdk.getConnect().getSmartCodeEvent(txhash0));
                System.out.println("First check: " + ontSdk.neovm().oep4().queryAllowance(account.getAddressU160().toBase58(), acct1.getAddressU160().toBase58()));

                // acct1将account账户的数字资产转到自己账户（第一次金额超过授权额度，第二次等于授权额度）
                String txhash1 = ontSdk.neovm().oep4().sendTransferFrom(acct1,account.getAddressU160().toBase58(),acct1.getAddressU160().toBase58(),1000000001,account,20000,500);
                Thread.sleep(6000);
                System.out.println(ontSdk.getConnect().getSmartCodeEvent(txhash1));
                System.out.println("Second check: " + ontSdk.neovm().oep4().queryAllowance(account.getAddressU160().toBase58(),acct1.getAddressU160().toBase58()));

                String txhash2 = ontSdk.neovm().oep4().sendTransferFrom(acct1,account.getAddressU160().toBase58(),acct1.getAddressU160().toBase58(),1000000000,account,20000,500);
                Thread.sleep(6000);
                System.out.println(ontSdk.getConnect().getSmartCodeEvent(txhash2));
                System.out.println("Last check: " + ontSdk.neovm().oep4().queryAllowance(account.getAddressU160().toBase58(),acct1.getAddressU160().toBase58()));
                return;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static OntSdk getOntSdk() throws Exception{
//        String ip = "http://127.0.0.1";
//        String ip = "http://polaris1.ont.io";
        String ip = "http://dappnode1.ont.io";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + "：" +  "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());
        wm.neovm().oep4().setContractAddress("1514857e55b0f711af93c9b5a3a3eb32b53efaab");
//        wm.neovm().oep4().setContractAddress("25277b421a58cfc2ef5836767e54eb7abdd31afd");
//        wm.neovm().oep4().setContractAddress("2a9cc8a5d0644283e7d7705abe5bbcb979c9bb03");
        wm.openWalletFile("nep5.json");

        return wm;
    }

    public static void showBalance(OntSdk ontSdk,Account[] accounts) throws Exception {
        for (int i=0;i<accounts.length;i++){
            int a = i+1;
            System.out.println("account"+ a +":"+ ontSdk.neovm().oep4().queryBalanceOf(accounts[i].getAddressU160().toBase58()));
        }
    }
}
