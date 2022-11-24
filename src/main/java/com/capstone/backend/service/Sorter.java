package com.capstone.backend.service;

import com.capstone.backend.pojo.DeliveryProductSummary;
import com.capstone.backend.pojo.TransactionProductSummary;
import com.capstone.backend.pojo.VoidProductSummary;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class Sorter {
    public List<List<TransactionProductSummary>> transactionInitializeAndSortList(@NotNull Map<String, TransactionProductSummary> productMap) {
        List<List<TransactionProductSummary>> summary = new ArrayList<>();
        List<TransactionProductSummary> summaryQuantity = new ArrayList<>(productMap.values());
        List<TransactionProductSummary> summaryProfit = new ArrayList<>(productMap.values());
        List<TransactionProductSummary> summaryName = new ArrayList<>(productMap.values());
        summaryQuantity.sort(Comparator.comparingInt(TransactionProductSummary::getQuantity).reversed());
        summaryProfit.sort(Comparator.comparing(TransactionProductSummary::getProfit).reversed());
        summaryName.sort(Comparator.comparing(TransactionProductSummary::getName));
        summary.add(summaryQuantity);
        summary.add(summaryProfit);
        summary.add(summaryName);
        return summary;
    }

    public List<List<VoidProductSummary>> voidInitializeAndSortList(@NotNull Map<String, VoidProductSummary> productMap) {
        List<List<VoidProductSummary>> summary = new ArrayList<>();
        List<VoidProductSummary> summaryQuantity = new ArrayList<>(productMap.values());
        List<VoidProductSummary> summaryProfit = new ArrayList<>(productMap.values());
        List<VoidProductSummary> summaryName = new ArrayList<>(productMap.values());
        summaryQuantity.sort(Comparator.comparingInt(VoidProductSummary::getQuantity).reversed());
        summaryProfit.sort(Comparator.comparing(VoidProductSummary::getTotalCapital).reversed());
        summaryName.sort(Comparator.comparing(VoidProductSummary::getName));
        summary.add(summaryQuantity);
        summary.add(summaryProfit);
        summary.add(summaryName);
        return summary;
    }

    public List<List<DeliveryProductSummary>> deliveryInitializeAndSortList(@NotNull Map<String, DeliveryProductSummary> productMap) {
        List<List<DeliveryProductSummary>> summary = new ArrayList<>();
        List<DeliveryProductSummary> summaryQuantity = new ArrayList<>(productMap.values());
        List<DeliveryProductSummary> summaryProfit = new ArrayList<>(productMap.values());
        List<DeliveryProductSummary> summaryName = new ArrayList<>(productMap.values());
        summaryQuantity.sort(Comparator.comparingInt(DeliveryProductSummary::getQuantity).reversed());
        summaryProfit.sort(Comparator.comparing(DeliveryProductSummary::getTotalCost).reversed());
        summaryName.sort(Comparator.comparing(DeliveryProductSummary::getName));
        summary.add(summaryQuantity);
        summary.add(summaryProfit);
        summary.add(summaryName);
        return summary;
    }
}
