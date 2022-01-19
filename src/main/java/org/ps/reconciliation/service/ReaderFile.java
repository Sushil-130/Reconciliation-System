package org.ps.reconciliation.service;


import org.ps.reconciliation.exception.FileReaderException;
import org.ps.reconciliation.model.Transaction;

import java.util.Map;

public interface ReaderFile {

    Map<String, Transaction> getTransactions() throws FileReaderException;

}
