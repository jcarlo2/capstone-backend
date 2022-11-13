package com.capstone.backend.facade;

import com.capstone.backend.entity.*;
import com.capstone.backend.pojo.ProductSummary;
import com.capstone.backend.pojo.SalesSummary;
import com.capstone.backend.service.EntityConverter;
import com.capstone.backend.service.InventoryService;
import com.capstone.backend.service.MerchandiseService;
import com.capstone.backend.service.TransactionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionFacade {

    private final TransactionService service;
    private final MerchandiseService merchandise;
    private final InventoryService inventory;
    private final EntityConverter converter;

    public TransactionFacade(TransactionService service, MerchandiseService merchandise, InventoryService inventory, EntityConverter converter) {
        this.service = service;
        this.merchandise = merchandise;
        this.inventory = inventory;
        this.converter = converter;
    }

    public String generateId() {
        return service.generateId();
    }

    public boolean isExistingReportId(String id) {
        return service.isExistingReportId(id);
    }

    public List<TransactionReport> findAllValidReport() {
        return service.findAllValidReport();
    }

    public List<TransactionReport> findAllValidReportBySearch(String search) {
        return service.findAllValidReportBySearch(search);
    }

    public List<TransactionReport> findAllValidReportByStart(String start) {
        return service.findAllValidReportByStart(start);
    }

    public List<TransactionReport> findAllValidReportByEnd(String end) {
        return service.findAllValidReportByEnd(end);
    }

    public List<TransactionReport> findAllValidReportByDate(String start, String end) {
        return service.findAllValidReportByDate(start,end);
    }

    public String findNewReportId(String id) {
        return service.getNewReportId(id);
    }

    public List<TransactionReportItemHistory> findAllItemById(String id, String timestamp) {
        if(service.existByIdAndTimestamp(id,timestamp)) return service.findAllItemByIdWithTimestamp(id,timestamp);
        return converter.convertTransactionReportItem(service.findAllItemById(id),"");
    }

    public Boolean saveReportItem(@NotNull List<TransactionReportItem> itemList) {
        for(TransactionReportItem item : itemList) {
            if(!merchandise.hasStock(item.getProductId(),item.getSold())) return false;
        }

        itemList.forEach(item -> merchandise.updateProductQuantity(item.getSold() * -1,item.getProductId()));
        return service.saveReportItem(itemList);
    }

    public Boolean saveReport(TransactionReport report) {
        return service.saveReport(report);
    }

    public void invalidateReport(String id) {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        TransactionReportHistory report = converter.convertTransactionReport(service.findReportById(id),timestamp);
        List<TransactionReportItemHistory> itemList = converter.convertTransactionReportItem(service.findAllItemById(id),timestamp);
        service.archiveReport(report,itemList);
        service.invalidateReport(id);
    }

    public Boolean saveReturnReportItem(List<TransactionReportItem> itemList) {
        return service.saveReportItem(itemList);
    }

    public void archiveAll(@NotNull String id) {
        while(service.existById(id)) {
            if(id.endsWith("A0")) break;
            service.archive(id);
            id = service.reverseId(id);
        }
    }

    public boolean archive(@NotNull String id) {
        // check if null works
        boolean flag = reflectDeliveryItemsToStock(id);
        if(flag) {
            makeArchivedReport(id);
            reflectNullItemsToStock(id);
            reflectTransactionItemsToStock(id);
            service.archive(id);
            inventory.invalidateNullReportByLink(id);
            inventory.invalidateDeliveryReportByLink(id);
            id = service.reverseId(id);
            inventory.validateNullReportByLink(id);
            inventory.validateDeliveryReportByLink(id);
        }
        return flag;
    }

    public void makeArchivedReport(String id) {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        TransactionReport report = service.findReportById(id);
        DeliveryReport deliveryReport = inventory.findDeliveryByLink(id);
        NullReport nullReport = inventory.findNullByLink(id);
        if(report != null) {
            TransactionReportHistory newReport = converter.convertTransactionReport(report,timestamp);
            List<TransactionReportItemHistory> itemList = converter.convertTransactionReportItem(service.findAllItemById(id),timestamp);
            service.archiveReport(newReport,itemList);
        }

        if(deliveryReport != null) {
            DeliveryReportHistory newDeliveryReport = converter.convertDeliveryReport(deliveryReport,timestamp);
            List<DeliveryReportItemHistory> deliveryReportItem = converter.convertDeliveryReportItem(inventory.findAllDeliveryItems(deliveryReport.getId()),timestamp,deliveryReport.getId());
            inventory.archiveDeliveryHistory(newDeliveryReport,deliveryReportItem);
        }

        if(nullReport != null) {
            NullReportHistory newNullReport = converter.convertNullReport(nullReport,timestamp);
            List<NullReportItemHistory> nullReportItem = converter.convertNullReportItem(inventory.findAllNullItem(nullReport.getId()),timestamp,nullReport.getId());
            inventory.archiveNullHistory(newNullReport,nullReportItem);
        }
    }

    public void reflectTransactionItemsToStock(String id) {
       service.findAllItemById(id).forEach(item -> merchandise.updateProductQuantity(item.getSold(),item.getProductId()));
    }

    public void reflectNullItemsToStock(String id) {
        NullReport nullReport = inventory.findNullByLink(id);
        if(nullReport != null) {
            List<NullReportItem> itemList = inventory.findAllNullItem(nullReport.getId());
            itemList.forEach(item -> merchandise.updateProductQuantity(item.getQuantity(),item.getId()));
        }
    }

    public boolean reflectDeliveryItemsToStock(String id) {
        DeliveryReport deliveryReport = inventory.findDeliveryByLink(id);
        boolean flag = true;
        if(deliveryReport != null) {
            List<DeliveryReportItem> itemList = inventory.findAllDeliveryItems(deliveryReport.getId());
            for (DeliveryReportItem item : itemList) {
                if(!merchandise.hasStock(item.getProductId(),item.getQuantity())) {
                    flag = false;
                    break;
                }
            }
            if(flag) itemList.forEach(item -> merchandise.updateProductQuantity(-1 * item.getQuantity(), item.getProductId()));
        }
        return flag;
    }

    public List<TransactionReportHistory> getAllArchivedReport() {
        return service.getAllArchivedReport();
    }

    public List<TransactionReportHistory> findArchivedReportBySearch(String search) {
        return service.findArchivedReportBySearch(search);
    }

    public List<TransactionReportHistory> findAllArchivedReportByDate(String start, String end) {
        return service.findAllArchivedReportByDate(start,end);
    }

    public List<TransactionReportHistory> findAllArchivedReportByEnd(String end) {
        return service.findAllArchivedReportByEnd(end);
    }

    public List<TransactionReportHistory> findAllArchivedReportByStart(String start) {
        return service.findAllArchivedReportByStart(start);
    }

    public List<TransactionReportHistory> getAllReport() {
        List<TransactionReportHistory> reportList = service.findAllReportHistory();
        for(TransactionReport report : service.findAllValidReport()) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> getAllReportBySearch(String search) {
        List<TransactionReportHistory> reportList = findArchivedReportBySearch(search);
        for (TransactionReport report : findAllValidReportBySearch(search)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> findAllReportByStart(String start) {
        List<TransactionReportHistory> reportList = findAllArchivedReportByStart(start);
        for (TransactionReport report : findAllValidReportByStart(start)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> findAllReportByEnd(String end) {
        List<TransactionReportHistory> reportList = findAllArchivedReportByEnd(end);
        for (TransactionReport report : findAllValidReportByEnd(end)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> findAllReportByDate(String start, String end) {
        List<TransactionReportHistory> reportList = findAllArchivedReportByDate(start,end);
        for (TransactionReport report : findAllValidReportByDate(start,end)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public SalesSummary calculateAllSales() {
        return service.calculateSales(service.findAllValidReport());
    }

    public SalesSummary calculateSalesByStart(String start) {
        return service.calculateSales(service.findAllValidReportByStart(start));
    }

    public SalesSummary calculateSalesByEnd(String end) {
        return service.calculateSales(service.findAllValidReportByEnd(end));
    }

    public SalesSummary calculateSalesByDate(String start, String end) {
        return service.calculateSales(service.findAllValidReportByDate(start,end));
    }

    public List<ProductSummary> calculateAllProductSales() {
        return service.calculateProductSales(service.findAllValidReport());
    }

    public List<ProductSummary> calculateAllProductSalesByDate(String start, String end) {
        return service.calculateProductSales(service.findAllValidReportByDate(start,end));
    }

    public List<ProductSummary> calculateAllProductSalesByStart(String start) {
        return service.calculateProductSales(service.findAllValidReportByStart(start));
    }

    public List<ProductSummary> calculateAllProductSalesByEnd(String end) {
        return service.calculateProductSales(service.findAllValidReportByEnd(end));
    }
}
