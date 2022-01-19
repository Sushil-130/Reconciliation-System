package org.ps.reconciliation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Currency;

public class Transaction {

    private String id;

    @JsonIgnore
    private String type;
    private Currency currencyCode;
    private double amount;
    private LocalDate transactionDate;
    @JsonIgnore
    private String location;
    @JsonIgnore
    private String purpose;
    @JsonIgnore
    private String description;

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrencyCode(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getLocation() {
        return location;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", transDescription='" + description + '\'' +
                ", amount=" + amount +
                ", currency='" + currencyCode + '\'' +
                ", purpose='" + purpose + '\'' +
                ", valueDate=" + transactionDate +
                ", transType='" + type + '\'' +
                '}';
    }

    public boolean isSame(Transaction transaction) {
        if (!id.equals(transaction.id))
            return false;
        if (amount != transaction.amount)
            return false;
        if (!currencyCode.equals(transaction.currencyCode))
            return false;
        return transactionDate.compareTo(transaction.transactionDate) == 0;
    }

}
