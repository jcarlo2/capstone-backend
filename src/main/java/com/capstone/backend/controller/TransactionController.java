package com.capstone.backend.controller;

import com.capstone.backend.entity.TransactionReport;
import com.capstone.backend.entity.TransactionReportHistory;
import com.capstone.backend.entity.TransactionReportItem;
import com.capstone.backend.entity.TransactionReportItemHistory;
import com.capstone.backend.facade.TransactionFacade;
import com.capstone.backend.pojo.TransactionProductSummary;
import com.capstone.backend.pojo.SalesSummary;
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
    public boolean isExistingReportId(@RequestParam String id) {
        return facade.isExistingReportId(id);
    }

    @GetMapping("/get-all-valid-report")
    public List<TransactionReport> getAllValidReport() {
        return facade.findAllValidReport();
    }

    @GetMapping("/get-all-report")
    public List<TransactionReportHistory> getAllReport() {
        return facade.getAllReport();
    }

    @GetMapping("/get-all-archived-report")
    public List<TransactionReportHistory> getAllArchivedReport() {
        return facade.getAllArchivedReport();
    }

    @GetMapping("/search-valid-transaction")
    public List<TransactionReport> getAllValidReportBySearch(@RequestParam String search) {
        return facade.findAllValidReportBySearch(search);
    }

    @GetMapping("/search-archived-transaction")
    public List<TransactionReportHistory> getArchivedReportBySearch(@RequestParam String search) {
        return facade.findArchivedReportBySearch(search);
    }

    @GetMapping("/search-all-transaction")
    public List<TransactionReportHistory> getAllReportBySearch(@RequestParam String search) {
        return facade.getAllReportBySearch(search);
    }

    @GetMapping("/search-start")
    public List<TransactionReport> getAllValidReportByStart(@RequestParam String start) {
        return facade.findAllValidReportByStart(start);
    }

    @GetMapping("/search-archived-start")
    public List<TransactionReportHistory> getAllArchivedReportByStart(@RequestParam String start) {
        return facade.findAllArchivedReportByStart(start);
    }

    @GetMapping("/search-all-start")
    public List<TransactionReportHistory> getAllReportByStart(@RequestParam String start) {
        return facade.findAllReportByStart(start);
    }

    @GetMapping("/search-end")
    public List<TransactionReport> getAllValidReportByEnd(@RequestParam String end) {
        return facade.findAllValidReportByEnd(end);
    }

    @GetMapping("/search-archived-end")
    public List<TransactionReportHistory> getAllArchivedReportByEnd(@RequestParam String end) {
        return facade.findAllArchivedReportByEnd(end);
    }

    @GetMapping("/search-all-end")
    public List<TransactionReportHistory> getAllReportByEnd(@RequestParam String end) {
        return facade.findAllReportByEnd(end);
    }

    @GetMapping("/search-date")
    public List<TransactionReport> getAllValidReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.findAllValidReportByDate(start,end);
    }

    @GetMapping("/search-archived-date")
    public List<TransactionReportHistory> getAllArchivedReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.findAllArchivedReportByDate(start,end);
    }

    @GetMapping("/search-all-date")
    public List<TransactionReportHistory> getAllReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.findAllReportByDate(start,end);
    }

    @GetMapping("/new-report-id")
    public String getNewReportId(@RequestParam String id) {
        return facade.findNewReportId(id);
    }

    @GetMapping("/invalidate-report")
    public void invalidateReport(@RequestParam String id) {
        facade.invalidateReport(id);
    }

    @GetMapping("/find-all-item")
    public List<TransactionReportItemHistory> findAllItemById(@RequestParam String id, @RequestParam String timestamp) {
        return facade.findAllItemById(id,timestamp);
    }

    @PostMapping("/save-report-item")
    public Boolean saveReportItem(@RequestBody List<TransactionReportItem> itemList) {
        return facade.saveReportItem(itemList);
    }

    @PostMapping("/save-report")
    public Boolean saveReport(@RequestBody TransactionReport report) {
        return facade.saveReport(report);
    }

    @PostMapping("/archive")
    public boolean archive(@RequestParam String id) {
        return facade.archive(id);
    }

    @PostMapping("/archive-all")
    public void archiveAll(@RequestParam String id) {
        facade.archiveAll(id);
    }

    @GetMapping("/calculate-sales-all")
    public SalesSummary calculateAllSales() {
        return facade.calculateAllSales();
    }

    @GetMapping("/calculate-sales-start")
    public SalesSummary calculateSalesByStart(@RequestParam String start) {
        return facade.calculateSalesByStart(start);
    }

    @GetMapping("/calculate-sales-end")
    public SalesSummary calculateSalesByEnd(@RequestParam String end) {
        return facade.calculateSalesByEnd(end);
    }

    @GetMapping("/calculate-sales-date")
    public SalesSummary calculateSalesByDate(@RequestParam String start, @RequestParam String end) {
        return facade.calculateSalesByDate(start,end);
    }

    @GetMapping("/calculate-product-sales-all")
    public List<List<TransactionProductSummary>> calculateAllProductSales() {
        return facade.calculateAllProductSales();
    }

    @GetMapping("/calculate-product-sales-date")
    public List<List<TransactionProductSummary>> calculateAllProductSalesByDate(@RequestParam String start, @RequestParam String end) {
        return facade.calculateAllProductSalesByDate(start,end);
    }

    @GetMapping("/calculate-product-sales-start")
    public List<List<TransactionProductSummary>> calculateAllProductSalesByStart(@RequestParam String start) {
        return facade.calculateAllProductSalesByStart(start);
    }

    @GetMapping("/calculate-product-sales-end")
    public List<List<TransactionProductSummary>> calculateAllProductSalesByEnd(@RequestParam String end) {
        return facade.calculateAllProductSalesByEnd(end);
    }

    @GetMapping("/get-earliest-transaction")
    public String getEarliestTransaction() {
        return facade.getEarliestTransaction();
    }
}
