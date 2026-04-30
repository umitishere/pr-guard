package com.prguard.scan;

import com.prguard.scanner.Finding;
import com.prguard.scanner.ScannerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScanService {
    private final ScannerService scannerService;
    public ScanService(ScannerService scannerService) { this.scannerService = scannerService; }
    public List<Finding> scanContent(String content) { return scannerService.scan(content); }
}
