package com.example.ruleengineast.controller;

import com.example.ruleengineast.entity.Rule;
import com.example.ruleengineast.service.RuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;
   
    
    // Create Rule Endpoint
    @PostMapping("/create")
    public ResponseEntity<?> createRule(@RequestBody Map<String, String> request) {
        try {
            String ruleName = request.get("ruleName");
            String ruleString = request.get("ruleString");
            Rule rule = ruleService.createRule(ruleName, ruleString);
            return ResponseEntity.ok(rule);  // Return 200 OK with the created rule
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating rule: " + e.getMessage());  // Return 400 with error message
        }
    }

    // Combine Rules Endpoint
    @PostMapping("/combine")
    public ResponseEntity<?> combineRules(@RequestBody Map<String, Object> request) {
        try {
            Long ruleId1 = Long.parseLong(request.get("ruleId1").toString());
            Long ruleId2 = Long.parseLong(request.get("ruleId2").toString());
            String operator = request.get("operator").toString();
            Rule combinedRule = ruleService.combineRules(ruleId1, ruleId2, operator);
            return ResponseEntity.ok(combinedRule);  // Return 200 OK with the combined rule
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error combining rules: " + e.getMessage());  // Return 400 with error message
        }
    }

    // Evaluate Rule Endpoint
    @PostMapping("/evaluate")
    public ResponseEntity<?> evaluateRule(@RequestBody Map<String, Object> request) {
        try {
            Long ruleId = Long.parseLong(request.get("ruleId").toString());
            Map<String, Object> userData = (Map<String, Object>) request.get("userData");
            boolean result = ruleService.evaluateRule(ruleId, userData);
            return ResponseEntity.ok(result);  // Return 200 OK with the evaluation result
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error evaluating rule: " + e.getMessage());  // Return 400 with error message
        }
    }
}
