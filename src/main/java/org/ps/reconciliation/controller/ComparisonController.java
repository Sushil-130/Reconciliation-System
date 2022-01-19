package org.ps.reconciliation.controller;

import org.ps.reconciliation.exception.FileReaderException;
import org.ps.reconciliation.model.Transaction;
import org.ps.reconciliation.model.User;
import org.ps.reconciliation.model.UserDetails;
import org.ps.reconciliation.repository.UserDetailsRepository;
import org.ps.reconciliation.repository.UserRepository;
import org.ps.reconciliation.service.FileService;
import org.ps.reconciliation.service.TransactionComparison;
import org.ps.reconciliation.service.TransactionImporter;
import org.ps.reconciliation.service.TransactionOutput;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rc")
public class ComparisonController {

    private final FileService fileService;
    private final TransactionImporter transactionImporter;
    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;

    public ComparisonController(FileService fileService,
                                TransactionImporter transactionImporter,
                                UserRepository userRepository,
                                UserDetailsRepository userDetailsRepository) {
        this.fileService = fileService;
        this.transactionImporter = transactionImporter;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @PostMapping(value = "/compare")
    public TransactionOutput getResult(
            @RequestParam String sourceFileName,
            @RequestParam String sourceFileType,
            @RequestParam MultipartFile sourceFile,
            @RequestParam String targetFileName,
            @RequestParam String targetFileType,
            @RequestParam MultipartFile targetFile
    ) throws IOException, FileReaderException {

        String sourceFilePath = fileService.save(sourceFile, sourceFileName);
        String targetFilePath = fileService.save(targetFile, targetFileName);

        Map<String, Transaction> sourceTransactions = transactionImporter.readFile(sourceFileType, sourceFilePath);
        Map<String, Transaction> targetTransactions = transactionImporter.readFile(targetFileType, targetFilePath);

        TransactionComparison transactionComparison = new TransactionComparison(sourceFileName, targetFileName,
                sourceTransactions, targetTransactions);

        TransactionOutput output = transactionComparison.compareSourceAndTarget();

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(loggedInUser.getName());
        UserDetails userDetails = new UserDetails();
        userDetails.setCreatedDate(Instant.now());
        userDetails.setSourceFileType(sourceFileType);
        userDetails.setSourceName(sourceFileName);
        userDetails.setTargetFileType(targetFileType);
        userDetails.setTargetName(targetFileName);

        userDetails.setUser(user);
        userDetailsRepository.save(userDetails);

        return output;
    }

    @GetMapping("/history")
    public List<UserDetails> getAllUsersDetails() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(loggedInUser.getName());
        return user.getUserDetails();
    }
}
