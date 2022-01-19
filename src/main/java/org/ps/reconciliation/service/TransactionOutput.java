package org.ps.reconciliation.service;

import org.ps.reconciliation.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionOutput {

    private final List<Transaction> matchingTransactions = new ArrayList<>();
    private final List<Transaction> misMatchTransactions = new ArrayList<>();
    private final List<Transaction> missingTransactions = new ArrayList<>();

    public void addMatchingTransactions(List<Transaction> transactions) {
        this.matchingTransactions.addAll(transactions);
    }

    public void addMismatchTransactions(List<Transaction> transactions) {
        this.misMatchTransactions.addAll(transactions);
    }

    public void addMissingTransactions(List<Transaction> transactions) {
        this.missingTransactions.addAll(transactions);
    }

    public List<Transaction> getMatchingTransactions() {
        return matchingTransactions;
    }

    public List<Transaction> getMisMatchTransactions() {
        return misMatchTransactions;
    }

    public List<Transaction> getMissingTransactions() {
        return missingTransactions;
    }

}
