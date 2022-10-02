package com.capstone.backend.facade;

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

    public Boolean saveReport(NullReport report) {
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
}
