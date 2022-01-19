package org.ps.reconciliation.service;



import org.ps.reconciliation.exception.FileReaderException;
import org.ps.reconciliation.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TransactionImporter {


    private TransactionImporter() {

    }

    public static Map<String, Transaction> readFile(String file, String fileFormat) throws FileReaderException {
        ReaderFile sourceReader = getFileReader(file, fileFormat);
        return sourceReader.getTransactions();
    }

    public static ReaderFile getFileReader(String type, String path) throws FileReaderException {

        if (type.equals("CSV")) {
            return new CsvReader(path);
        }
        if (type.equals("JSON")) {
            return new JsonReader(path);
        }
        throw new FileReaderException("Unrecognized file type" +type);
    }

}
