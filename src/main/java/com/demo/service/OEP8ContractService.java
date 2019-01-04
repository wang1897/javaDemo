package com.demo.service;

import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.crypto.SignatureScheme;
import com.github.ontio.smartcontract.neovm.oep8.Oep8State;
import com.github.ontio.smartcontract.neovm.oep8.TransferFrom;


public class OEP8ContractService {
    public static String contractCode = "0154c56b6a00527ac46a51527ac46a00c3046e616d659c6424006a51c3c0519e640700006c7566616a51c300c36a52527ac46a52c3651b156c7566616a00c30673796d626f6c9c6424006a51c3c0519e640700006c7566616a51c300c36a52527ac46a52c36598146c7566616a00c30b746f74616c537570706c799c6424006a51c3c0519e640700006c7566616a51c300c36a52527ac46a52c3650b146c7566616a00c30962616c616e63654f669c6432006a51c3c0529e640700006c7566616a51c300c36a53527ac46a51c351c36a52527ac46a53c36a52c37c656a136c7566616a00c3087472616e736665729c645f006a51c3c0549e640700006c7566616a51c300c36a54527ac46a51c351c36a55527ac46a51c352c36a52527ac46a51c353c36a56527ac46a54c36a55c36a52c36a56c3537951795572755172755279527954727552727565f3106c7566616a00c30d7472616e736665724d756c74699c640c006a51c3650b106c7566616a00c307617070726f76659c645f006a51c3c0549e640700006c7566616a51c300c36a57527ac46a51c351c36a58527ac46a51c352c36a52527ac46a51c353c36a56527ac46a57c36a58c36a52c36a56c35379517955727551727552795279547275527275659c0e6c7566616a00c30c617070726f76654d756c74699c640c006a51c365b80d6c7566616a00c309616c6c6f77616e63659c6440006a51c3c0539e640700006c7566616a51c300c36a57527ac46a51c351c36a58527ac46a51c352c36a52527ac46a57c36a58c36a52c3527265f50c6c7566616a00c30c7472616e7366657246726f6d9c646c006a51c3c0559e640700006c7566616a51c300c36a58527ac46a51c351c36a54527ac46a51c352c36a55527ac46a51c353c36a52527ac46a51c354c36a56527ac46a58c36a54c36a55c36a52c36a56c354795179567275517275537952795572755272756544096c7566616a00c3117472616e7366657246726f6d4d756c74699c640c006a51c3654b086c7566616a00c304696e69749c6417006a51c3c0009e640700006c756661651e076c7566616a00c30a62616c616e6365734f669c6424006a51c3c0519e640700006c7566616a51c300c36a53527ac46a53c365dd006c7566616a00c30e746f74616c42616c616e63654f669c6424006a51c3c0519e640700006c7566616a51c300c36a53527ac46a53c36573016c7566616a00c3046d696e749c6432006a51c3c0529e640700006c7566616a51c300c36a52527ac46a51c351c36a59527ac46a52c36a59c37c65e2046c756661006c756657c56b6a00527ac4044e616d656a51527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a00c36a51c37c6528077c681253797374656d2e53746f726167652e47657461640700516c756661006c7566006c75665bc56b6a00527ac40101010201030104010555c176c96a51527ac40742616c616e63656a52527ac4005152535455c176c96a53527ac400c176c96a54527ac4006a57527ac46a53c3c06a58527ac4616a57c36a58c39f646f006a53c36a57c3c36a55527ac46a57c351936a57527ac46a51c36a55c3c36a56527ac46a54c3681953797374656d2e53746f726167652e476574436f6e74657874616a56c36a52c37c655e066a00c37c6557067c681253797374656d2e53746f726167652e47657461c8628cff6161616a54c36c75665bc56b6a00527ac40101010201030104010555c176c96a51527ac40742616c616e63656a52527ac4005152535455c176c96a53527ac4006a54527ac4006a57527ac46a53c3c06a58527ac4616a57c36a58c39f6477006a53c36a57c3c36a55527ac46a57c351936a57527ac46a51c36a55c3c36a56527ac46a54c3681953797374656d2e53746f726167652e476574436f6e74657874616a56c36a52c37c6593056a00c37c658c057c681253797374656d2e53746f726167652e476574617c6586106a54527ac46284ff6161616a54c36c75660117c56b2241557235515566654241447136424d593654703579754d73554e47707344376e4c5a75148f651d459b4f146380dab28e7cfb9d4bb9c3fcd16a00527ac40101010201030104010555c176c96a51527ac4044e616d656a52527ac40653796d626f6c6a53527ac40742616c616e63656a54527ac40b546f74616c537570706c796a55527ac4005152535455c176c96a56527ac40e546f6b656e4e616d6546697273740f546f6b656e4e616d655365636f6e640e546f6b656e4e616d6554686972640f546f6b656e4e616d65466f757274680e546f6b656e4e616d65466966746855c176c96a57527ac403544e4603544e5303544e4803544e4f03544e4955c176c96a58527ac403a0860103400d0303e0930403801a060320a10755c176c96a59527ac4006a5f527ac46a56c3c06a60527ac4616a5fc36a60c39f6493016a56c36a5fc3c36a5a527ac46a5fc351936a5f527ac46a57c36a5ac3c36a5b527ac46a58c36a5ac3c36a5c527ac46a59c36a5ac3c36a5d527ac46a51c36a5ac3c36a5e527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a5ec36a52c37c65b4036a5bc35272681253797374656d2e53746f726167652e50757461681953797374656d2e53746f726167652e476574436f6e74657874616a5ec36a53c37c6574036a5cc35272681253797374656d2e53746f726167652e50757461681953797374656d2e53746f726167652e476574436f6e74657874616a5ec36a55c37c6534036a5dc35272681253797374656d2e53746f726167652e50757461681953797374656d2e53746f726167652e476574436f6e74657874616a5ec36a54c37c65f4026a00c37c65ed026a5dc35272681253797374656d2e53746f726167652e50757461006a00c36a5ec36a5dc35379517955727551727552795279547275527275087472616e7366657255c1681553797374656d2e52756e74696d652e4e6f746966796268fe616161516c756660c56b6a00527ac46a51527ac42241557235515566654241447136424d593654703579754d73554e47707344376e4c5a75148f651d459b4f146380dab28e7cfb9d4bb9c3fcd16a52527ac40742616c616e63656a53527ac40b546f74616c537570706c796a54527ac46a52c3681b53797374656d2e52756e74696d652e436865636b5769746e6573736165820d756a00c36598fa65780d756a00c3657e0b6a55527ac46a51c300a065640d756a51c36a55c37c65e70c6a56527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a00c36a54c37c65a8016a56c35272681253797374656d2e53746f726167652e50757461681953797374656d2e53746f726167652e476574436f6e74657874616a00c36a53c37c6568016a52c37c6561016a51c36a52c36a00c37c65870a7c65640c5272681253797374656d2e53746f726167652e50757461006a52c36a00c36a51c35379517955727551727552795279547275527275087472616e7366657255c1681553797374656d2e52756e74696d652e4e6f74696679516c75665cc56b2241557235515566654241447136424d593654703579754d73554e47707344376e4c5a75148f651d459b4f146380dab28e7cfb9d4bb9c3fcd16a00527ac40b496e697469616c697a65646a51527ac46a00c365db0b75681953797374656d2e53746f726167652e476574436f6e74657874616a51c37c681253797374656d2e53746f726167652e47657461635f006503fb6a52527ac46a52c3519c644200681953797374656d2e53746f726167652e476574436f6e74657874616a51c304545255455272681253797374656d2e53746f726167652e50757461516c7566610a696e6974206572726f72f061006c756655c56b6a00527ac46a51527ac46a00c3015f7e6a51c37e6c756659c56b6a00527ac4006a52527ac46a00c3c06a53527ac4616a52c36a53c39f64b0006a00c36a52c3c36a51527ac46a52c351936a52527ac46a51c3c0559e642c00277472616e7366657246726f6d4d756c7469206661696c6564202d20696e707574206572726f7221f0616a51c300c36a51c351c36a51c352c36a51c353c36a51c354c35479517956727551727553795279557275527275653e00009c647aff2a7472616e7366657246726f6d4d756c7469206661696c6564202d207472616e73666572206572726f7221f0624bff616161516c75660120c56b6a00527ac46a51527ac46a52527ac46a53527ac46a54527ac40742616c616e63656a55527ac407417070726f76656a56527ac46a00c365140a756a00c3653e0a756a51c365370a756a52c365300a756a53c3655df7653d0a756a53c36a55c37c65acfe6a51c37c65a5fe6a57527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a57c37c681253797374656d2e53746f726167652e476574616a58527ac46a58c36a54c3a265e209756a54c300a065d909756a53c36a55c37c6548fe6a52c37c6541fe6a59527ac46a53c36a56c37c6532fe6a51c37c652bfe6a00c37c6524fe6a5a527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a5ac37c681253797374656d2e53746f726167652e476574616a5b527ac46a54c36a5bc3a06437002f796f7520617265206e6f7420616c6c6f77656420746f20776974686472617720746f6f206d616e7920746f6b656e73f0620901616a54c36a5bc39c647d00681953797374656d2e53746f726167652e476574436f6e74657874616a5ac37c681553797374656d2e53746f726167652e44656c65746561681953797374656d2e53746f726167652e476574436f6e74657874616a58c36a54c37c6530087c681553797374656d2e53746f726167652e44656c6574656162840061681953797374656d2e53746f726167652e476574436f6e74657874616a5ac36a5bc36a54c37c65ea075272681253797374656d2e53746f726167652e50757461681953797374656d2e53746f726167652e476574436f6e74657874616a57c36a58c36a54c37c65aa075272681253797374656d2e53746f726167652e5075746161681953797374656d2e53746f726167652e476574436f6e74657874616a59c37c681253797374656d2e53746f726167652e476574616a5c527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a59c36a5cc36a54c37c6551075272681253797374656d2e53746f726167652e507574616a51c36a52c36a53c36a54c35379517955727551727552795279547275527275087472616e7366657255c1681553797374656d2e52756e74696d652e4e6f74696679516c756658c56b6a00527ac46a51527ac46a52527ac407417070726f76656a53527ac46a52c36a53c37c65b7fb6a00c37c65b0fb6a51c37c65a9fb6a54527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a54c37c681253797374656d2e53746f726167652e476574616c756659c56b6a00527ac4006a52527ac46a00c3c06a53527ac4616a52c36a53c39f64a0006a00c36a52c3c36a51527ac46a52c351936a52527ac46a51c3c0549e64270022617070726f76654d756c7469206661696c6564202d20696e707574206572726f7221f0616a51c300c36a51c351c36a51c352c36a51c353c35379517955727551727552795279547275527275653800009c6484ff24617070726f76654d756c7469206661696c6564202d20617070726f7665206572726f7221f0625bff616161516c75660112c56b6a00527ac46a51527ac46a52527ac46a53527ac407417070726f76656a54527ac46a00c365b905756a00c365e305756a51c365dc05756a52c36509f365e905756a00c36a52c37c658b036a55527ac46a55c36a53c3a265cf05756a53c300a065c605756a52c36a54c37c6535fa6a00c37c652efa6a51c37c6527fa6a56527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a56c36a53c35272681253797374656d2e53746f726167652e507574616a00c36a51c36a52c36a53c3537951795572755172755279527954727552727508617070726f76616c55c1681553797374656d2e52756e74696d652e4e6f74696679516c756659c56b6a00527ac4006a52527ac46a00c3c06a53527ac4616a52c36a53c39f64a3006a00c36a52c3c36a51527ac46a52c351936a52527ac46a51c3c0549e642800237472616e736665724d756c7469206661696c6564202d20696e707574206572726f7221f0616a51c300c36a51c351c36a51c352c36a51c353c35379517955727551727552795279547275527275653a00009c6483ff267472616e736665724d756c7469206661696c6564202d207472616e73666572206572726f7221f06258ff616161516c75660118c56b6a00527ac46a51527ac46a52527ac46a53527ac40742616c616e63656a54527ac46a00c365ed03756a52c3654bf1652b04756a00c3650d04756a51c3650604756a52c36a54c37c658cf86a55527ac46a55c36a00c37c657df86a56527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a56c37c681253797374656d2e53746f726167652e476574616a57527ac46a53c36a57c3a0630b006a53c300a164080061006c7566616a53c36a57c39c643e00681953797374656d2e53746f726167652e476574436f6e74657874616a56c37c681553797374656d2e53746f726167652e44656c6574656162440061681953797374656d2e53746f726167652e476574436f6e74657874616a56c36a57c36a53c37c65a9025272681253797374656d2e53746f726167652e50757461616a55c36a51c37c6595f76a58527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a58c37c681253797374656d2e53746f726167652e476574616a59527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a58c36a59c36a53c37c6541025272681253797374656d2e53746f726167652e507574616a00c36a51c36a52c36a53c35379517955727551727552795279547275527275087472616e7366657255c1681553797374656d2e52756e74696d652e4e6f74696679516c756656c56b6a00527ac46a51527ac40742616c616e63656a52527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a51c36a52c37c6590f66a00c37c6589f67c681253797374656d2e53746f726167652e476574616c756655c56b6a00527ac40b546f74616c537570706c796a51527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a00c36a51c37c6531f67c681253797374656d2e53746f726167652e476574616c756655c56b6a00527ac40653796d626f6c6a51527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a00c36a51c37c65def57c681253797374656d2e53746f726167652e476574616c756655c56b6a00527ac4044e616d656a51527ac4681953797374656d2e53746f726167652e476574436f6e74657874616a00c36a51c37c658df57c681253797374656d2e53746f726167652e476574616c756657c56b6a00527ac46a51527ac46a51c300a065e500756a00c36a51c3966a52527ac46a52c36c756659c56b6a00527ac46a51527ac46a00c3009c640700006c7566616a00c36a51c3956a52527ac46a52c36a00c3966a51c39c659e00756a52c36c756656c56b6a00527ac46a51527ac46a00c36a51c3a2658000756a00c36a51c3946c756657c56b6a00527ac46a51527ac46a00c36a51c3936a52527ac46a52c36a00c3a2655200756a52c36c756655c56b6a00527ac46a00c3681b53797374656d2e52756e74696d652e436865636b5769746e65737361651f0075516c756655c56b6a00527ac46a00c3c001149c65080075516c756656c56b6a00527ac46a00c36307006509007561516c756653c56b09f4f4f3f3f2f2f1f100f0006c75665ec56b6a00527ac46a51527ac46a51c36a00c3946a52527ac46a52c3c56a53527ac4006a54527ac46a00c36a55527ac461616a00c36a51c39f6433006a54c36a55c3936a56527ac46a56c36a53c36a54c37bc46a54c351936a54527ac46a55c36a54c3936a00527ac462c8ff6161616a53c36c7566";


