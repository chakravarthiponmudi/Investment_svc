package com.chakra.projects.investment.service.funds;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.Domain.MutualFund.FundTransaction;
import com.chakra.projects.investment.Domain.MutualFund.Scheme;
import com.chakra.projects.investment.constants.Constants;
import com.chakra.projects.investment.constants.Constants.CAS;
import com.chakra.projects.investment.db.FolioDao;
import com.chakra.projects.investment.db.SchemeDao;
import com.chakra.projects.investment.db.TransactionDao;
import com.chakra.projects.investment.utils.Context;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FundManagerSvc {


    public void addFolio(Jdbi jdbi, Folio folio) {

        Context context = Context.getContext();
        Date startDate = context.get(CAS.STATEMENT_START_DATE);
        Date endDate = context.get(CAS.STATEMENT_END_DATE);


        jdbi.useHandle(handle -> {
            Folio updateFolio = this.processFolio(handle,folio);
            folio.getSchemes().forEach(scheme -> {
                scheme.setStatementStartDate(startDate);
                scheme.setStatementEndDate(endDate);
                this.processScheme(handle,updateFolio.getId(),scheme);
                scheme.getTransactions().forEach(transaction -> this.processTransaction(handle,scheme.getIsin(),transaction));
            });
        });

    }

    private void processTransaction(Handle h,String isin, FundTransaction transaction) {
        TransactionDao tDao = h.attach(TransactionDao.class);
        Context context = Context.getContext();
        String batchId = context.get(Constants.BATCH_ID);
        Boolean newBatch = context.get(Constants.NEW_BATCH);
        transaction.setBatchId(batchId);
        if (newBatch || !transactionExists(h, transaction)) {
            tDao.save(isin, transaction);
        }
    }

    private boolean transactionExists(Handle h, FundTransaction transaction) {
        TransactionDao tDao = h.attach(TransactionDao.class);

        List<Integer> res = tDao.findDuplicateTransaction(transaction);
        return res.size() > 0;
    }

    private Folio processFolio(Handle h, Folio folio) {
        FolioDao f = h.attach(FolioDao.class);
        Folio existingFolio = f.getByNo(folio.getFolioNo());
        if (existingFolio == null) {
            f.save(folio);
            return f.getByNo(folio.getFolioNo());
        }

        return existingFolio;

    }
    private void processScheme(Handle h, int folioId, Scheme scheme) {
        SchemeDao sDao = h.attach(SchemeDao.class);
        Scheme existingScheme = sDao.findByFolioIdAndIsin(folioId, scheme.getIsin());
        Context context = Context.getContext();
        context.put(Constants.NEW_BATCH, Boolean.FALSE);
        if (existingScheme == null) {
            sDao.save(folioId,scheme);
            return;
        }

        boolean updateOpenNav = false, updateCloseNav = false;

        if (existingScheme.getStatementStartDate().compareTo(scheme.getStatementEndDate()) > 0 ||
                existingScheme.getStatementEndDate().compareTo(scheme.getStatementStartDate()) < 0
        ) {
            context.put(Constants.NEW_BATCH, Boolean.TRUE);
        }

        if (existingScheme.getStatementStartDate().compareTo(scheme.getStatementStartDate()) > 0) {
            updateOpenNav = true;
        }
        if (existingScheme.getStatementEndDate().compareTo(scheme.getStatementEndDate()) < 0
        ) {
            updateCloseNav = true;
        }

        if (updateOpenNav) {
            existingScheme.setOpenNavUnits(scheme.getOpenNavUnits());
            existingScheme.setStatementStartDate(scheme.getStatementStartDate());
        }

        if (updateCloseNav) {
            existingScheme.setCloseNavUnits(scheme.getCloseNavUnits());
            existingScheme.setStatementEndDate(scheme.getStatementEndDate());
            existingScheme.setMarketValue(scheme.getMarketValue());
            existingScheme.setNavValue(scheme.getNavValue());
            existingScheme.setNavDate(scheme.getNavDate());
            existingScheme.setCloseCalculated(scheme.getCloseCalculated());
        }

        if (updateCloseNav || updateOpenNav) {
            sDao.updateScheme(existingScheme);
        }
    }

    public Iterable<Folio> getAllFolio(Jdbi jdbi) {
        try (Handle h = jdbi.open()) {
            FolioDao f = h.attach(FolioDao.class);
            return f.findAll();
        }
    }

    public Folio getFolio(Jdbi jdbi, String folio_no) {
        try (Handle h = jdbi.open()) {
            FolioDao fDao = h.attach(FolioDao.class);
            SchemeDao sDao = h.attach(SchemeDao.class);
            TransactionDao tDao = h.attach(TransactionDao.class);
            Folio folioObj=  fDao.getByNo(folio_no);
            if (folioObj == null) {
                return null;
            }
            List<Scheme> schemes = sDao.findOpenSchemesByFolioId(folioObj.getId());
            schemes = schemes.stream().map(scheme -> {
                List<FundTransaction> transactions = tDao.findBySchemeIsin(scheme.getIsin());
                scheme.setTransactions(transactions);
                return scheme;
            }).collect(Collectors.toList());
            folioObj.setSchemes(schemes);
            return folioObj;
        }
    }






    public Iterable<FundTransaction> getAllTransactions(Jdbi jdbi) {
        try (Handle h = jdbi.open()) {
            TransactionDao dao = h.attach(TransactionDao.class);
            return dao.findAll();
        }

    }

    public void closeScheme(Jdbi jdbi, String isin, Date date) {
        try(Handle h = jdbi.open()) {
            SchemeDao sDao = h.attach(SchemeDao.class);
            sDao.closeFolio(isin, date);
        }
    }


}
