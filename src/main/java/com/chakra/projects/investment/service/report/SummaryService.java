package com.chakra.projects.investment.service.report;

import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.service.funds.FundManagerSvc;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SummaryService {

    private final FundManagerSvc fundManagerSvc;

    public SummaryService(FundManagerSvc fundManagerSvc) {
        this.fundManagerSvc = fundManagerSvc;
    }

    public Map<String, Double> getSummaryByFiscalYear(Jdbi jdbi) {
        Iterable<FundTransaction> transactions = fundManagerSvc.getAllTransactions(jdbi);
        HashMap<String, Double> summary = new HashMap<>();
        transactions.forEach(transaction -> {
            Date transactionDate = transaction.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(transactionDate);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            // Determine fiscal year based on transaction date
            String fiscalYear;
            if (month >= Calendar.APRIL) {
                fiscalYear = year + "";
            } else {
                fiscalYear = (year-1) + "";
            }
            if (summary.containsKey(fiscalYear)) {
                summary.put(fiscalYear, summary.get(fiscalYear) + transaction.getAmount());
            } else {
                summary.put(fiscalYear, transaction.getAmount());
            }
        });

        return summary;
    }

    public Map<String, Double> getSummaryByCalendarYear(Jdbi jdbi) {
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
}
