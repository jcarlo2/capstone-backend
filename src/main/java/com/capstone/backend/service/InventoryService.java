package com.capstone.backend.service;

import com.capstone.backend.entity.NullReport;
import com.capstone.backend.entity.NullReportItem;
import com.capstone.backend.repository.NullItemRepository;
import com.capstone.backend.repository.NullReportRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final NullReportRepository reportRepository;
    private final NullItemRepository itemRepository;

    public InventoryService(NullReportRepository reportRepository, NullItemRepository itemRepository) {
        this.reportRepository = reportRepository;
        this.itemRepository = itemRepository;
    }

    public Boolean saveReport(NullReport report) {
        reportRepository.save(report);
        return this.reportRepository.existsById(report.getId());
    }

    public Boolean saveReportItem(@NotNull List<NullReportItem> itemList) {
        String id = "";
        for(NullReportItem item : itemList) {
            if(item.getReason().equalsIgnoreCase("Exp/Dmg")) {
                itemRepository.save(item);
                id = item.getReportId();
            }
        }
        return itemRepository.existsByReportId(id);
    }

    @NotNull
    public String generateNullId() {
        String formatId = "";
        boolean flag = true;
        while(flag) {
            long id = (long) (Math.random() * 1000000000000L);
            formatId = String.format("%013d",id);
            if(!reportRepository.existsById(formatId)) flag = false;
        }
        return "NP" + formatId + "-A0";
    }
}
