package com.financeapp.service;

import com.financeapp.dto.TransactionDTO;
import com.financeapp.model.Transaction;
import com.financeapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    // ✅ CREATE using DTO (UPDATED)
    public Transaction save(TransactionDTO dto) {

        validateDTO(dto);

        Transaction t = new Transaction();
        t.setAmount(dto.getAmount());
        t.setType(dto.getType());
        t.setCategory(dto.getCategory());
        t.setDate(dto.getDate());
        t.setNotes(dto.getNotes());

        return repo.save(t);
    }

    public List<Transaction> getAll() {
        return repo.findAll();
    }

    public Transaction update(Long id, Transaction t) {

        validateTransaction(t);

        Transaction existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setAmount(t.getAmount());
        existing.setType(t.getType());
        existing.setCategory(t.getCategory());
        existing.setDate(t.getDate());
        existing.setNotes(t.getNotes());

        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        repo.deleteById(id);
    }

    public List<Transaction> getByCategory(String category) {
        return repo.findByCategory(category);
    }

    // ✅ NEW FILTER (TYPE)
    public List<Transaction> getByType(String type) {
        return repo.findByType(type);
    }

    // ✅ SUMMARY LOGIC
    public double getTotalIncome() {
        return repo.findAll().stream()
                .filter(t -> t.getType().equalsIgnoreCase("income"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return repo.findAll().stream()
                .filter(t -> t.getType().equalsIgnoreCase("expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpense();
    }

    // ================= VALIDATION =================

    // ✅ DTO validation
    private void validateDTO(TransactionDTO t) {

        if (t.getAmount() <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        if (t.getType() == null ||
           !(t.getType().equalsIgnoreCase("income") ||
             t.getType().equalsIgnoreCase("expense"))) {
            throw new RuntimeException("Type must be 'income' or 'expense'");
        }

        if (t.getCategory() == null || t.getCategory().isEmpty()) {
            throw new RuntimeException("Category is required");
        }

        if (t.getDate() == null) {
            throw new RuntimeException("Date is required");
        }
    }

    // ✅ Entity validation
    private void validateTransaction(Transaction t) {

        if (t == null) {
            throw new RuntimeException("Transaction body is missing");
        }

        if (t.getAmount() <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        if (t.getType() == null || t.getType().trim().isEmpty()) {
            throw new RuntimeException("Type is required");
        }

        if (!(t.getType().equalsIgnoreCase("income") ||
              t.getType().equalsIgnoreCase("expense"))) {
            throw new RuntimeException("Type must be 'income' or 'expense'");
        }

        if (t.getCategory() == null || t.getCategory().trim().isEmpty()) {
            throw new RuntimeException("Category is required");
        }

        if (t.getDate() == null) {
            throw new RuntimeException("Date is required");
        }
    }
    }