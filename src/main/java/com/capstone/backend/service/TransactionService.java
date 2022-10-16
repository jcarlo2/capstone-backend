package com.capstone.backend.service;

import com.capstone.backend.entity.TransactionDetail;
import com.capstone.backend.entity.TransactionItemDetail;
import com.capstone.backend.repository.TransactionItemRepository;
import com.capstone.backend.repository.TransactionReportRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionReportRepository reportRepository;
    private final TransactionItemRepository itemRepository;

    public TransactionService(TransactionReportRepository reportRepository, TransactionItemRepository itemRepository) {
        this.reportRepository = reportRepository;
        this.itemRepository = itemRepository;
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

    public List<TransactionDetail> getAllReport() {
        return reportRepository.findByIsValidOrderByTimestampDesc("1");
    }

    public List<TransactionDetail> getReportBySearch(String search) {
        return reportRepository.findAllByIdContainsAndIsValidOrderByTimestampDesc(search,"1");
    }

    public List<TransactionDetail> getAllReportByStart(String start) {
        return reportRepository.findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc("1",start);
    }

    public List<TransactionDetail> getAllReportByEnd(String end) {
        return reportRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc("1",end);
    }

    public List<TransactionDetail> getAllReportByDate(String start, String end) {
        return reportRepository.findAllByIsValidAndTimestampBetween("1",start,end);
    }

    public String getNewReportId(@NotNull String id) {
        int num = Character.getNumericValue(id.charAt(id.length() - 1));
        return id.substring(0, id.length()-1) + ++num;
    }

    public List<TransactionItemDetail> findAllItemById(String id) {
        return itemRepository.findAllByUniqueId(id);
    }

    public Boolean saveReportItem(@NotNull List<TransactionItemDetail> itemList) {
        if(itemRepository.existsByUniqueId(itemList.get(0).getUniqueId())) {
            for(TransactionItemDetail item : itemList) {
                itemRepository.updateItem(
                    item.getSold(),
                    item.getSoldTotal(),
                    item.getDiscountPercentage(),
                    item.getTotalAmount(),
                    item.getUniqueId(),
                    item.getProductId()
                );
            }
        }else itemRepository.saveAll(itemList);

        return itemRepository.existsByUniqueId(itemList.get(0).getUniqueId());
    }

    public Boolean saveReport(TransactionDetail report) {
        reportRepository.save(report);
        reportRepository.validate(report.getId());
        return reportRepository.existsById(report.getId());
    }

    public void invalidateReport(String id) {
        reportRepository.invalidate(id);
    }

    public void deleteAll(@NotNull String id) {
        String end = id.substring(17);
        int num = Integer.parseInt(end);
        for(int i=0;i<num + 1;i++) {
            reportRepository.invalidate(id);
            id = reverseId(id);
        }
    }

    public void delete(String id) {
        reportRepository.invalidate(id);
        reportRepository.validate(reverseId(id));
    }

    @Contract(value = "_ -> param1", pure = true)
    private @NotNull String reverseId(@NotNull String id) {
        StringBuilder start = new StringBuilder(id.substring(0,17));
        String end = id.substring(17);
        int num = Integer.parseInt(end) - 1;
        return start.append(num).toString();
    }

    public List<TransactionDetail> getAllArchivedReport() {
        return reportRepository.findAllByIsValidOrderByTimestampDesc("0");
    }

    public List<TransactionDetail> getArchivedReportBySearch(String search) {
        return reportRepository.findAllByIdContainsAndIsValidOrderByTimestampDesc(search,"0");
    }

    public List<TransactionDetail> getAllArchivedReportByDate(String start, String end) {
        return reportRepository.findAllByIsValidAndTimestampBetween("0",start,end);
    }

    public List<TransactionDetail> getAllArchivedReportByEnd(String end) {
        return reportRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc("0",end);
    }

    public List<TransactionDetail> getAllArchivedReportByStart(String start) {
        return reportRepository.findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc("0",start);
    }
}
