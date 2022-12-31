package com.chakra.projects.investment.controllers;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.service.funds.FundManagerSvc;
import org.jdbi.v3.core.Jdbi;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/folios", produces = "application/json")
public class FundManagerController {

    public FundManagerSvc fundManagerSvc;
    private  Jdbi jdbi;


    public FundManagerController(Jdbi jdbi, FundManagerSvc svc) {
        this.jdbi = jdbi;
        this.fundManagerSvc  =svc;
    }




    @GetMapping(path="/")
    public Iterable<Folio> getAll() {
        return fundManagerSvc.getAllFolio(jdbi);
    }


    @GetMapping(path="/number")
    public ResponseEntity<Folio> getFolio(@RequestParam("folio_no") String folioNo) {
        Folio folio = fundManagerSvc.getFolio(jdbi, folioNo);
        if (folio == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Folio>(folio, HttpStatus.OK);
    }

    @GetMapping(path="/schemes")
    public Iterable<Scheme> getAllSchemes() {
        return fundManagerSvc.getAllSchemes(jdbi);
    }

    @GetMapping(path="/schemes/transactions")
    public Iterable<FundTransaction> getAllTransactions() {
        return fundManagerSvc.getAllTransactions(jdbi);
    }
}
