package com.demo.service;

import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Helper;
import com.github.ontio.crypto.SignatureScheme;

public class DemoService {

    public static void main(String[] args) {
        try {
            OntSdk ontSdk = getOntSdk();

            String privateKey = "274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b";
            String privatekey1 = "67ae8a3731709d8c820c03b200b9552ec61e6634cbcaf8a6a1f9d8f9f0f608";
            String privatekey2 = "961df65f40dbfe3ad1b63a8e8456b613b718cfd4c65102be8c591b173c148ffd";

            Account account1 = new Account(Helper.hexToBytes(privatekey1), SignatureScheme.SHA256WITHECDSA);
            Account account2 = new Account(Helper.hexToBytes(privatekey2), SignatureScheme.SHA256WITHECDSA);

            Account account = new Account(Helper.hexToBytes(privateKey), SignatureScheme.SHA256WITHECDSA);

//            String txhash = ontSdk.nativevm().ont().sendTransfer(account, "AL42aUzAGJ18LdjjH6ppt29LLn8CvTcViq",1000,account,20000,500);
            String txhash1 = ontSdk.nativevm().ong().sendTransfer(account, "AL42aUzAGJ18LdjjH6ppt29LLn8CvTcViq",1000000000000l, account,20000,500);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static OntSdk getOntSdk() throws Exception {
//        String ip = "http://127.0.0.1";
        String ip = "http://polaris1.ont.io";
//        String ip = "http://dappnode1.ont.io";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());
//        wm.neovm().oep5().setContractAddress("f7e4b77f2f8bd611beea671574e7bbba785d19a7");
        wm.neovm().oep5().setContractAddress("c7c4b82f002728c7065319f62f1aa5c9a4f5cea0");
//        wm.neovm().oep5().setContractAddress("4cfbe5d9e6b6d2e58f63b2883b26b540e0119c71");
        wm.openWalletFile("oep8.dat");
        return wm;
    }

}
