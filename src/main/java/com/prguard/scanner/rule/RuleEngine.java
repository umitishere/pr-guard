package com.prguard.scanner.rule;

import com.prguard.scanner.Finding;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RuleEngine {
    public List<Finding> run(String content) { return Collections.emptyList(); }
}
