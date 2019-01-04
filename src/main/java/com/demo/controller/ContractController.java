package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author king
 * @version 1.0
 * @date 2018.11.21
 */
@AutoConfigurationPackage
@RestController
@RequestMapping(value="Contract", produces="application/json")
public class ContractController {
    private static Logger logger = LoggerFactory.getLogger(ContractController.class);

    /**
     * 发起转账交易
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public void transfer( String fromAcc, String toAcc, String amount){
        logger.info("oep4Transfer");
        System.out.print("OK");
    }

    /**
     * 发起转账交易
     */
    @RequestMapping(value = "/transferDemo", method = RequestMethod.POST)
    public void transferDemo(){
        logger.info("oep4TransferDemo");

    }



}
