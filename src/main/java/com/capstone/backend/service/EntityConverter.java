package com.capstone.backend.service;

import com.capstone.backend.entity.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityConverter {
    public TransactionReportHistory convertTransactionReport(@NotNull TransactionReport report, String timestamp) {
        return new TransactionReportHistory("", report.getId(), report.getUser(), report.getTotalAmount(), timestamp);
    }

    public List<TransactionReportItemHistory> convertTransactionReportItem(@NotNull List<TransactionReportItem> itemList,String timestamp) {
        List<TransactionReportItemHistory> newItemList = new ArrayList<>();
        for(TransactionReportItem item : itemList) {
            newItemList.add(new TransactionReportItemHistory(
                item.getNum().toString(),
                item.getProductId(),
                item.getName(),
                item.getPrice(),
                item.getSold(),
                item.getSoldTotal(),
                item.getDiscountPercentage(),
                item.getTotalAmount(),
                item.getCapital(),
                item.getUniqueId(),
                timestamp
            ));
        }
        return newItemList;
    }

    public DeliveryReportHistory convertDeliveryReport(@NotNull DeliveryReport report, String timestamp) {
        return new DeliveryReportHistory("", report.getId(), report.getUser(), report.getTotal(), report.getReason(), timestamp, report.getLink());
    }

    public List<DeliveryReportItemHistory> convertDeliveryReportItem(@NotNull List<DeliveryReportItem> itemList, String timestamp, String id) {
        List<DeliveryReportItemHistory> newItemList = new ArrayList<>();
        for(DeliveryReportItem item : itemList) {
            newItemList.add(new DeliveryReportItemHistory(
                    "",
                    item.getProductId(),
                    item.getName(),
                    item.getQuantity().toString(),
                    item.getDiscountPercentage(),
                    item.getTotalPrice(),
                    item.getTotalAmount(),
                    id,
                    timestamp
            ));
        }
        return newItemList;
    }

    public NullReportHistory convertNullReport(@NotNull NullReport report, String timestamp) {
        return new NullReportHistory(
                "",
                report.getId(),
                report.getUser(),
                report.getTotal(),
                report.getReason(),
                timestamp,
                report.getLink());
    }

    public List<NullReportItemHistory> convertNullReportItem(@NotNull List<NullReportItem> itemList, String timestamp, String id) {
        List<NullReportItemHistory> newItemList = new ArrayList<>();
        for(NullReportItem item : itemList) {
            newItemList.add(new NullReportItemHistory(
                    "",
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getDiscount(),
                    item.getTotalAmount(),
                    item.getCapital(),
                    id,
                    timestamp,
                    item.getReason()
            ));
        }
        return newItemList;
    }

    public NullReportHistory convertDeliveryToNullHistory(@NotNull DeliveryReport report, String timestamp) {
        return new NullReportHistory(
                "",
                report.getId(),
                report.getUser(),
                report.getTotal(),
                report.getReason(),
                timestamp,
                report.getLink());
    }

    public List<NullReportHistory> allDeliveryHistoryToNullHistory(@NotNull List<DeliveryReportHistory> reportList) {
        List<NullReportHistory> nullReport = new ArrayList<>();
        for(DeliveryReportHistory report : reportList) {
            nullReport.add(new NullReportHistory(
                    "",
                    report.getId(),
                    report.getUser(),
                    report.getTotal(),
                    report.getReason(),
                    report.getTimestamp(),
                    report.getLink()));
        }
        return nullReport;
    }
}
