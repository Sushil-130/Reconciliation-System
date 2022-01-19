package org.ps.reconciliation.service;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.ps.reconciliation.exception.FileReaderException;
import org.ps.reconciliation.model.Transaction;
import org.ps.reconciliation.util.AmountConverter;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class CsvReader implements ReaderFile {

    private final String csvFile;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String TRANSACTION_ID = "trans unique id";
    private static final String TRANSACTION_DESCRIPTION = "trans description";
    private static final String TRANSACTION_AMOUNT = "amount";
    private static final String TRANSACTION_CURRENCYCODE = "currency";
    private static final String TRANSACTION_PURPOSE = "purpose";
    private static final String TRANSACTION_DATE = "value date";
    private static final String TRANSACTION_TYPE = "trans type";
    private static final String[] FILE_HEADER_MAPPING = {TRANSACTION_ID, TRANSACTION_DESCRIPTION, TRANSACTION_AMOUNT,
            TRANSACTION_CURRENCYCODE, TRANSACTION_PURPOSE, TRANSACTION_DATE,
            TRANSACTION_TYPE};
    private static final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

    public CsvReader(String csvFile) {
        this.csvFile = csvFile;
    }

    public Map<String, Transaction> getTransactions() throws FileReaderException {
        List<Transaction> transactions = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(new FileReader(csvFile), csvFileFormat)) {
            List<CSVRecord> csvRecords = csvParser.getRecords();
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
                Transaction transaction = new Transaction();
                transaction.setId(record.get(TRANSACTION_ID));
                transaction.setDescription(record.get(TRANSACTION_DESCRIPTION));
                transaction.setAmount(
                        Double.parseDouble(record.get(TRANSACTION_AMOUNT)));

                transaction.setCurrencyCode(Currency.getInstance(record.get(TRANSACTION_CURRENCYCODE)));
                transaction.setPurpose(record.get(TRANSACTION_PURPOSE));
                transaction.setTransactionDate(LocalDate.parse(record.get(TRANSACTION_DATE), formatter));
                transaction.setType(record.get(TRANSACTION_TYPE));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            throw new FileReaderException("unable to read csv file");
        }
        return transactions.stream()
                .collect(toMap(Transaction::getId, Function.identity()));
    }

}

