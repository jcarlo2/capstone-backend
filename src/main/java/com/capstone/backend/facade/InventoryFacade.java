package com.capstone.backend.facade;

import com.capstone.backend.entity.*;
import com.capstone.backend.pojo.*;
import com.capstone.backend.service.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class InventoryFacade {
    private final InventoryService service;
    private final TransactionService transaction;
    private final MerchandiseService merchandise;
    private final EntityConverter converter;
    private final Sorter sorter;

    public InventoryFacade(InventoryService service, TransactionService transaction, MerchandiseService merchandise, EntityConverter converter, Sorter sorter) {
        this.service = service;
        this.transaction = transaction;
        this.merchandise = merchandise;
        this.converter = converter;
        this.sorter = sorter;
    }

    public Boolean saveNullReport(NullReport report) {
        return service.saveReport(report);
    }

//    public Boolean saveReturnReportItem(@NotNull List<NullReportItem> itemList) {
//        itemList.forEach(item -> {
//            if(item.getReason().equals("Change"))  merchandise.updateProductQuantity(item.getQuantity(),item.getId());
//        });
//        return service.saveReportItem(itemList);
//    }

    public String generateNullId() {
        return service.generateNullId();
    }

    public void saveDeliveryReportItem(@NotNull List<DeliveryReportItem> itemList) {
        itemList.forEach(item -> merchandise.updateProductQuantity(item.getQuantity(), item.getProductId()));
        service.deleteExistingDeliveryItemsByReportId(itemList.get(0).getUniqueId());
        service.saveDeliveryReportItem(itemList);
    }

    public void saveDeliveryReport(DeliveryReport report) {
        service.saveDeliveryReport(report);
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
        itemList.forEach(item -> merchandise.updateProductQuantity(-1 * item.getQuantity(),item.getId()));
        service.deleteExistingNullItemsByReportId(itemList.get(0).getReportId());
        service.saveNullItem(itemList);
    }

    public List<NullReportHistory> findAllArchivedNullReport() {
        return service.findAllArchivedNullReport();
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReport() {
        return service.findAllArchivedDeliveryReport();
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportByDate(String start, String end) {
        return service.findAllArchivedDeliveryReportByDate(start,end);
    }

    public List<NullReportHistory> findAllArchivedNullReportByDate(String start, String end) {
        return service.findAllArchivedNullReportByDate(start,end);
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportByEnd(String end) {
        return service.findAllArchivedDeliveryReportByEnd(end);
    }

    public List<NullReportHistory> findAllArchivedNullReportByEnd(String end) {
        return service.findAllArchivedNullReportByEnd(end);
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportByStart(String start) {
        return service.findAllArchivedDeliveryReportByStart(start);
    }

    public List<NullReportHistory> findAllArchivedNullReportByStart(String start) {
        return service.findAllArchivedNullReportByStart(start);
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportBySearch(String search) {
        return service.findAllArchivedDeliveryReportBySearch(search);
    }

    public List<NullReportHistory> findAllArchivedNullReportBySearch(String search) {
        return service.findAllArchivedNullReportBySearch(search);
    }

    public List<NullReportItemHistory> findAllNullItems(String id, String timestamp) {
        if(service.existsNullReportByIdAndTimestamp(id,timestamp)) return service.findAllNullItemsHistory(id,timestamp);
        return converter.convertNullReportItem(service.findAllNullItem(id),timestamp,id);
    }

    public List<DeliveryReportItemHistory> findAllDeliveryItems(String id, String timestamp) {
        if(service.existDeliveryReportByIdAndTimestamp(id,timestamp)) return service.findAllDeliveryItemsHistory(id,timestamp);
        return converter.convertDeliveryReportItem(service.findAllDeliveryItems(id),timestamp,id);
    }

    public void archiveDelivery(String id) {
        DeliveryReport report = service.findDeliveryReportById(id);
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        if(report != null) {
            List<DeliveryReportItem> itemList = service.getAllArchivedDeliveryReportItems(id);
            itemList.forEach(item -> {
                merchandise.updateProductQuantity(-1 * item.getQuantity(),item.getProductId());
                service.archiveDeliveryHistory(
                        converter.convertDeliveryReport(report,timestamp),
                        converter.convertDeliveryReportItem(itemList,timestamp,report.getId())
                );
            });
        }
        service.archiveDelivery(id);
    }

    public void archiveNullReport(String id) {
        NullReport report = service.findNullReportById(id);
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        if(report != null) {
            List<NullReportItem> itemList = service.getAllArchivedNullReportItems(id);
            itemList.forEach(item -> merchandise.updateProductQuantity(item.getQuantity(),item.getId()));
            service.archiveNullHistory(
                    converter.convertNullReport(report,timestamp),
                    converter.convertNullReportItem(itemList,timestamp,report.getId())
            );
        }
        service.archiveNull(id);
    }

    // Check if still needed
    public boolean archivedNullReportWithLink(String link, String id) {
        if(service.isExistByIdAndValid(service.forwardId(id))) return false;
        service.invalidateNullReportByLink(id);
        transaction.invalidateReport(link);
        service.invalidateDeliveryReportByLink(link);
        link = transaction.reverseId(link);
        transaction.validateReport(link);
        service.validateNullReportByLink(link);
        service.validateDeliveryReportByLink(link);
        // reflect to product stock
        return true;
    }

    public String generateNullIdForReturn(String link) {
        String reverse = transaction.reverseId(link);
        if(service.existByLink(reverse)) {
           NullReport report = service.findNullByLink(reverse);
           return service.forwardId(report.getId());
        }
        return service.generateNullId();
    }

    public List<NullReportHistory> findAllNullAndDeliveryReport() {
        List<NullReportHistory> nullList = service.findAllNullReportHistory();
        nullList.addAll(converter.allDeliveryHistoryToNullHistory(service.findAllDeliveryReportHistory()));
        service.findAllNullReport("1").forEach((r) -> nullList.add(converter.convertNullReport(r,r.getTimestamp())));
        service.findAllDeliveryReport("1").forEach((r) -> nullList.add(converter.convertDeliveryToNullHistory(r,r.getTimestamp())));
        nullList.sort(Comparator.comparing(NullReportHistory::getTimestamp).reversed());
        return nullList;
    }

    public List<NullReport> getAllNullReport() {
        return service.findAllNullReport("1");
    }

    public List<DeliveryReport> getAllDeliveryReport() {
        return service.findAllDeliveryReport("1");
    }

    public List<DeliveryReport> getAllDeliveryReportByDate(String start, String end) {
        return service.findAllDeliveryReportByDate(start,end);
    }

    public List<NullReport> getAllNullReportByDate(String start, String end) {
        return service.findAllNullReportByDate(start,end);
    }

    public List<DeliveryReport> getAllDeliveryReportByEnd(String end) {
        return service.findAllDeliveryReportByEnd(end);
    }

    public List<NullReport> getAllNullReportByEnd(String end) {
        return service.findAllNullReportByEnd(end);

    }

    public List<DeliveryReport> getAllDeliveryReportByStart(String start) {
        return service.findAllDeliveryReportByStart(start);
    }

    public List<NullReport> getAllNullReportByStart(String start) {
        return service.findAllNullReportByStart(start);
    }

    public List<DeliveryReport> getAllDeliveryReportBySearch(String search) {
        return service.findAllDeliveryReportBySearch(search);
    }

    public List<NullReport> getAllNullReportBySearch(String search) {
        return service.findAllNullReportBySearch(search);
    }

    public boolean findIfIdExistFromNullAndDeliveryHistory(@NotNull String id, String timestamp) {
        if(id.charAt(0) == 'N') return service.existsNullReportByIdAndTimestamp(id,timestamp);
        return service.existDeliveryReportByIdAndTimestamp(id,timestamp);
    }

    public List<NullReportHistory> findAllNullAndDeliveryReportBySearch(String search) {
        List<NullReportHistory> report = service.findAllArchivedNullReportBySearch(search);
        service.findAllNullReportBySearch(search).forEach((r) -> report.add(converter.convertNullReport(r,r.getTimestamp())));
        service.findAllDeliveryReportBySearch(search).forEach((r) -> report.add(converter.convertDeliveryToNullHistory(r,r.getTimestamp())));
        report.addAll(converter.allDeliveryHistoryToNullHistory(service.findAllArchivedDeliveryReportBySearch(search)));
        report.sort(Comparator.comparing(NullReportHistory::getTimestamp).reversed());
        return report;
    }

    public List<NullReportHistory> findAllNullAndDeliveryReportByDate(String start, String end) {
        List<NullReportHistory> report = service.findAllArchivedNullReportByDate(start,end);
        service.findAllNullReportByDate(start,end).forEach((r) -> report.add(converter.convertNullReport(r,r.getTimestamp())));
        service.findAllDeliveryReportByDate(start,end).forEach((r) -> report.add(converter.convertDeliveryToNullHistory(r,r.getTimestamp())));
        report.addAll(converter.allDeliveryHistoryToNullHistory(service.findAllArchivedDeliveryReportByDate(start,end)));
        report.sort(Comparator.comparing(NullReportHistory::getTimestamp).reversed());
        return report;
    }

    public List<NullReportHistory> findAllNullAndDeliveryReportByEnd(String end) {
        List<NullReportHistory> report = service.findAllArchivedNullReportByEnd(end);
        service.findAllNullReportByEnd(end).forEach((r) -> report.add(converter.convertNullReport(r,r.getTimestamp())));
        service.findAllDeliveryReportByEnd(end).forEach((r) -> report.add(converter.convertDeliveryToNullHistory(r,r.getTimestamp())));
        report.addAll(converter.allDeliveryHistoryToNullHistory(service.findAllArchivedDeliveryReportByEnd(end)));
        report.sort(Comparator.comparing(NullReportHistory::getTimestamp).reversed());
        return report;
    }

    public List<NullReportHistory> findAllNullAndDeliveryReportByStart(String start) {
        List<NullReportHistory> report = service.findAllArchivedNullReportByStart(start);
        service.findAllNullReportByStart(start).forEach((r) -> report.add(converter.convertNullReport(r,r.getTimestamp())));
        service.findAllDeliveryReportByStart(start).forEach((r) -> report.add(converter.convertDeliveryToNullHistory(r,r.getTimestamp())));
        report.addAll(converter.allDeliveryHistoryToNullHistory(service.findAllArchivedDeliveryReportByStart(start)));
        report.sort(Comparator.comparing(NullReportHistory::getTimestamp).reversed());
        return report;
    }

    public VoidSummary calculateAllVoidSales() {
        return service.calculateSales(service.findAllNullReport("1"));
    }

    public VoidSummary calculateAllVoidSalesByStart(String start) {
        return service.calculateSales(service.findAllNullReportByStart(start));
    }

    public VoidSummary calculateAllVoidSalesByEnd(String end) {
        return service.calculateSales(service.findAllNullReportByEnd(end));
    }

    public VoidSummary calculateAllVoidSalesByDate(String start, String end) {
        return service.calculateSales(service.findAllNullReportByDate(start,end));
    }

    public List<List<VoidProductSummary>> calculateProductVoidAll() {
        Map<String,VoidProductSummary> summaryList = service.calculateProductVoidSales(service.findAllNullReport("1"));
        return sorter.voidInitializeAndSortList(summaryList);
    }

    public List<List<VoidProductSummary>> calculateProductVoidByDate(String start, String end) {
        Map<String,VoidProductSummary> summaryList = service.calculateProductVoidSales(service.findAllNullReportByDate(start,end));
        return sorter.voidInitializeAndSortList(summaryList);
    }

    public List<List<VoidProductSummary>> calculateProductVoidByStart(String start) {
        Map<String,VoidProductSummary> summaryList = service.calculateProductVoidSales(service.findAllNullReportByStart(start));
        return sorter.voidInitializeAndSortList(summaryList);
    }

    public List<List<VoidProductSummary>> calculateProductVoidByEnd(String end) {
        Map<String,VoidProductSummary> summaryList = service.calculateProductVoidSales(service.findAllNullReportByEnd(end));
        return sorter.voidInitializeAndSortList(summaryList);
    }

    public List<List<DeliveryProductSummary>> calculateProductDeliveryAll() {
        Map<String, DeliveryProductSummary> summaryList = service.calculateProductDeliverySales(service.findAllDeliveryReport("1"));
        return sorter.deliveryInitializeAndSortList(summaryList);
    }

    public List<List<DeliveryProductSummary>> calculateProductDeliveryByDate(String start, String end) {
        Map<String, DeliveryProductSummary> summaryList = service.calculateProductDeliverySales(service.findAllDeliveryReportByDate(start,end));
        return sorter.deliveryInitializeAndSortList(summaryList);
    }

    public List<List<DeliveryProductSummary>> calculateProductDeliveryByStart(String start) {
        Map<String, DeliveryProductSummary> summaryList = service.calculateProductDeliverySales(service.findAllDeliveryReportByStart(start));
        return sorter.deliveryInitializeAndSortList(summaryList);
    }

    public List<List<DeliveryProductSummary>> calculateProductDeliveryByEnd(String end) {
        Map<String, DeliveryProductSummary> summaryList = service.calculateProductDeliverySales(service.findAllDeliveryReportByEnd(end));
        return sorter.deliveryInitializeAndSortList(summaryList);
    }

    public DeliverySummary calculateAllDeliverySales() {
        return service.calculateAllDeliverySales(service.findAllDeliveryReport("1"));
    }

    public DeliverySummary calculateAllDeliverySalesByDate(String start, String end) {
        return service.calculateAllDeliverySales(service.findAllDeliveryReportByDate(start,end));
    }

    public DeliverySummary calculateAllDeliverySalesByStart(String start) {
        return service.calculateAllDeliverySales(service.findAllDeliveryReportByStart(start));
    }

    public DeliverySummary calculateAllDeliverySalesByEnd(String end) {
        return service.calculateAllDeliverySales(service.findAllDeliveryReportByEnd(end));
    }
}
