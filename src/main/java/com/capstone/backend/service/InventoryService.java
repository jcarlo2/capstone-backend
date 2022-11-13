package com.capstone.backend.service;

import com.capstone.backend.entity.*;
import com.capstone.backend.pojo.ProductSummary;
import com.capstone.backend.pojo.SalesSummary;
import com.capstone.backend.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InventoryService {
    private final NullReportRepository nullReportRepository;
    private final NullItemRepository nullItemRepository;
    private final DeliveryReportItemRepository deliveryReportItemRepository;
    private final DeliveryReportRepository deliveryRepository;
    private final DeliveryReportHistoryRepository deliveryReportHistoryRepository;
    private final DeliveryReportItemHistoryRepository deliveryReportItemHistoryRepository;
    private final NullReportHistoryRepository nullReportHistoryRepository;
    private final NullReportItemHistoryRepository nullReportItemHistoryRepository;

    public InventoryService(NullReportRepository reportRepository, NullItemRepository itemRepository, DeliveryReportItemRepository deliveryReportItemRepository, DeliveryReportRepository deliveryRepository, DeliveryReportHistoryRepository deliveryReportHistoryRepository, DeliveryReportItemHistoryRepository deliveryReportItemHistoryRepository, NullReportHistoryRepository nullReportHistoryRepository, NullReportItemHistoryRepository nullReportItemHistoryRepository) {
        this.nullReportRepository = reportRepository;
        this.nullItemRepository = itemRepository;
        this.deliveryReportItemRepository = deliveryReportItemRepository;
        this.deliveryRepository = deliveryRepository;
        this.deliveryReportHistoryRepository = deliveryReportHistoryRepository;
        this.deliveryReportItemHistoryRepository = deliveryReportItemHistoryRepository;
        this.nullReportHistoryRepository = nullReportHistoryRepository;
        this.nullReportItemHistoryRepository = nullReportItemHistoryRepository;
    }

    public boolean saveReport(@NotNull NullReport report) {
        if(!report.getLink().equals("") && nullReportRepository.existsByLink(report.getLink())) {
            NullReport rep = nullReportRepository.findByLink(report.getLink());
            report.setId(rep.getId());
        }
        nullReportRepository.save(report);
        return this.nullReportRepository.existsById(report.getId());
    }

    public NullReport findNullByLink(String link) {
        return nullReportRepository.findByLink(link);
    }

    public DeliveryReport findDeliveryByLink(String link) {
        return deliveryRepository.findByLink(link);
    }


    public boolean existByLink(String link) {
        return nullReportRepository.existsByLink(link);
    }

    public boolean saveReportItem(@NotNull List<NullReportItem> itemList) {
        String link = itemList.get(0).getLink();
        String id = "";
        if(nullReportRepository.existsByLink(link)) id = updateExistingNullItemList(link,itemList);
        else {
            for(NullReportItem item : itemList) {
                if(item.getReason().equals("Exp/Dmg")) {
                    nullItemRepository.save(item);
                    id = item.getReportId();
                }
            }
        }
        return nullItemRepository.existsByReportId(id);
    }

    public String updateExistingNullItemList(String link, List<NullReportItem> itemList) {
        String id = "";
        NullReport rep = nullReportRepository.findByLink(link);
        filterToDeleteNullItems(rep,itemList);
        for(NullReportItem item : itemList) {
            if(item.getReason().equalsIgnoreCase("Exp/Dmg")) {
                nullItemRepository.updateByReportIdAndId(item.getQuantity(),item.getDiscount(),item.getTotalAmount(), rep.getId(),item.getId());
                id = item.getReportId();
            }
        }
        return id;
    }

    public void filterToDeleteNullItems(@NotNull NullReport rep, List<NullReportItem> itemList) {
        for(NullReportItem item : nullItemRepository.findAllByReportId(rep.getId())) {
            boolean flag = true;
            for(NullReportItem newItem : itemList) {
                if(newItem.getId().equalsIgnoreCase(item.getId())) {
                    flag = false;
                    break;
                }
            }
            if(flag) nullItemRepository.deleteNullReportItemByReportIdAndId(rep.getId(), item.getId());
        }
    }

    @NotNull
    public String generateNullId() {
        String formatId;
        do {
            long id = (long) (Math.random() * 1000000000000L);
            formatId = String.format("%013d", id);
        } while (nullReportRepository.existsById("NP" + formatId + "-A0"));
        return "NP" + formatId + "-A0";
    }

    public boolean isExistByIdAndValid(String id) {
        return nullReportRepository.existsByIdAndIsValid(id,"1");
    }

    public void saveDeliveryReportItem(List<DeliveryReportItem> itemList) {
        deliveryReportItemRepository.saveAll(itemList);
    }

    public void saveDeliveryReport(@NotNull DeliveryReport report) {
        if(!report.getLink().equals("") && deliveryRepository.existsByLink(report.getLink())) {
            DeliveryReport rep = deliveryRepository.findByLink(report.getLink());
            report.setId(rep.getId());
        }
        deliveryRepository.save(report);
    }

    public String generateDeliveryId() {
        String formatId;
        do {
            long id = (long) (Math.random() * 1000000000000L);
            formatId = String.format("%013d", id);
        } while (deliveryRepository.existsById("IP" + formatId + "-A0"));
        return "IP" + formatId + "-A0";
    }

    public Boolean isExistById(String id) {
        return deliveryRepository.existsById(id);
    }

    public Boolean isNullIdExist(String id) {
        return nullReportRepository.existsById(id);
    }

    public void saveNullItem(List<NullReportItem> itemList) {
        nullItemRepository.saveAll(itemList);
    }

    public List<NullReportHistory> findAllArchivedNullReport() {
        return nullReportHistoryRepository.findAllByOrderByTimestampDesc();
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReport() {
        return deliveryReportHistoryRepository.findAllByOrderByTimestampDesc();
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportByDate(String start, String end) {
        return deliveryReportHistoryRepository.findAllByTimestampBetweenOrderByTimestampDesc(start,end);
    }

    public List<NullReportHistory> findAllArchivedNullReportByDate(String start, String end) {
        return nullReportHistoryRepository.findAllByTimestampBetweenOrderByTimestampDesc(start,end);
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportByEnd(String end) {
        return deliveryReportHistoryRepository.findAllByTimestampLessThanEqualOrderByTimestampDesc(end);
    }

    public List<NullReportHistory> findAllArchivedNullReportByEnd(String end) {
        return nullReportHistoryRepository.findAllByTimestampLessThanEqualOrderByTimestampDesc(end);
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportByStart(String start) {
        return deliveryReportHistoryRepository.findAllByTimestampGreaterThanEqualOrderByTimestampDesc(start);
    }

    public List<NullReportHistory> findAllArchivedNullReportByStart(String start) {
        return nullReportHistoryRepository.findAllByTimestampGreaterThanEqualOrderByTimestampDesc(start);
    }

    public List<DeliveryReportHistory> findAllArchivedDeliveryReportBySearch(String search) {
        return deliveryReportHistoryRepository.findAllByIdContainsOrLinkContainsOrderByTimestampDesc(search,search);
    }

    public List<NullReportHistory> findAllArchivedNullReportBySearch(String search) {
        return nullReportHistoryRepository.findAllByIdContainsOrLinkContainsOrderByTimestampDesc(search,search);
    }

    public List<NullReportItem> findAllNullItem(String id) {
        return nullItemRepository.findAllByReportIdOrderByQuantityDesc(id);
    }

    public List<NullReportItemHistory> findAllNullItemsHistory(String id, String timestamp) {
        return nullReportItemHistoryRepository.findAllByReportIdAndTimestamp(id,timestamp);
    }

    public boolean existsNullReportByIdAndTimestamp(String id, String timestamp) {
       return nullReportHistoryRepository.existsByIdAndTimestamp(id,timestamp);
    }

    public List<DeliveryReportItem> findAllDeliveryItems(String id) {
        return deliveryReportItemRepository.findAllByUniqueIdOrderByQuantity(id);
    }

    public void archiveDelivery(String id) {
        deliveryRepository.archivedDelivery(id);
    }

    public void archiveNull(String id) {
        nullReportRepository.invalidateNullReportById(id);
    }

    public List<NullReportItem> getAllArchivedNullReportItems(String id) {
        return nullItemRepository.findAllByReportId(id);
    }

    public void invalidateNullReportByLink(String id) {
        nullReportRepository.invalidateNullReportByLink(id);
    }

    public String forwardId(@NotNull String id) {
        StringBuilder start = new StringBuilder(id.substring(0,17));
        String end = id.substring(17);
        int num = Integer.parseInt(end) + 1;
        return start.append(num).toString();
    }

    public NullReport findNullReportById(String id) {
        Optional<NullReport> opt = nullReportRepository.findById(id);
        return opt.orElse(null);
    }

    public DeliveryReport findDeliveryReportById(String id) {
        Optional<DeliveryReport> opt = deliveryRepository.findById(id);
        return opt.orElse(null);
    }

    public void validateNullReportByLink(String link) {
        nullReportRepository.validateNullReportByLink(link);
    }

    public void validateDeliveryReportByLink(String link) {
        deliveryRepository.validateDeliveryReportByLink(link);
    }

    public void invalidateDeliveryReportByLink(String link) {
        deliveryRepository.invalidateDeliveryReportByLink(link);
    }

    public List<DeliveryReportItem> getAllArchivedDeliveryReportItems(String id) {
        return deliveryReportItemRepository.findAllByUniqueId(id);
    }

    public void archiveDeliveryHistory(DeliveryReportHistory report, List<DeliveryReportItemHistory> itemList) {
        deliveryReportHistoryRepository.save(report);
        deliveryReportItemHistoryRepository.saveAll(itemList);
    }

    public void archiveNullHistory(NullReportHistory report, List<NullReportItemHistory> itemList) {
        nullReportHistoryRepository.save(report);
        nullReportItemHistoryRepository.saveAll(itemList);
    }

    public List<NullReportHistory> findAllNullReportHistory() {
        List<NullReportHistory> report = new ArrayList<>();
        nullReportHistoryRepository.findAll().forEach(report::add);
        return report;
    }

    public List<DeliveryReportHistory> findAllDeliveryReportHistory() {
        List<DeliveryReportHistory> report = new ArrayList<>();
        deliveryReportHistoryRepository.findAll().forEach(report::add);
        return report;
    }

    public List<NullReport> findAllNullReport(String isValid) {
        return nullReportRepository.findAllByIsValidOrderByTimestampDesc(isValid);
    }

    public List<DeliveryReport> findAllDeliveryReport(String isValid) {
        return deliveryRepository.findAllByIsValidOrderByTimestampDesc(isValid);
    }

    public List<DeliveryReport> findAllDeliveryReportByDate(String start, String end) {
        return deliveryRepository.findAllByIsValidAndTimestampBetweenOrderByTimestampDesc("1",start,end);
    }

    public List<NullReport> findAllNullReportByDate(String start, String end) {
        return nullReportRepository.findAllByIsValidAndTimestampBetweenOrderByTimestampDesc("1",start,end);
    }

    public List<DeliveryReport> findAllDeliveryReportByEnd(String end) {
        return deliveryRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc("1",end);
    }

    public List<NullReport> findAllNullReportByEnd(String end) {
        return nullReportRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc("1",end);
    }

    public List<DeliveryReport> findAllDeliveryReportByStart(String start) {
        return deliveryRepository.findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc("1",start);
    }

    public List<NullReport> findAllNullReportByStart(String start) {
        return nullReportRepository.findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc("1",start);
    }

    public List<DeliveryReport> findAllDeliveryReportBySearch(String search) {
        return deliveryRepository.findAllByIsValidAndIdContainsOrIsValidAndLinkContainsOrderByTimestampDesc("1",search,"1",search);
    }

    public List<NullReport> findAllNullReportBySearch(String search) {
        return nullReportRepository.findAllByIsValidAndIdContainsOrIsValidAndLinkContainsOrderByTimestampDesc("1",search,"1",search);
    }

    public void deleteExistingDeliveryItemsByReportId(String id) {
        deliveryReportItemRepository.deleteExistingItems(id);
    }

    public void deleteExistingNullItemsByReportId(String id) {
        nullItemRepository.deleteExistingItems(id);
    }

    public boolean existDeliveryReportByIdAndTimestamp(String id, String timestamp) {
        return deliveryReportHistoryRepository.existsByIdAndTimestamp(id,timestamp);
    }

    public List<DeliveryReportItemHistory> findAllDeliveryItemsHistory(String id, String timestamp) {
        return deliveryReportItemHistoryRepository.findAllByReportIdAndTimestamp(id,timestamp);
    }

    public SalesSummary calculateSales(@NotNull List<NullReport> reportList) {
        int sold = 0;
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal capital = BigDecimal.ZERO;
        for(NullReport report : reportList) {
            for(NullReportItem item : nullItemRepository.findAllByReportId(report.getId())) {
                sold += item.getQuantity();
                price = price.add(item.getTotalAmount());
                capital = capital.add(item.getCapital().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return new SalesSummary(sold,price,capital,price.subtract(capital));
    }

    /**
     * sort product by concatenating all properties to differentiate
     * product with same id but have different value of other properties
     * then converting to List<ProductSummary>
     */
    public List<ProductSummary> calculateProductVoidSales(List<NullReport> reportList) {
        Map<String,List<NullReportItem>> map = sortProductVoidToMap(reportList);
        Map<String,ProductSummary> productMap = new HashMap<>();
        map.keySet()
            .forEach(key -> map.get(key)
                .forEach(item ->{
                    String str = item.getId() + item.getCapital() + item.getPrice() + item.getName();
                    BigDecimal totalCapital = item.getCapital().multiply(new BigDecimal(item.getQuantity()));
                    BigDecimal profit = item.getTotalAmount().subtract(totalCapital);
                    if(!productMap.containsKey(str)) {
                        productMap.put(str,new ProductSummary(
                                item.getId(),
                                item.getName(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getDiscount(),
                                item.getCapital(),
                                item.getTotalAmount(),
                                totalCapital,
                                profit
                        ));
                    } else {
                        ProductSummary summary = productMap.get(str);
                        summary.setQuantity(summary.getQuantity() + item.getQuantity());
                        summary.setTotalPrice(summary.getTotalPrice().add(item.getTotalAmount()));
                        summary.setTotalCapital(summary.getTotalCapital().add(totalCapital));
                        summary.setProfit(summary.getProfit().add(profit));
                    }
                }));
        return new ArrayList<>(productMap.values());
    }

    /**
     * sort product by concatenating all properties to differentiate
     * product with same id but have different value of other properties
     * then converting to List<ProductSummary>
     */
    public List<ProductSummary> calculateProductDeliverySales(List<DeliveryReport> reportList) {
        Map<String,List<DeliveryReportItem>> map = sortProductDeliveryToMap(reportList);
        Map<String,ProductSummary> productMap = new HashMap<>();
        map.keySet()
            .forEach(key -> map.get(key)
                .forEach(item -> {
                    String str = item.getProductId() + item.getCapital() + item.getTotalPrice() + item.getName();
                    BigDecimal totalCapital = item.getCapital().multiply(new BigDecimal(item.getQuantity()));
                    BigDecimal profit = item.getTotalAmount().subtract(totalCapital);
                    if(!productMap.containsKey(str)) {
                        productMap.put(str,new ProductSummary(
                                item.getProductId(),
                                item.getName(),
                                item.getQuantity(),
                                item.getTotalPrice(),
                                item.getDiscountPercentage(),
                                item.getCapital(),
                                item.getTotalAmount(),
                                totalCapital,
                                profit
                        ));
                    } else {
                        ProductSummary summary = productMap.get(str);
                        summary.setQuantity(summary.getQuantity() + item.getQuantity());
                        summary.setTotalPrice(summary.getTotalPrice().add(item.getTotalAmount()));
                        summary.setTotalCapital(summary.getTotalCapital().add(totalCapital));
                        summary.setProfit(summary.getProfit().add(profit));
                    }
                }));
        return new ArrayList<>(productMap.values());
    }

    /**
     * [MAP : ID -> [MAP : PROPERTIES -> LIST NULL ITEM]]
     * sort product by id and capital
     */
    public Map<String,List<NullReportItem>> sortProductVoidToMap(@NotNull List<NullReport> reportList) {
        Map<String,List<NullReportItem>> map = new HashMap<>();
        reportList.forEach(report -> nullItemRepository.findAllByReportId(report.getId())
            .forEach(item -> {
                String id = item.getId();
                if(!map.containsKey(id)) {
                    map.put(id, new ArrayList<>());
                    map.get(id).add(item);
                } else map.get(id).add(item);
            }));
        return map;
    }

    /**
     * [MAP : ID -> [MAP : PROPERTIES -> LIST DELIVERY ITEM]]
     * sort product by id and capital
     */
    public Map<String,List<DeliveryReportItem>> sortProductDeliveryToMap(@NotNull List<DeliveryReport> reportList) {
        Map<String,List<DeliveryReportItem>> map = new HashMap<>();
        reportList.forEach(report -> {
            if(report.getReason().equals("Default")) {
                deliveryReportItemRepository.findAllByUniqueId(report.getId())
                    .forEach(item -> {
                        String id = item.getProductId();
                        if(!map.containsKey(id)) {
                            map.put(id, new ArrayList<>());
                            map.get(id).add(item);
                        } else map.get(id).add(item);
                    });
            }
        });
        return map;
    }

    public SalesSummary calculateAllDeliverySales(List<DeliveryReport> reportList) {
        SalesSummary summary = new SalesSummary(0,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
        reportList
            .forEach(report -> findAllDeliveryItems(report.getId())
                .forEach(item -> {
                    summary.setTotalItem(summary.getTotalItem() + item.getQuantity());
                    summary.setTotalAmount(summary.getTotalAmount().add(item.getTotalAmount()));
                }));
        return summary;
    }
}
