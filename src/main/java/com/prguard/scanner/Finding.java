package com.prguard.scanner;

public record Finding(String ruleId, String message, Severity severity, Confidence confidence) {}
