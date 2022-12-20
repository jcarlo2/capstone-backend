package com.capstone.backend.service;

import com.capstone.backend.entity.TransactionReport;
import com.capstone.backend.entity.TransactionReportHistory;
import com.capstone.backend.entity.TransactionReportItem;
import com.capstone.backend.entity.TransactionReportItemHistory;
import com.capstone.backend.pojo.SalesSummary;
import com.capstone.backend.pojo.TransactionProductSummary;
import com.capstone.backend.repository.TransactionItemRepository;
import com.capstone.backend.repository.TransactionReportHistoryRepository;
import com.capstone.backend.repository.TransactionReportItemHistoryRepository;
import com.capstone.backend.repository.TransactionReportRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private final TransactionReportRepository reportRepository;
    private final TransactionItemRepository itemRepository;
    private final TransactionReportHistoryRepository reportHistoryRepository;
    private final TransactionReportItemHistoryRepository reportItemHistoryRepository;

    public TransactionService(TransactionReportRepository reportRepository, TransactionItemRepository itemRepository, TransactionReportHistoryRepository reportHistoryRepository, TransactionReportItemHistoryRepository reportItemHistoryRepository) {
        this.reportRepository = reportRepository;
        this.itemRepository = itemRepository;
        this.reportHistoryRepository = reportHistoryRepository;
        this.reportItemHistoryRepository = reportItemHistoryRepository;
    }

    public String generateId() {
        long id;
        String formatId;
        do {
            id = (long) (Math.random() * 1000000000000L);
            formatId = String.format("%013d", id);
        } while (reportRepository.existsById("TR" + formatId + "-A0"));
        return "TR" + formatId + "-A0";
    }

    public boolean isExistingReportId(String id) {
        return reportRepository.existsById(id);
    }

    public List<TransactionReport> findAllValidReport() {
        return reportRepository.findByIsValidOrderByTimestampDesc("1");
    }

    public List<TransactionReport> findAllValidReportBySearch(String search) {
        return reportRepository.findAllByIdContainsAndIsValidOrderByTimestampDesc(search,"1");
    }

    public List<TransactionReport> findAllValidReportByStart(String start) {
        return reportRepository.findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc("1",start);
    }

    public List<TransactionReport> findAllValidReportByEnd(String end) {
        return reportRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc("1",end);
    }

    public List<TransactionReport> findAllValidReportByDate(String start, String end) {
        return reportRepository.findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc("1",start,end);
    }

    public String getNewReportId(@NotNull String id) {
        int num = Character.getNumericValue(id.charAt(id.length() - 1));
        return id.substring(0, id.length()-1) + ++num;
    }

    public List<TransactionReportItem> findAllItemById(String id) {
        return itemRepository.findAllByUniqueId(id);
    }

    public List<TransactionReportItemHistory> findAllItemByIdWithTimestamp(String id, String timestamp) {
        return reportItemHistoryRepository.findAllByReportIdAndTimestamp(id,timestamp);
    }

    public Boolean saveReportItem(@NotNull List<TransactionReportItem> itemList) {
        if(itemList.get(0).getUniqueId() == null) return false;
        itemRepository.deleteAllByUniqueId(itemList.get(0).getUniqueId());
        itemList.forEach(item -> {
            if(item.getSold() > 0) itemRepository.save(item);
        });
        return itemRepository.existsByUniqueId(itemList.get(0).getUniqueId());
    }

    public Boolean saveReport(TransactionReport report) {
        reportRepository.save(report);
        reportRepository.validate(report.getId());
        return reportRepository.existsById(report.getId());
    }

    public void invalidateReport(String id) {
        reportRepository.invalidate(id);
    }

    public void validateReport(String id) {
        reportRepository.validate(id);
    }

    public void archive(String id) {
        reportRepository.invalidate(id);
        reportRepository.validate(reverseId(id));
    }

    public boolean existById(String id) {
        return reportRepository.existsById(id);
    }

    @Contract(value = "_ -> param1", pure = true)
    public @NotNull String reverseId(@NotNull String id) {
        StringBuilder start = new StringBuilder(id.substring(0,17));
        String end = id.substring(17);
        int num = Integer.parseInt(end) - 1;
        return start.append(num).toString();
    }

    public List<TransactionReportHistory> getAllArchivedReport() {
        return reportHistoryRepository.findAllByOrderByTimestampDesc();
    }

    public List<TransactionReportHistory> findArchivedReportBySearch(String search) {
        return reportHistoryRepository.findAllByIdContainsOrderByTimestampDesc(search);
    }

    public List<TransactionReportHistory> findAllArchivedReportByDate(String start, String end) {
        return reportHistoryRepository.findAllByTimestampBetweenOrderByTimestampDesc(start,end);
    }

    public List<TransactionReportHistory> findAllArchivedReportByEnd(String end) {
        return reportHistoryRepository.findAllByTimestampLessThanEqualOrderByTimestampDesc(end);
    }

    public List<TransactionReportHistory> findAllArchivedReportByStart(String start) {
        return reportHistoryRepository.findAllByTimestampGreaterThanEqualOrderByTimestampDesc(start);
    }

    public TransactionReport findReportById(String id) {
        return reportRepository.findById(id).orElse(null);
    }

    public void archiveReport(TransactionReportHistory report, List<TransactionReportItemHistory> itemList) {
        if(report == null) return;
        reportHistoryRepository.save(report);
        reportItemHistoryRepository.saveAll(itemList);
    }

    public List<TransactionReportHistory> findAllReportHistory() {
        return reportHistoryRepository.findAllByOrderByTimestampDesc();
    }

    public boolean existByIdAndTimestamp(String id, String timestamp) {
        return reportItemHistoryRepository.existsByReportIdAndTimestamp(id,timestamp);
    }


    public SalesSummary calculateSales(@NotNull List<TransactionReport> reportList) {
        SalesSummary summary = new SalesSummary(0,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
        reportList.forEach(report -> itemRepository.findAllByUniqueId(report.getId())
            .forEach(item -> {
                BigDecimal totalCapital = item.getCapital().multiply(new BigDecimal(item.getSold()));
                summary.setTotalItem(summary.getTotalItem() + item.getSold());
                summary.setTotalAmount(summary.getTotalAmount().add(item.getTotalAmount()));
                summary.setTotalCapital(summary.getTotalCapital().add(totalCapital));
                summary.setTotalProfit(summary.getTotalProfit().add(item.getTotalAmount().subtract(totalCapital)));
            }));
        return summary;
    }

    /**
     * sort product by concatenating all properties to differentiate
     * product with same id but have different value of other properties
     * then converting to List<ProductSummary>
     */
    public  Map<String, TransactionProductSummary> calculateProductSales(@NotNull List<TransactionReport> reportList) {
        Map<String, TransactionProductSummary> productMap = new HashMap<>();
        reportList.forEach(report -> itemRepository.findAllByUniqueId(report.getId())
            .forEach(item -> {
                String concat = item.getProductId() + item.getName() + item.getPrice() + item.getCapital() + item.getDiscountPercentage();
                BigDecimal totalCapital = item.getCapital().multiply(new BigDecimal(item.getSold()));
                BigDecimal totalProfit = item.getTotalAmount().subtract(totalCapital);
                if(productMap.containsKey(concat)) {
                    TransactionProductSummary summary =  productMap.get(concat);
                    summary.setQuantity(summary.getQuantity() + item.getSold());
                    summary.setTotalPrice(summary.getTotalPrice().add(item.getTotalAmount()));
                    summary.setTotalCapital(summary.getTotalCapital().add(totalCapital));
                    summary.setProfit(summary.getProfit().add(totalProfit));
                }else {
                    productMap.put(concat,new TransactionProductSummary(
                            item.getProductId(),
                            item.getName(),
                            item.getSold(),
                            item.getPrice(),
                            item.getDiscountPercentage(),
                            item.getCapital(),
                            item.getTotalAmount(),
                            totalCapital,
                            totalProfit
                    ));
                }
            }));

        return productMap;
    }

    public String getEarliestTransaction() {
        List<TransactionReport> reportList = reportRepository.findByIsValidOrderByTimestampDesc("1");
        return reportList.get(reportList.size() - 1).getTimestamp().split(" ")[0];
    }
}
