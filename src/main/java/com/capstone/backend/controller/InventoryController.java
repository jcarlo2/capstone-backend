package com.capstone.backend.controller;

import com.capstone.backend.entity.*;
import com.capstone.backend.facade.InventoryFacade;
import com.capstone.backend.pojo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryFacade facade;

    public InventoryController(InventoryFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/save-report-null")
    public Boolean saveNullReport(@RequestBody NullReport report) {
        return facade.saveNullReport(report);
    }

//    @PostMapping("/save-return-report-item")
//    public Boolean saveReportItem(@RequestBody List<NullReportItem> itemList) {
//        return facade.saveReturnReportItem(itemList);
//    }

    @PostMapping("/save-null-item")
    public void saveNullItem(@RequestBody List<NullReportItem> itemList) {
        facade.saveNullItem(itemList);
    }

    @GetMapping("/generate-id-null")
    public String generateNullId() {
        return facade.generateNullId();
    }

    @GetMapping("/generate-id-null-return")
    public String generateNullIdForReturn(@RequestParam String link) {
        return facade.generateNullIdForReturn(link);
    }

    @GetMapping("/generate-id-delivery")
    public String generateDeliveryId() {
        return facade.generateDeliveryId();
    }

    @GetMapping("/get-all-archived-null-report")
    public List<NullReportHistory> getAllArchivedNullReport() {
        return facade.findAllArchivedNullReport();
    }

    @GetMapping("/get-all-archived-delivery-report")
    public List<DeliveryReportHistory> getAllArchivedDeliveryReport() {
        return facade.findAllArchivedDeliveryReport();
    }

    @GetMapping("/get-all-archived-delivery-report-date")
    public List<DeliveryReportHistory> getAllArchivedDeliveryReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.findAllArchivedDeliveryReportByDate(start,end);
    }

    @GetMapping("/get-all-archived-null-report-date")
    public List<NullReportHistory> getAllArchivedNullReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.findAllArchivedNullReportByDate(start,end);
    }

    @GetMapping("/get-all-archived-delivery-report-end")
    public List<DeliveryReportHistory> getAllArchivedDeliveryReportByEnd(@RequestParam String end) {
        return facade.findAllArchivedDeliveryReportByEnd(end);
    }

    @GetMapping("/get-all-archived-null-report-end")
    public List<NullReportHistory> getAllArchivedNullReportByEnd(@RequestParam String end) {
        return facade.findAllArchivedNullReportByEnd(end);
    }

    @GetMapping("/get-all-archived-delivery-report-start")
    public List<DeliveryReportHistory> getAllArchivedDeliveryReportByStart(@RequestParam String start) {
        return facade.findAllArchivedDeliveryReportByStart(start);
    }

    @GetMapping("/get-all-archived-null-report-start")
    public List<NullReportHistory> getAllArchivedNullReportByStart(@RequestParam String start) {
        return facade.findAllArchivedNullReportByStart(start);
    }

    @GetMapping("/get-all-archived-delivery-report-search")
    public List<DeliveryReportHistory> getAllArchivedDeliveryReportBySearch(@RequestParam String search) {
        return facade.findAllArchivedDeliveryReportBySearch(search);
    }

    @GetMapping("/get-all-archived-null-report-search")
    public List<NullReportHistory> getAllArchivedNullReportBySearch(@RequestParam String search) {
        return facade.findAllArchivedNullReportBySearch(search);
    }

    @GetMapping("/is-exist-null-id")
    public Boolean  isNullIdExist(@RequestParam String id) {
        return facade.isNullIdExist(id);
    }

    @GetMapping("/is-exist-delivery-id")
    public Boolean  isDeliveryIdExist(@RequestParam String id) {
        return facade.isDeliveryIdExist(id);
    }

    @PostMapping("/save-delivery-report-item")
    public void saveDeliveryReportItem(@RequestBody List<DeliveryReportItem> itemList) {
        facade.saveDeliveryReportItem(itemList);
    }

    @PostMapping("/save-delivery-report")
    public void saveDeliveryReport(@RequestBody DeliveryReport item) {
        facade.saveDeliveryReport(item);
    }

    @GetMapping("/get-all-null-item")
    public List<NullReportItemHistory> getAllNullItem(@RequestParam String id, @RequestParam String timestamp) {
        return facade.findAllNullItems(id,timestamp);
    }

    @GetMapping("/get-all-delivery-item")
    public List<DeliveryReportItemHistory> getAllDeliveryItem(@RequestParam String id, @RequestParam String timestamp) {
        return facade.findAllDeliveryItems(id,timestamp);
    }

    @GetMapping("/archived-delivery")
    public void archivedDelivery(@RequestParam String id) {
        facade.archiveDelivery(id);
    }

    @GetMapping("/archived-null-report")
    public void archivedNullReport(@RequestParam String id) {
        facade.archiveNullReport(id);
    }

    @GetMapping("/archived-null-link")
    public boolean archivedNullReportWithLink(@RequestParam String link, @RequestParam String id) {
        return facade.archivedNullReportWithLink(link,id);
    }

    @GetMapping("/get-all-null-and-delivery")
    public List<NullReportHistory> findAllNullAndDeliveryReport() {
        return facade.findAllNullAndDeliveryReport();
    }

    @GetMapping("/get-all-null-report")
    public List<NullReport> getAllNullReport() {
        return facade.getAllNullReport();
    }

    @GetMapping("/get-all-delivery-report")
    public List<DeliveryReport> getAllDeliveryReport() {
        return facade.getAllDeliveryReport();
    }

    @GetMapping("/get-all-report-date")
    public List<DeliveryReport> getAllDeliveryReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.getAllDeliveryReportByDate(start,end);
    }

    @GetMapping("/get-all-null-report-date")
    public List<NullReport> getAllNullReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.getAllNullReportByDate(start,end);
    }

    @GetMapping("/get-all-delivery-report-end")
    public List<DeliveryReport> getAllDeliveryReportByEnd(@RequestParam String end) {
        return facade.getAllDeliveryReportByEnd(end);
    }

    @GetMapping("/get-all-null-report-end")
    public List<NullReport> getAllNullReportByEnd(@RequestParam String end) {
        return facade.getAllNullReportByEnd(end);
    }

    @GetMapping("/get-all-delivery-report-start")
    public List<DeliveryReport> getAllDeliveryReportByStart(@RequestParam String start) {
        return facade.getAllDeliveryReportByStart(start);
    }

    @GetMapping("/get-all-null-report-start")
    public List<NullReport> getAllNullReportByStart(@RequestParam String start) {
        return facade.getAllNullReportByStart(start);
    }

    @GetMapping("/get-all-delivery-report-search")
    public List<DeliveryReport> getAllDeliveryReportBySearch(@RequestParam String search) {
        return facade.getAllDeliveryReportBySearch(search);
    }

    @GetMapping("/get-all-null-report-search")
    public List<NullReport> getAllNullReportBySearch(@RequestParam String search) {
        return facade.getAllNullReportBySearch(search);
    }

    @GetMapping("/check-if-id-is-archived")
    public boolean findIfIdExistFromNullAndDeliveryHistory(@RequestParam String id, @RequestParam String timestamp) {
        return facade.findIfIdExistFromNullAndDeliveryHistory(id,timestamp);
    }

    @GetMapping("/get-all-null-and-delivery-search")
    public List<NullReportHistory> findAllNullAndDeliveryReportBySearch(@RequestParam String search) {
        return facade.findAllNullAndDeliveryReportBySearch(search);
    }

    @GetMapping("/get-all-null-and-delivery-date")
    public List<NullReportHistory> findAllNullAndDeliveryReportByDate(@RequestParam String start, @RequestParam String end) {
        return facade.findAllNullAndDeliveryReportByDate(start,end);
    }

    @GetMapping("/get-all-null-and-delivery-end")
    public List<NullReportHistory> findAllNullAndDeliveryReportByEnd(@RequestParam String end) {
        return facade.findAllNullAndDeliveryReportByEnd(end);
    }

    @GetMapping("/get-all-null-and-delivery-start")
    public List<NullReportHistory> findAllNullAndDeliveryReportByStart(@RequestParam String start) {
        return facade.findAllNullAndDeliveryReportByStart(start);
    }

    @GetMapping("/calculate-delivery-all")
    public DeliverySummary calculateAllDeliverySales() {
        return facade.calculateAllDeliverySales();
    }

    @GetMapping("/calculate-delivery-date")
    public DeliverySummary calculateAllDeliverySales(@RequestParam String start, @RequestParam String end) {
        return facade.calculateAllDeliverySalesByDate(start, end);
    }

    @GetMapping("/calculate-delivery-start")
    public DeliverySummary calculateAllDeliverySalesByStart(@RequestParam String start) {
        return facade.calculateAllDeliverySalesByStart(start);
    }

    @GetMapping("/calculate-delivery-end")
    public DeliverySummary calculateAllDeliverySalesByEnd(@RequestParam String end) {
        return facade.calculateAllDeliverySalesByEnd(end);
    }

    @GetMapping("/calculate-void-all")
    public VoidSummary calculateAllVoidSales() {
        return facade.calculateAllVoidSales();
    }

    @GetMapping("/calculate-void-start")
    public VoidSummary calculateAllVoidSalesByStart(@RequestParam String start) {
        return facade.calculateAllVoidSalesByStart(start);
    }

    @GetMapping("/calculate-void-end")
    public VoidSummary calculateAllVoidSalesByEnd(@RequestParam String end) {
        return facade.calculateAllVoidSalesByEnd(end);
    }

    @GetMapping("/calculate-void-date")
    public VoidSummary calculateAllVoidSalesByDate(@RequestParam String start, @RequestParam String end) {
        return facade.calculateAllVoidSalesByDate(start,end);
    }

    @GetMapping("/calculate-product-void-all")
    public List<List<VoidProductSummary>> calculateProductVoidAll() {
        return facade.calculateProductVoidAll();
    }

    @GetMapping("/calculate-product-void-date")
    public List<List<VoidProductSummary>> calculateProductVoidByDate(@RequestParam String start, @RequestParam String end) {
        return facade.calculateProductVoidByDate(start,end);
    }

    @GetMapping("/calculate-product-void-start")
    public List<List<VoidProductSummary>> calculateProductVoidByStart(@RequestParam String start) {
        return facade.calculateProductVoidByStart(start);
    }

    @GetMapping("/calculate-product-void-end")
    public List<List<VoidProductSummary>> calculateProductVoidByEnd(@RequestParam String end) {
        return facade.calculateProductVoidByEnd(end);
    }

    @GetMapping("/calculate-product-delivery-all")
    public List<List<DeliveryProductSummary>> calculateProductDeliveryAll() {
        return facade.calculateProductDeliveryAll();
    }

    @GetMapping("/calculate-product-delivery-date")
    public List<List<DeliveryProductSummary>> calculateProductDeliveryByDate(@RequestParam String start, @RequestParam String end) {
        return facade.calculateProductDeliveryByDate(start,end);
    }

    @GetMapping("/calculate-product-delivery-start")
    public List<List<DeliveryProductSummary>> calculateProductDeliveryStart(@RequestParam String start) {
        return facade.calculateProductDeliveryByStart(start);
    }

    @GetMapping("/calculate-product-delivery-end")
    public List<List<DeliveryProductSummary>> calculateProductDeliveryEnd(@RequestParam String end) {
        return facade.calculateProductDeliveryByEnd(end);
    }
}
