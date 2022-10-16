package com.capstone.backend.facade;

import com.capstone.backend.entity.TransactionDetail;
import com.capstone.backend.entity.TransactionItemDetail;
import com.capstone.backend.service.MerchandiseService;
import com.capstone.backend.service.TransactionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionFacade {

    private final TransactionService service;
    private final MerchandiseService merchandise;

    public TransactionFacade(TransactionService service, MerchandiseService merchandise) {
        this.service = service;
        this.merchandise = merchandise;
    }

    public String generateId() {
        return service.generateId();
    }

    public boolean isExistingReportId(String id) {
        return service.isExistingReportId(id);
    }

    public List<TransactionDetail> getAllReport() {
        return service.getAllReport();
    }

    public List<TransactionDetail> getReportBySearch(String search) {
        return service.getReportBySearch(search);
    }

    public List<TransactionDetail> getAllReportByStart(String start) {
        return service.getAllReportByStart(start);
    }

    public List<TransactionDetail> getAllReportByEnd(String end) {
        return service.getAllReportByEnd(end);
    }

    public List<TransactionDetail> getAllReportByDate(String start, String end) {
        return service.getAllReportByDate(start,end);
    }

    public String getNewReportId(String id) {
        return service.getNewReportId(id);
    }

    public List<TransactionItemDetail> findAllItemById(String id) {
        return service.findAllItemById(id);
    }

    public Boolean saveReportItem(@NotNull List<TransactionItemDetail> itemList) {
        for(TransactionItemDetail item : itemList) {
            if(!merchandise.hasStock(item.getProductId(),item.getSold())) return false;
        }

        for(TransactionItemDetail item : itemList) {
            int quantity = item.getSold() * -1;
            merchandise.updateProductQuantity(quantity,item.getProductId());
        }
        return service.saveReportItem(itemList);
    }

    public Boolean saveReport(TransactionDetail report) {
        return service.saveReport(report);
    }

    public void invalidateReport(String id) {
        service.invalidateReport(id);
    }

    public Boolean saveReturnReportItem(List<TransactionItemDetail> itemList) {
        return service.saveReportItem(itemList);
    }

    public void delete(@NotNull String id) {
        service.delete(id.substring(3));
    }

    public void deleteAll(@NotNull String id) {
        service.deleteAll(id.substring(3));
    }

    public List<TransactionDetail> getAllArchivedReport() {
        return service.getAllArchivedReport();
    }

    public List<TransactionDetail> getArchivedReportBySearch(String search) {
        return service.getArchivedReportBySearch(search);
    }

    public List<TransactionDetail> getAllArchivedReportByDate(String start, String end) {
        return service.getAllArchivedReportByDate(start,end);
    }

    public List<TransactionDetail> getAllArchivedReportByEnd(String end) {
        return service.getAllArchivedReportByEnd(end);
    }

    public List<TransactionDetail> getAllArchivedReportByStart(String start) {
        return service.getAllArchivedReportByStart(start);
    }
}
