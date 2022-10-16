package com.capstone.backend.controller;

import com.capstone.backend.entity.DeliveryDetail;
import com.capstone.backend.entity.DeliveryItemDetail;
import com.capstone.backend.entity.NullReport;
import com.capstone.backend.entity.NullReportItem;
import com.capstone.backend.facade.InventoryFacade;
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

    @PostMapping("/save-return-report-item")
    public Boolean saveReportItem(@RequestBody List<NullReportItem> itemList) {
        return facade.saveReturnReportItem(itemList);
    }

    @PostMapping("/save-null-item")
    public void saveNullItem(@RequestBody List<NullReportItem> itemList) {
        facade.saveNullItem(itemList);
    }

    @GetMapping("/generate-id-null")
    public String generateNullId() {
        return facade.generateNullId();
    }

    @GetMapping("/generate-id-delivery")
    public String generateDeliveryId() {
        return facade.generateDeliveryId();
    }

    @GetMapping("/get-all-null-report")
    public List<NullReport> getAllNullReport(String isValid) {
        return facade.getAllNullReport(isValid);
    }

    @GetMapping("/get-all-delivery-report")
    public List<DeliveryDetail> getAllDeliveryReport(String isValid) {
        return facade.getAllDeliveryReport(isValid);
    }

    @GetMapping("/get-all-delivery-report-date")
    public List<DeliveryDetail> getAllDeliveryReportByDate(String isValid,String start, String end) {
        return facade.getAllDeliveryReportByDate(isValid,start,end);
    }

    @GetMapping("/get-all-null-report-date")
    public List<NullReport> getAllNullReportByDate(String isValid,String start, String end) {
        return facade.getAllNullReportByDate(isValid,start,end);
    }

    @GetMapping("/get-all-delivery-report-end")
    public List<DeliveryDetail> getAllDeliveryReportByEnd(String isValid, String end) {
        return facade.getAllDeliveryReportByEnd(isValid,end);
    }

    @GetMapping("/get-all-null-report-end")
    public List<NullReport> getAllNullReportByEnd(String isValid, String end) {
        return facade.getAllNullReportByEnd(isValid,end);
    }

    @GetMapping("/get-all-delivery-report-start")
    public List<DeliveryDetail> getAllDeliveryReportByStart(String isValid, String start) {
        return facade.getAllDeliveryReportByStart(isValid,start);
    }

    @GetMapping("/get-all-null-report-start")
    public List<NullReport> getAllNullReportByStart(String isValid, String start) {
        return facade.getAllNullReportByStart(isValid,start);
    }

    @GetMapping("/get-all-delivery-report-search")
    public List<DeliveryDetail> getAllDeliveryReportBySearch(String isValid, String search) {
        return facade.getAllDeliveryReportBySearch(isValid,search);
    }

    @GetMapping("/get-all-null-report-search")
    public List<NullReport> getAllNullReportBySearch(String isValid, String search) {
        return facade.getAllNullReportBySearch(isValid,search);
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
    public void saveDeliveryReportItem(@RequestBody List<DeliveryItemDetail> itemList) {
        facade.saveDeliveryReportItem(itemList);
    }

    @PostMapping("/save-delivery-report")
    public void saveDeliveryReport(@RequestBody DeliveryDetail item) {
        facade.saveDeliveryReport(item);
    }

    @GetMapping("/get-all-null-item")
    public List<NullReportItem> getAllNullItem(String id) {
        return facade.getAllNullItem(id);
    }

    @GetMapping("/get-all-delivery-item")
    public List<DeliveryItemDetail> getAllDeliveryItem(String id) {
        return facade.getAllDeliveryItem(id);
    }
}
