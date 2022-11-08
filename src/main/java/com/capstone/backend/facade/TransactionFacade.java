package com.capstone.backend.facade;

import com.capstone.backend.entity.*;
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

    public List<TransactionReport> getAllValidReport() {
        return service.getAllValidReport();
    }

    public List<TransactionReport> getAllValidReportBySearch(String search) {
        return service.getAllValidReportBySearch(search);
    }

    public List<TransactionReport> getAllValidReportByStart(String start) {
        return service.getAllValidReportByStart(start);
    }

    public List<TransactionReport> getAllValidReportByEnd(String end) {
        return service.getAllValidReportByEnd(end);
    }

    public List<TransactionReport> getAllValidReportByDate(String start, String end) {
        return service.getAllValidReportByDate(start,end);
    }

    public String getNewReportId(String id) {
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

        for(TransactionReportItem item : itemList) {
            int quantity = item.getSold() * -1;
            merchandise.updateProductQuantity(quantity,item.getProductId());
        }
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
        List<TransactionReportItem> itemList = service.findAllItemById(id);
        for(TransactionReportItem item : itemList) {
            merchandise.updateProductQuantity(item.getSold(),item.getProductId());
        }
    }

    public void reflectNullItemsToStock(String id) {
        NullReport nullReport = inventory.findNullByLink(id);
        if(nullReport != null) {
            List<NullReportItem> itemList = inventory.findAllNullItem(nullReport.getId());
            for(NullReportItem item : itemList) {
                merchandise.updateProductQuantity(item.getQuantity(),item.getId());
            }
        }
    }

    public boolean reflectDeliveryItemsToStock(String id) {
        // FIX SHOULD BE IN MERCHANDISE SERVICE
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
            if(flag) {
                for (DeliveryReportItem item : itemList) {
                    merchandise.updateProductQuantity(-1 * item.getQuantity(), item.getProductId());
                }
            }
        }
        return flag;
    }

    public List<TransactionReportHistory> getAllArchivedReport() {
        return service.getAllArchivedReport();
    }

    public List<TransactionReportHistory> getArchivedReportBySearch(String search) {
        return service.getArchivedReportBySearch(search);
    }

    public List<TransactionReportHistory> getAllArchivedReportByDate(String start, String end) {
        return service.getAllArchivedReportByDate(start,end);
    }

    public List<TransactionReportHistory> getAllArchivedReportByEnd(String end) {
        return service.getAllArchivedReportByEnd(end);
    }

    public List<TransactionReportHistory> getAllArchivedReportByStart(String start) {
        return service.getAllArchivedReportByStart(start);
    }

    public List<TransactionReportHistory> getAllReport() {
        List<TransactionReportHistory> reportList = service.findAllReportHistory();
        for(TransactionReport report : service.getAllReport()) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> getAllReportBySearch(String search) {
        List<TransactionReportHistory> reportList = getArchivedReportBySearch(search);
        for (TransactionReport report : getAllValidReportBySearch(search)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> getAllReportByStart(String start) {
        List<TransactionReportHistory> reportList = getAllArchivedReportByStart(start);
        for (TransactionReport report : getAllValidReportByStart(start)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> getAllReportByEnd(String end) {
        List<TransactionReportHistory> reportList = getAllArchivedReportByEnd(end);
        for (TransactionReport report : getAllValidReportByEnd(end)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }

    public List<TransactionReportHistory> getAllReportByDate(String start, String end) {
        List<TransactionReportHistory> reportList = getAllArchivedReportByDate(start,end);
        for (TransactionReport report : getAllValidReportByDate(start,end)) {
            reportList.add(converter.convertTransactionReport(report,report.getTimestamp()));
        }
        reportList.sort(Collections.reverseOrder(Comparator.comparing(TransactionReportHistory::getTimestamp)));
        return reportList;
    }
}
