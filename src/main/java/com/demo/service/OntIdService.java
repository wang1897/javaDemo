package com.demo.service;

import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Helper;
import com.github.ontio.crypto.SignatureScheme;
import com.github.ontio.sdk.wallet.Identity;

import java.util.List;

public class OntIdService {

    public static void main(String[] args) {
        try {
            OntSdk ontSdk = getOntSdk();
            String password = "111111";

            String privateKey = "274b0b664d9c1e993c1d62a42f78ba84c379e332aa1d050ce9c1840820acee8b";
            String privatekey1 = "67ae8a3731709d8c820c03b200b9552ec61e6634cbcaf8a6a1f9d8f9f0f608";
            String privatekey2 = "961df65f40dbfe3ad1b63a8e8456b613b718cfd4c65102be8c591b173c148ffd";

            Account account1 = new Account(Helper.hexToBytes(privatekey1), SignatureScheme.SHA256WITHECDSA);
            Account account2 = new Account(Helper.hexToBytes(privatekey2), SignatureScheme.SHA256WITHECDSA);
            Account account = new Account(Helper.hexToBytes(privateKey), SignatureScheme.SHA256WITHECDSA);

            Identity identity = null;
            Identity identity2 = null;
            Identity identity3 = null;
            List<Identity> dids = ontSdk.getWalletMgr().getWallet().getIdentities();
            if (ontSdk.getWalletMgr().getWallet().getIdentities().size() < 3) {
                // Identity identity1 = ontSdk.getWalletMgr().importIdentity("",password,"".getBytes(),acct0.getAddressU160().toBase58());
                identity = ontSdk.getWalletMgr().createIdentityFromPriKey(password, privateKey);

                ontSdk.nativevm().ontId().sendRegister(identity, password, account, ontSdk.DEFAULT_GAS_LIMIT, 0);
                //  ontSdk.nativevm().ontId().sendRegister(identity1,password,payerAcct,ontSdk.DEFAULT_GAS_LIMIT,0);
                identity2 = ontSdk.getWalletMgr().createIdentity(password);
                ontSdk.nativevm().ontId().sendRegister(identity2, password, account, ontSdk.DEFAULT_GAS_LIMIT, 0);

                identity3 = ontSdk.getWalletMgr().createIdentity(password);
                ontSdk.nativevm().ontId().sendRegister(identity3, password, account, ontSdk.DEFAULT_GAS_LIMIT, 0);

                ontSdk.getWalletMgr().writeWallet();

                Thread.sleep(6000);
            } else {
                identity = ontSdk.getWalletMgr().getWallet().getIdentity(dids.get(0).ontid);
                identity2 = ontSdk.getWalletMgr().getWallet().getIdentity(dids.get(1).ontid);
                identity3 = ontSdk.getWalletMgr().getWallet().getIdentity(dids.get(2).ontid);
            }

            System.out.println("##########");
            System.out.println("ontid1:" +dids.get(0).ontid+" "+Helper.toHexString(dids.get(0).ontid.getBytes()));
            System.out.println("ontid2:" +dids.get(1).ontid);
            System.out.println("ontid3:" +dids.get(2).ontid);
            System.out.println("##########");

            System.out.println("ddo1:" + ontSdk.nativevm().ontId().sendGetDDO(dids.get(0).ontid));
            System.out.println("ddo2:" + ontSdk.nativevm().ontId().sendGetDDO(dids.get(1).ontid));
            System.out.println("ddo3:" + ontSdk.nativevm().ontId().sendGetDDO(dids.get(2).ontid));
            System.out.println("##########");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static OntSdk getOntSdk() throws Exception {
        String ip = "http://127.0.0.1";
//        String ip = "http://polaris1.ont.io";
//        String ip = "http://dappnode1.ont.io";
        String restUrl = ip + ":" + "20334";
        String rpcUrl = ip + ":" + "20336";
        String wsUrl = ip + ":" + "20335";

        OntSdk wm = OntSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());
        wm.neovm().oep5().setContractAddress("c7c4b82f002728c7065319f62f1aa5c9a4f5cea0");
        wm.openWalletFile("oep5.dat");
        return wm;
    }

}
