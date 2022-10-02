package com.capstone.backend.controller;

import com.capstone.backend.entity.TransactionDetail;
import com.capstone.backend.entity.TransactionItemDetail;
import com.capstone.backend.facade.TransactionFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionFacade facade;

    public TransactionController(TransactionFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/generate-report-id")
    public String generateId() {
        return facade.generateId();
    }

    @GetMapping("/is-exist-report-id")
    public boolean isExistingReportId(@RequestParam(name = "id") String id) {
        return facade.isExistingReportId(id);
    }

    @GetMapping("/get-all-report")
    public List<TransactionDetail> getAllReport() {
        return facade.getAllReport();
    }

    @GetMapping("/search-transaction")
    public List<TransactionDetail> getReportBySearch(@RequestParam(name = "search") String search) {
        return facade.getReportBySearch(search);
    }

    @GetMapping("/search-start")
    public List<TransactionDetail> getAllReportByStart(@RequestParam String start) {
        return facade.getAllReportByStart(start);
    }

    @GetMapping("/search-end")
    public List<TransactionDetail> getAllReportByEnd(@RequestParam String end) {
        return facade.getAllReportByEnd(end);
    }

    @GetMapping("/search-date")
    public List<TransactionDetail> getAllReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.getAllReportByDate(start,end);
    }

    @GetMapping("/new-report-id")
    public String getNewReportId(@RequestParam String id) {
        return facade.getNewReportId(id);
    }

    @GetMapping("/invalidate-report")
    public void invalidateReport(@RequestParam String id) {
        facade.invalidateReport(id);
    }

    @GetMapping("/find-all-item")
    public List<TransactionItemDetail> findAllItemById(@RequestParam String id) {
        return facade.findAllItemById(id);
    }

    @PostMapping("/save-report-item")
    public Boolean saveReportItem(@RequestBody List<TransactionItemDetail> itemList) {
        return facade.saveReportItem(itemList);
    }

    @PostMapping("/save-return-report-item")
    public Boolean saveReturnReportItem(@RequestBody List<TransactionItemDetail> itemList) {
        return facade.saveReturnReportItem(itemList);
    }

    @PostMapping("/save-report")
    public Boolean saveReport(@RequestBody TransactionDetail report) {
        return facade.saveReport(report);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody String id) {
        facade.delete(id);
    }

    @PostMapping("/delete-all")
    public void deleteAll(@RequestBody String id) {
        facade.deleteAll(id);
    }

}
