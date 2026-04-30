package com.prguard.scanner;

import com.prguard.scanner.rule.RuleEngine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScannerService {
    private final RuleEngine ruleEngine;
    public ScannerService(RuleEngine ruleEngine) { this.ruleEngine = ruleEngine; }
    public List<Finding> scan(String content) { return ruleEngine.run(content); }
}