    public static void main(String[] args) {
        try {
            OntSdk sdk = getOntSdk();

            String privateKey0 = "e01e50e3956e8190f41c4291bedf2268c573892ad4b8a8f199b10d5f0a815771";// AVMUDu6MWF5hnfpPaDrXfTG356v1pZLoTh
            String privateKey = "274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b";
            String privatekey1 = "67ae8a3731709d8c820c03b200b9552ec61e6634cbcaf8a6a1f9d8f9f0f608";
            String privatekey2 = "961df65f40dbfe3ad1b63a8e8456b613b718cfd4c65102be8c591b173c148ffd";

            Account account0 = new Account(Helper.hexToBytes(privateKey0),SignatureScheme.SHA256WITHECDSA);
            Account account1 = new Account(Helper.hexToBytes(privatekey1),SignatureScheme.SHA256WITHECDSA);
            Account account2 = new Account(Helper.hexToBytes(privatekey2),SignatureScheme.SHA256WITHECDSA);

            Account account = new Account(Helper.hexToBytes(privateKey),SignatureScheme.SHA256WITHECDSA);

            if(false){
                // 部署OEP8数字资产合约
                System.out.println("ContractAddress:" + Address.AddressFromVmCode(contractCode).toHexString());

                sdk.vm().setCodeAddress(Address.AddressFromVmCode(contractCode).toHexString());
                Transaction tx = sdk.vm().makeDeployCodeTransaction(contractCode, true, "OEP8",
                        "v1.0", "AUr5QUfeBADq6BMY6Tp5yuMsUNGpsD7nLZ", "king@onchain.com", "OEP8 Description", account.getAddressU160().toBase58(),30000000L,0);
                sdk.signTx(tx, new Account[][]{{account}});
                String txHex = Helper.toHexString(tx.toArray());

                Object result = sdk.getConnect().syncSendRawTransaction(txHex);
                System.out.println(txHex);
                System.out.println(result);
                System.exit(0);
            }

            if (false){
                String txhash = sdk.neovm().oep8().sendInit(account, account,200000, 0);
                System.out.println(txhash);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
                return;
            }


            byte[] tokenId1 = Helper.hexToBytes("01");
            byte[] tokenId2 = Helper.hexToBytes("02");
            byte[] tokenId3 = Helper.hexToBytes("03");
            byte[] tokenId4 = Helper.hexToBytes("04");
            byte[] tokenId5 = Helper.hexToBytes("05");
            byte[] tokenId6 = Helper.hexToBytes("06");
            byte[] tokenId7 = Helper.hexToBytes("07");
            byte[] tokenId8 = Helper.hexToBytes("08");
            if (true){
                String name = sdk.neovm().oep8().queryName(tokenId1);
                System.out.println("name: " + name);
                System.out.println(sdk.neovm().oep8().queryName(tokenId2));
                System.out.println(sdk.neovm().oep8().queryName(tokenId3));
                System.out.println(sdk.neovm().oep8().queryName(tokenId4));
                System.out.println(sdk.neovm().oep8().queryName(tokenId5));

                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId1));
                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId2));
                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId3));
                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId4));
                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId5));
                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId6));
                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId7));
                System.out.println("totalsupply: " + sdk.neovm().oep8().queryTotalSupply(tokenId8));
                System.out.println("symbol: " + sdk.neovm().oep8().querySymbol(tokenId1));
                System.out.println("symbol: " + sdk.neovm().oep8().querySymbol(tokenId2));
                System.out.println("symbol: " + sdk.neovm().oep8().querySymbol(tokenId3));
                System.out.println("symbol: " + sdk.neovm().oep8().querySymbol(tokenId4));
                System.out.println("symbol: " + sdk.neovm().oep8().querySymbol(tokenId5));
                return;
            }
            if(false){
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId1));
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId2));
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId3));
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId4));
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId5));
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId6));
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId7));
                System.out.println("balance: " + sdk.neovm().oep8().queryBalanceOf(account0.getAddressU160().toBase58(), tokenId8));

                return;
            }
            if(false){
                System.out.println(sdk.neovm().oep8().balancesOf(account1.getAddressU160().toBase58()));
                System.out.println(sdk.neovm().oep8().totalBalanceOf(account1.getAddressU160().toBase58()));
                return;
            }
            if(false){
                System.out.println(sdk.neovm().oep8().queryTotalSupply(tokenId1));
                return;
            }
            if(false){
                System.out.println(sdk.neovm().oep8().queryTotalSupply(tokenId1));
                String txhash = sdk.neovm().oep8().mint(account1,tokenId1,1000, account, 20000, 500);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
                System.out.println(sdk.neovm().oep8().queryTotalSupply(tokenId1));
                return;
            }
            if(false){
                String txhash = sdk.neovm().oep8().sendCompound(account1,2, account, 71442, 500);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(txhash));
                System.out.println(sdk.neovm().oep8().queryTotalSupply(tokenId1));
                return;
            }
            if(false){
                System.out.println(sdk.neovm().oep8().balancesOf(account.getAddressU160().toBase58()));
                System.out.println(sdk.neovm().oep8().totalBalanceOf(account.getAddressU160().toBase58()));
                return;
            }
            if (true){
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account0.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account2.getAddressU160().toBase58()));
                String txhash = sdk.neovm().oep8().sendTransfer(account0,"AL42aUzAGJ18LdjjH6ppt29LLn8CvTcViq",tokenId8,1,account0,20000,500);
                System.out.println("txhash: " + txhash);
                Thread.sleep(6000);
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account0.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account2.getAddressU160().toBase58()));
                return;
            }

            if (false){
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account2.getAddressU160().toBase58(), tokenId1));
                return;
            }
            if (false){
                String res = sdk.neovm().oep8().sendApprove(account,account2.getAddressU160().toBase58(), tokenId1, 10000, account, 20000, 500);
                System.out.println("res: " + res);
                Thread.sleep(3000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(res));
                return;
            }
            if (false){
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account2.getAddressU160().toBase58()));
                String res = sdk.neovm().oep8().sendTransferFrom(account2,account.getAddressU160().toBase58(),account2.getAddressU160().toBase58(), tokenId1, 1000, account, 20000, 500);
                System.out.println("res: " + res);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(res));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account2.getAddressU160().toBase58()));
                return;
            }
            if (false){
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account1.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account2.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account.getAddressU160().toBase58()));
                Account[] accounts = new Account[]{account1, account2};
                Oep8State state = new Oep8State(account.getAddressU160().toArray(), account1.getAddressU160().toArray(), tokenId1, 1);
                Oep8State state2 = new Oep8State(account.getAddressU160().toArray(), account2.getAddressU160().toArray(), tokenId1, 1);
                String res = sdk.neovm().oep8().sendTransferMulti(accounts,new Oep8State[]{state,state2},  account, 20000, 500);
                System.out.println("res: " + res);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(res));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account1.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account2.getAddressU160().toBase58()));
                System.out.println("balance: " + sdk.neovm().oep8().balancesOf(account.getAddressU160().toBase58()));
                return;
            }
            if (false){
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account1.getAddressU160().toBase58(), tokenId1));
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account2.getAddressU160().toBase58(), tokenId1));
                Account[] accounts = new Account[]{account1, account2};
                Oep8State state = new Oep8State(account.getAddressU160().toArray(), account1.getAddressU160().toArray(), tokenId1, 1);
                Oep8State state2 = new Oep8State(account.getAddressU160().toArray(), account2.getAddressU160().toArray(), tokenId1, 1);
                String res = sdk.neovm().oep8().sendApproveMulti(accounts,new Oep8State[]{state, state2},  account, 20000, 500);
                System.out.println("res: " + res);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(res));
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account1.getAddressU160().toBase58(), tokenId1));
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account2.getAddressU160().toBase58(), tokenId1));
                return;
            }
            if (false){
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account1.getAddressU160().toBase58(), tokenId1));
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account2.getAddressU160().toBase58(), tokenId1));
                Account[] accounts = new Account[]{account1, account2};
                TransferFrom state = new TransferFrom(account.getAddressU160().toArray(),account1.getAddressU160().toArray(), account.getAddressU160().toArray(), tokenId1, 1);
                TransferFrom state2 = new TransferFrom(account.getAddressU160().toArray(),account2.getAddressU160().toArray(), account.getAddressU160().toArray(), tokenId1, 1);
                String res = sdk.neovm().oep8().sendTransferFromMulti(accounts,new TransferFrom[]{state, state2}, account, 27740, 500);
                System.out.println("res: " + res);
                Thread.sleep(6000);
                System.out.println(sdk.getConnect().getSmartCodeEvent(res));
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account1.getAddressU160().toBase58(), tokenId1));
                System.out.println("res: " + sdk.neovm().oep8().queryAllowance(account.getAddressU160().toBase58(),account2.getAddressU160().toBase58(), tokenId1));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OntSdk getOntSdk() throws Exception {
//        String ip = "http://139.219.108.204";
//        String ip = "http://127.0.0.1";
//        String ip = "http://polaris1.ont.io";
        String ip = "http://dappnode1.ont.io";
//        ip= "http://139.219.138.201";
//        String ip = "http://101.132.193.149";
//        String ip = "http://polaris1.ont.io";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());
//        wm.neovm().oep8().setContractAddress("db80b875208f1f87b53c80a06a3288a1b2a0ea76");
//        wm.neovm().oep8().setContractAddress("b2ae73193b07043e75de65edd4ad74b0fa6148b3");
        wm.neovm().oep8().setContractAddress("edf64937ca304ea8180fa92e2de36dc0a33cc712");
        wm.openWalletFile("oep8.dat");
        return wm;
    }
}
