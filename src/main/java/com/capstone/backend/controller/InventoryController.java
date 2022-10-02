package com.capstone.backend.controller;

import com.capstone.backend.entity.NullReport;
import com.capstone.backend.entity.NullReportItem;
import com.capstone.backend.facade.InventoryFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryFacade facade;

    public InventoryController(InventoryFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/save-report-null")
    public Boolean saveReport(@RequestBody NullReport report) {
        return facade.saveReport(report);
    }

    @PostMapping("/save-return-report-item")
    public Boolean saveReportItem(@RequestBody List<NullReportItem> itemList) {
        return facade.saveReturnReportItem(itemList);
    }

    @GetMapping("/generate-id-null")
    public String generateNullId() {
        return facade.generateNullId();
    }
}
