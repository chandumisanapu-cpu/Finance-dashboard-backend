package com.financeapp.controller;

import com.financeapp.dto.TransactionDTO;
import com.financeapp.model.Transaction;
import com.financeapp.model.ApiResponse;
import com.financeapp.model.SummaryResponse;
import com.financeapp.service.TransactionService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    // CREATE → ADMIN only
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(@RequestHeader String role,
                              @RequestBody TransactionDTO dto) {

        checkAdmin(role);
        return new ApiResponse("Transaction Created", service.save(dto));
    }

    // READ → ALL ROLES
    @GetMapping
    public ApiResponse getAll(@RequestHeader String role) {
        checkValidRole(role);
        List<Transaction> list = service.getAll();
        return new ApiResponse("Transactions fetched", list);
    }

    // UPDATE → ADMIN only
    @PutMapping("/{id}")
    public ApiResponse update(@RequestHeader String role,
                              @PathVariable Long id,
                              @RequestBody Transaction t) {

        checkAdmin(role);
        return new ApiResponse("Transaction updated", service.update(id, t));
    }

    // DELETE → ADMIN only
    @DeleteMapping("/{id}")
    public ApiResponse delete(@RequestHeader String role, @PathVariable Long id) {
        checkAdmin(role);
        service.delete(id);
        return new ApiResponse("Transaction deleted", null);
    }

    // FILTER BY CATEGORY → ADMIN + ANALYST
    @GetMapping("/category/{category}")
    public ApiResponse getByCategory(@RequestHeader String role,
                                     @PathVariable String category) {

        if (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("ANALYST")) {
            return new ApiResponse("Filtered by category", service.getByCategory(category));
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
    }

    // ✅ NEW FILTER BY TYPE (THIS WAS MISSING)
    @GetMapping("/type/{type}")
    public ApiResponse getByType(@RequestHeader String role,
                                 @PathVariable String type) {

        if (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("ANALYST")) {
            return new ApiResponse("Filtered by type", service.getByType(type));
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
    }

    // ✅ IMPROVED SUMMARY (JSON FORMAT)
    @GetMapping("/summary")
    public ApiResponse getSummary(@RequestHeader String role) {
        checkValidRole(role);

        double income = service.getTotalIncome();
        double expense = service.getTotalExpense();
        double balance = service.getBalance();

        SummaryResponse summary = new SummaryResponse(income, expense, balance);

        return new ApiResponse("Summary fetched", summary);
    }

    // ================= HELPER METHODS =================

    private void checkAdmin(String role) {
        if (!role.equalsIgnoreCase("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
    }

    private void checkValidRole(String role) {
        if (!(role.equalsIgnoreCase("VIEWER") ||
              role.equalsIgnoreCase("ANALYST") ||
              role.equalsIgnoreCase("ADMIN"))) {

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Role");
        }
    }
}