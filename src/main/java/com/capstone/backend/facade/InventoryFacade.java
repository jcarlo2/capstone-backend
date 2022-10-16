package com.capstone.backend.facade;

import com.capstone.backend.entity.DeliveryDetail;
import com.capstone.backend.entity.DeliveryItemDetail;
import com.capstone.backend.entity.NullReport;
import com.capstone.backend.entity.NullReportItem;
import com.capstone.backend.service.InventoryService;
import com.capstone.backend.service.MerchandiseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryFacade {
    private final InventoryService service;
    private final MerchandiseService merchandise;

    public InventoryFacade(InventoryService service, MerchandiseService merchandise) {
        this.service = service;
        this.merchandise = merchandise;
    }

    public Boolean saveNullReport(NullReport report) {
        return service.saveReport(report);
    }

    public Boolean saveReturnReportItem(@NotNull List<NullReportItem> itemList) {
        for(NullReportItem item : itemList) {
            if(item.getReason().equalsIgnoreCase("Return")) {
                merchandise.updateProductQuantity(item.getQuantity(),item.getId());
            }
        }
        return service.saveReportItem(itemList);
    }

    public String generateNullId() {
        return service.generateNullId();
    }

    public void saveDeliveryReportItem(@NotNull List<DeliveryItemDetail> itemList) {
        for(DeliveryItemDetail item : itemList) {
            merchandise.updateProductQuantity(Integer.parseInt(item.getQuantity()), item.getProductId());
        }
        service.saveDeliveryReportItem(itemList);
    }

    public void saveDeliveryReport(DeliveryDetail item) {
        service.saveDeliveryReport(item);
    }

    public String generateDeliveryId() {
        return service.generateDeliveryId();
    }

    public Boolean isDeliveryIdExist(String id) {
        return service.isExistById(id);
    }

    public Boolean isNullIdExist(String id) {
        return service.isNullIdExist(id);
    }

    public void saveNullItem(@NotNull List<NullReportItem> itemList) {
        for(NullReportItem item : itemList) {
            merchandise.updateProductQuantity(-1 * item.getQuantity(),item.getId());
        }
        service.saveNullItem(itemList);
    }

    public List<NullReport> getAllNullReport(String isValid) {
        return service.getAllNullReport(isValid);
    }

    public List<DeliveryDetail> getAllDeliveryReport(String isValid) {
        return service.getAllDeliveryReport(isValid);
    }

    public List<DeliveryDetail> getAllDeliveryReportByDate(String isValid, String start, String end) {
        return service.getAllDeliveryReportByDate(isValid,start,end);
    }

    public List<NullReport> getAllNullReportByDate(String isValid, String start, String end) {
        return service.getAllNullReportByDate(isValid,start,end);
    }

    public List<DeliveryDetail> getAllDeliveryReportByEnd(String isValid, String end) {
        return service.getAllDeliveryReportByEnd(isValid,end);
    }

    public List<NullReport> getAllNullReportByEnd(String isValid, String end) {
        return service.getAllNullReportByEnd(isValid,end);
    }

    public List<DeliveryDetail> getAllDeliveryReportByStart(String isValid, String start) {
        return service.getAllDeliveryReportByStart(isValid,start);
    }

    public List<NullReport> getAllNullReportByStart(String isValid, String start) {
        return service.getAllNullReportByStart(isValid,start);
    }

    public List<DeliveryDetail> getAllDeliveryReportBySearch(String isValid, String search) {
        return service.getAllDeliveryReportBySearch(isValid,search);
    }

    public List<NullReport> getAllNullReportBySearch(String isValid, String search) {
        return service.getAllNullReportBySearch(isValid,search);
    }

    public List<NullReportItem> getAllNullItem(String id) {
        return service.getAllNullItem(id);
    }

    public List<DeliveryItemDetail> getAllDeliveryItem(String id) {
        return service.getAllDeliveryItem(id);
    }
}
