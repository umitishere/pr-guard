package com.prguard.scanner.rule;

import com.prguard.scanner.Finding;
import java.util.List;

public interface Rule {
    List<Finding> evaluate(String content);
}
