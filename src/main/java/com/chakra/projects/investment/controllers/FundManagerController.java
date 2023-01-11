package com.chakra.projects.investment.controllers;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.Domain.MutualFund.SchemeType;
import com.chakra.projects.investment.Domain.MutualFund.TransactionType;
import com.chakra.projects.investment.service.funds.FundManagerSvc;
import com.chakra.projects.investment.service.funds.SchemeService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/folios", produces = "application/json")
public class FundManagerController {

    public FundManagerSvc fundManagerSvc;

    @Autowired
    public SchemeService schemeService;

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

    @GetMapping(path="/investment")
    public ResponseEntity<Double> getTotalInvestmentForFolio(@RequestParam("folio_no") String folioNo) {
        Folio folio = fundManagerSvc.getFolio(jdbi, folioNo);
        if (folio == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        double amount = 0.0;
        for(Scheme scheme: folio.getSchemes()) {
            List<FundTransaction> transactions = scheme.getTransactions();
            amount += transactions.stream()
                    .filter(transaction -> transaction.getType() == TransactionType.PURCHASE_SIP ||
                        transaction.getType() == TransactionType.PURCHASE ||
                        transaction.getType() == TransactionType.REDEMPTION ||
                        transaction.getType() == TransactionType.STAMP_DUTY_TAX)
                    .map(FundTransaction::getAmount).reduce(0.0,(accumulator,element ) -> accumulator + element);
        }
        return new ResponseEntity<>(amount, HttpStatus.OK);
    }

    @GetMapping(path="/marketvalue")
    public ResponseEntity<Map<String,Double>> getTotalMarketValueForFolio(@RequestParam("folio_no") String folioNo) {
        Folio folio = fundManagerSvc.getFolio(jdbi, folioNo);
        if (folio == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Map<String,Double> result = new HashMap<>();
        final String EQUITY = SchemeType.EQUITY.name();
        final String DEBT = SchemeType.DEBT.name();
        result.put(EQUITY,Double.valueOf(0));
        result.put(DEBT,Double.valueOf(0));


        for(Scheme scheme: folio.getSchemes()) {
            if (scheme.getType() == SchemeType.EQUITY) {
                result.compute(EQUITY, (key, val)-> {
                    val+=schemeService.getMarketPrice(jdbi, scheme.getIsin());
                    return val;
                });
            } else {
                result.compute(DEBT, (key, val)-> {
                    val+=schemeService.getMarketPrice(jdbi, scheme.getIsin());
                    return val;
                });
            }

        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(path="/{folioId}/schemes")
    public ResponseEntity<List<Scheme>> getSchemesForFolio(@PathVariable("folioId") int folioId,
            @RequestParam(name="withClosedFolios", required = false) boolean withClosedFolios) {
        List<Scheme> schemes = fundManagerSvc.getSchemeForFolio(jdbi, folioId, withClosedFolios);
        if (schemes == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(schemes, HttpStatus.OK);
    }

    @GetMapping(path="/schemes/transactions")
    public Iterable<FundTransaction> getAllTransactions() {
        return fundManagerSvc.getAllTransactions(jdbi);
    }


    class MarketValueContract {
        double equityAmount;
        double debtAmount;
        public MarketValueContract(double equityAmount, double debtAmount) {
            this.equityAmount = equityAmount;
            this.debtAmount = debtAmount;
        }
    }

}
