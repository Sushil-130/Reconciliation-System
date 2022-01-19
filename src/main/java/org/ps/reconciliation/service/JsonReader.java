package org.ps.reconciliation.service;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.ps.reconciliation.exception.FileReaderException;
import org.ps.reconciliation.model.Transaction;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class JsonReader implements ReaderFile {

    private static final JSONParser parser = new JSONParser();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final String jsonFile;

    public JsonReader(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public Map<String, Transaction> getTransactions() throws FileReaderException {
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(jsonFile));
            List<Transaction> transactions = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject person = (JSONObject) o;
                String date = (String) person.get("date");
                String reference = (String) person.get("reference");
                String amount = (String) person.get("amount");
                String currencyCode = (String) person.get("currencyCode");
                String purpose = (String) person.get("purpose");
                Transaction transaction = new Transaction();
                transaction.setId(reference);
                transaction.setTransactionDate(LocalDate.parse(date, formatter));
                transaction.setAmount(Double.parseDouble(amount));
                transaction.setCurrencyCode(Currency.getInstance(currencyCode));
                transaction.setPurpose(purpose);
                transactions.add(transaction);
            }
            return transactions.stream()
                    .collect(toMap(Transaction::getId, Function.identity()));
        } catch (Exception e) {
            throw new FileReaderException("unable to read json file");
        }
    }

}

