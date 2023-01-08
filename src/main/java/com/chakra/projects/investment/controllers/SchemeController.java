package com.chakra.projects.investment.controllers;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.Domain.MutualFund.TransactionType;
import com.chakra.projects.investment.service.funds.FundManagerSvc;
import org.jdbi.v3.core.Jdbi;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/Schemes", produces = "application/json")
public class SchemeController {

    public FundManagerSvc fundManagerSvc;
    private  Jdbi jdbi;


    public SchemeController(Jdbi jdbi, FundManagerSvc svc) {
        this.jdbi = jdbi;
        this.fundManagerSvc  =svc;
    }



    @GetMapping(path="/{isin}/Transactions")
    public ResponseEntity<List<FundTransaction>> getTransactionsForScheme(@PathVariable("isin") String isin) {
        List<FundTransaction> transactions = fundManagerSvc.getTransactionsForScheme(jdbi, isin);
        if (transactions == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @DeleteMapping(path="/{isin}")
    public ResponseEntity<String>  closeFund(@PathVariable("isin") String isin, @RequestParam("closeDate") String dateStr){
        if (dateStr == null) {
            return new ResponseEntity<>("Close date is required", HttpStatus.BAD_REQUEST);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date closeDate = formatter.parse(dateStr);
            fundManagerSvc.closeScheme(jdbi, isin, closeDate);
            return new ResponseEntity<>("Scheme closed successfully", HttpStatus.OK);
        } catch (ParseException e) {
            System.out.println(e);
            return new ResponseEntity<>("Date should be in the format yyyy-mm-dd", HttpStatus.BAD_REQUEST);
        }



    }
    @GetMapping(path="/")
    public Iterable<Scheme> getAllSchemes() {
        return fundManagerSvc.getAllSchemes(jdbi);
    }


}
