package com.chakra.projects.investment.controllers;

import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.service.funds.FundManagerSvc;
import com.chakra.projects.investment.service.funds.SchemeService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/schemes", produces = "application/json")
public class SchemeController {

    public FundManagerSvc fundManagerSvc;
    public SchemeService schemeSvc;
    private  final Jdbi jdbi;


    public SchemeController(Jdbi jdbi, FundManagerSvc svc, SchemeService schemeSvc) {
        this.jdbi = jdbi;
        this.fundManagerSvc  =svc;
        this.schemeSvc = schemeSvc;
    }



    @CrossOrigin
    @GetMapping(path="/{isin}/transactions")
    public ResponseEntity<List<FundTransaction>> getTransactionsForScheme(@PathVariable("isin") String isin) {
        List<FundTransaction> transactions = schemeSvc.getTransactionsForScheme(jdbi, isin);
        if (transactions == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping(path="/{isin}/marketvalue")
    public ResponseEntity<Double> getMarketValue(@PathVariable(name="isin") String isin)  {
        return new ResponseEntity<>(schemeSvc.getMarketPrice(jdbi,isin), HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping(path="/")
    public Iterable<Scheme> getAllSchemes() {
        return schemeSvc.getAllSchemes(jdbi);
    }


}
