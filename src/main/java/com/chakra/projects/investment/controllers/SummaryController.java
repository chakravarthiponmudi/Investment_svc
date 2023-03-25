package com.chakra.projects.investment.controllers;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.response.objects.MonthlyFolioSummary;
import com.chakra.projects.investment.service.funds.FundManagerSvc;
import com.chakra.projects.investment.service.funds.SchemeService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping(path = "/summary", produces = "application/json")
public class SummaryController {

    private final FundManagerSvc fundManagerSvc;

    private final SchemeService schemeService;

    private Jdbi jdbi;
    public SummaryController(Jdbi jdbi, FundManagerSvc fundManagerSvc, SchemeService schemeService) {
        this.jdbi = jdbi;
        this.fundManagerSvc = fundManagerSvc;
        this.schemeService = schemeService;
    }

    @CrossOrigin
    @GetMapping(path = "/")
    public Map<String, Double> getSummary() {
        Iterable<FundTransaction> transactions = fundManagerSvc.getAllTransactions(jdbi);
        HashMap<String, Double> summary = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        transactions.forEach(transaction -> {
            String year = formatter.format(transaction.getDate());
            if (summary.containsKey(year)) {
                summary.put(year, summary.get(year) + transaction.getAmount());
            } else {
                summary.put(year, transaction.getAmount());
            }
        });

        return summary;
    }

    @CrossOrigin
    @GetMapping(path = "/{year}")
    public Map<String, Double> getMonthlySummary(@PathVariable(name="year") String year) {
        Iterable<FundTransaction> transactions = fundManagerSvc.getAllTransactions(jdbi);
        HashMap<String, Double> summary = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthExtractor = new SimpleDateFormat("MMM");

        StreamSupport.stream(transactions.spliterator(), false)
            .filter(transaction -> {
                String transactionYear = formatter.format(transaction.getDate());
                return transactionYear.equals(year);
            }).forEach(transaction -> {
                String month = monthExtractor.format(transaction.getDate());
                if (summary.containsKey(month)) {
                    summary.put(month, summary.get(month) + transaction.getAmount());
                } else {
                    summary.put(month, transaction.getAmount());
                }
            });

        return summary;
    }

    @CrossOrigin
    @GetMapping(path = "/{year}/{month}")
    public Map<String, MonthlyFolioSummary> getMonthlySummaryByFolio(@PathVariable(name="year") String year,
            @PathVariable(name="month") String month
    ) {
        Iterable<FundTransaction> transactions = fundManagerSvc.getAllTransactions(jdbi);
        HashMap<String, MonthlyFolioSummary> summary = new HashMap<>();
        HashMap<String, Folio> isinToFolio = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthExtractor = new SimpleDateFormat("MMM");

        StreamSupport.stream(transactions.spliterator(), false)
                .filter(transaction -> {
                    String transactionYear = formatter.format(transaction.getDate());
                    String transactionMonth = monthExtractor.format(transaction.getDate());
                    return transactionYear.equals(year) && transactionMonth.equals(month);
                }).forEach(transaction -> {
                    String isin = transaction.getIsin();
                    Folio folio;
                    if (isinToFolio.containsKey(isin)) {
                        folio = isinToFolio.get(isin);
                    } else {
                        folio = schemeService.getFolioForScheme(jdbi, isin);
                        isinToFolio.put(isin, folio);
                    }

                    String folioName = folio.getAmc();
                    if (summary.containsKey(folioName)) {
                        MonthlyFolioSummary monthlyFolioSummary = summary.get(folioName);
                        monthlyFolioSummary.addInvestment(transaction.getAmount());
                        summary.put(folioName, monthlyFolioSummary);
                    } else {
                        MonthlyFolioSummary monthlyFolioSummary = new MonthlyFolioSummary();
                        monthlyFolioSummary.setFolio_id(folio.getId());
                        monthlyFolioSummary.setAmc(folio.getAmc());
                        monthlyFolioSummary.setMonth(month);
                        monthlyFolioSummary.setYear(year);
                        monthlyFolioSummary.setTotalInvestment(transaction.getAmount());
                        summary.put(folioName, monthlyFolioSummary);
                    }
                });

        return summary;
    }
}
