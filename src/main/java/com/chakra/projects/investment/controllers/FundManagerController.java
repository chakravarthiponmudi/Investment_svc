package com.chakra.projects.investment.controllers;

import com.chakra.projects.investment.Domain.MutualFund.Folio;
import com.chakra.projects.investment.service.funds.FundManagerSvc;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
