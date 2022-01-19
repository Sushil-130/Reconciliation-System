package org.ps.reconciliation.service;

import org.ps.reconciliation.model.Transaction;

import java.util.*;

public class TransactionComparison {

    private final String sourceFile;
    private final String targetFile;
    private final Set<String> transactionIds;
    private final List<Transaction> matchingTransactions;
    private final List<Transaction> misMatchTransactions;
    private final List<Transaction> missingTransactions;
    private final Map<String, Transaction> source;
    private final Map<String, Transaction> target;


    public TransactionComparison(String sourceFile,
                                 String targetFile,
                                 Map<String, Transaction> source,
                                 Map<String, Transaction> target) {
        this.source = source;
        this.target = target;
        this.sourceFile = sourceFile;
        this.targetFile = targetFile;
        transactionIds = new TreeSet<>();
        transactionIds.addAll(source.keySet());
        transactionIds.addAll(target.keySet());
        matchingTransactions = new ArrayList<>();
        misMatchTransactions = new ArrayList<>();
        missingTransactions = new ArrayList<>();
    }

    public TransactionOutput compareSourceAndTarget() {
        TransactionOutput transactionOutput = new TransactionOutput();

        transactionOutput.addMissingTransactions(findMissingTransactions());
        transactionOutput.addMismatchTransactions(findMisMatchTransactions());
        transactionOutput.addMatchingTransactions(findMatchingTransactions());

        return transactionOutput;
    }

    private List<Transaction> findMissingTransactions() {
        transactionIds.forEach(id -> {

            if (!source.containsKey(id)) {
                missingTransactions.add(addTransaction(targetFile, target.get(id)));
            }
            if (!target.containsKey(id)) {
                missingTransactions.add(addTransaction(sourceFile, source.get(id)));
            }
        });
        return missingTransactions;
    }

    private List<Transaction> findMisMatchTransactions() {
        transactionIds.forEach(id -> {
            if (source.containsKey(id) && target.containsKey(id)) {
                Transaction csvTransaction = source.get(id);
                Transaction jsTransaction = target.get(id);
                if (!csvTransaction.isSame(jsTransaction)) {
                    misMatchTransactions.add(addTransaction(sourceFile, source.get(id)));
                    misMatchTransactions.add(addTransaction(targetFile, target.get(id)));
                }
            }
        });
        return misMatchTransactions;
    }

    private Transaction addTransaction(String sourceFile, Transaction transaction) {
        Transaction t = new Transaction();
        t.setId(transaction.getId());
        t.setLocation(sourceFile);
        t.setCurrencyCode(transaction.getCurrencyCode());
        t.setAmount(transaction.getAmount());
        t.setTransactionDate(transaction.getTransactionDate());
        return t;
    }

    private List<Transaction> findMatchingTransactions() {
        transactionIds.forEach(id -> {
            if (source.containsKey(id) && target.containsKey(id)) {
                Transaction csvTransaction = source.get(id);
                Transaction jsTransaction = target.get(id);
                if (csvTransaction.isSame(jsTransaction)) {
                    matchingTransactions.add(jsTransaction);
                }
            }
        });
        return matchingTransactions;
    }
}
