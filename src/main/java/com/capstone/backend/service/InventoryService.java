package com.capstone.backend.service;

import com.capstone.backend.entity.DeliveryDetail;
import com.capstone.backend.entity.DeliveryItemDetail;
import com.capstone.backend.entity.NullReport;
import com.capstone.backend.entity.NullReportItem;
import com.capstone.backend.repository.DeliveryDetailRepository;
import com.capstone.backend.repository.DeliveryItemRepository;
import com.capstone.backend.repository.NullItemRepository;
import com.capstone.backend.repository.NullReportRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final NullReportRepository nullReportRepository;
    private final NullItemRepository nullItemRepository;
    private final DeliveryItemRepository deliveryItemRepository;
    private final DeliveryDetailRepository deliveryRepository;

    public InventoryService(NullReportRepository reportRepository, NullItemRepository itemRepository, DeliveryItemRepository deliveryItemRepository, DeliveryDetailRepository deliveryRepository) {
        this.nullReportRepository = reportRepository;
        this.nullItemRepository = itemRepository;
        this.deliveryItemRepository = deliveryItemRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public Boolean saveReport(@NotNull NullReport report) {
        if(!report.getLink().equals("") && nullReportRepository.existsByLink(report.getLink())) {
            NullReport rep = nullReportRepository.findByLink(report.getLink());
            report.setId(rep.getId());
        }
        nullReportRepository.save(report);
        return this.nullReportRepository.existsById(report.getId());
    }

    public Boolean saveReportItem(@NotNull List<NullReportItem> itemList) {
        String link = itemList.get(0).getLink();
        String id = "";
        if(nullReportRepository.existsByLink(link)) id = updateExistingItemList(link,itemList);
        else {
            for(NullReportItem item : itemList) {
                if(item.getReason().equalsIgnoreCase("Exp/Dmg")) {
                    nullItemRepository.save(item);
                    id = item.getReportId();
                }
            }
        }
        return nullItemRepository.existsByReportId(id);
    }

    public String updateExistingItemList(String link,List<NullReportItem> itemList) {
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

    public void saveDeliveryReportItem(List<DeliveryItemDetail> itemList) {
        deliveryItemRepository.saveAll(itemList);
    }

    public void saveDeliveryReport(DeliveryDetail item) {
        deliveryRepository.save(item);
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

    public List<NullReport> getAllNullReport(String isValid) {
        return nullReportRepository.findAllByIsValidOrderByTimestampDesc(isValid);
    }

    public List<DeliveryDetail> getAllDeliveryReport(String isValid) {
        return deliveryRepository.findAllByIsValidOrderByTimestampDesc(isValid);
    }

    public List<DeliveryDetail> getAllDeliveryReportByDate(String isValid, String start, String end) {
        return deliveryRepository.findAllByIsValidAndTimestampBetweenOrderByTimestampDesc(isValid,start,end);
    }

    public List<NullReport> getAllNullReportByDate(String isValid, String start, String end) {
        return nullReportRepository.findAllByIsValidAndTimestampBetweenOrderByTimestampDesc(isValid,start,end);
    }

    public List<DeliveryDetail> getAllDeliveryReportByEnd(String isValid, String end) {
        return deliveryRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(isValid,end);
    }

    public List<NullReport> getAllNullReportByEnd(String isValid, String end) {
        return nullReportRepository.findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(isValid,end);
    }

    public List<DeliveryDetail> getAllDeliveryReportByStart(String isValid, String start) {
        return deliveryRepository.findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(isValid,start);
    }

    public List<NullReport> getAllNullReportByStart(String isValid, String start) {
        return nullReportRepository.findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(isValid,start);
    }

    public List<DeliveryDetail> getAllDeliveryReportBySearch(String isValid, String search) {
        return deliveryRepository.findAllByIsValidAndIdContainsOrderByTimestampDesc(isValid,search);
    }

    public List<NullReport> getAllNullReportBySearch(String isValid, String search) {
        return nullReportRepository.findAllByIsValidAndIdContainsOrLinkContainsOrderByTimestampDesc(isValid,search,search);
    }

    public List<NullReportItem> getAllNullItem(String id) {
        return nullItemRepository.findAllByReportIdOrderByQuantityDesc(id);
    }

    public List<DeliveryItemDetail> getAllDeliveryItem(String id) {
        return deliveryItemRepository.findAllByUniqueIdOrderByQuantity(id);
    }
}
