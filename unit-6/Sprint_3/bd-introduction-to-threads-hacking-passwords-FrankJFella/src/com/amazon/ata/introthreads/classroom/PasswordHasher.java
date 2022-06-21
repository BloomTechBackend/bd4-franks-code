package com.amazon.ata.introthreads.classroom;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class to pre-compute hashes for all common passwords to speed up cracking the hacked database.
 *
 * Passwords are downloaded from https://github.com/danielmiessler/SecLists/tree/master/Passwords/Common-Credentials
 */
public class PasswordHasher {
    // should create the file in your workspace directory
    private static final String PASSWORDS_AND_HASHES_FILE = "./passwordsAndHashesOutput.csv";
    private static final String DISCOVERED_SALT = "salt";  // value used to hash passwords
                                                           // the salt is usually not a common word
                                                           // salts are usually random set of chars
                                                           //     64. 256, 128, 512 chars are common

    /**
     * Generates hashes for all of the given passwords.
     *
     * @param passwords List of passwords to hash
     * @return map of password to hash
     * @throws InterruptedException
     */
    public static Map<String, String> generateAllHashes(List<String> passwords) throws InterruptedException {
        // Map returned with the passwords and hashed passwords
        // final added to make it immutable for concurrency
        // Note: Use of Google newConcurrentMap instead of the Java Map
        final Map<String, String> passwordToHashes = Maps.newConcurrentMap(); // Use a thread-safe Map

        // Split the passwords into 4 sublists
        // partition returns a List or Lists
        //                                                  original-list, number-elems-in-a sublist
        List<List<String>> passwordSubLists = Lists.partition(passwords  , passwords.size() / 4);

        // Due to the BatchPasswordHasher being removed from memory when the Thread completes
        //     AND we need the hashed passwords from the BatchPasswordHasher when it's done
        //     we need to store the BatchPasswordHasher so it is not removed from memory when the thread completes
        List<BatchPasswordHasher> theBatchHashers = new ArrayList<>();

        // Since we don't know how long a Thread will run
        // We need to wait for it to complete before we can copy the hashed passwords
        //    out of the BatchPasswordHasher assigned to the thread
        // We have method called waitForThreadsToComplete() with receives a List of threads
        //         you want to wait for completion
        List<Thread> theThreads = new ArrayList<>();  // Hold the Threads we want waitForThreadsToComplete()
                                                      //      to wait

// Replace the single call to BatchPasswordHatcher with one for each sublist on a thread
//        BatchPasswordHasher batchHasher = new BatchPasswordHasher(passwords, DISCOVERED_SALT);
//        batchHasher.hashPasswords();
//        passwordToHashes.putAll(batchHasher.getPasswordToHashes());

        // Loop through the List of sublist and start a thread for each sublist with a BatchPasswordHasher
        for(int i=0; i < passwordSubLists.size(); i++) {
            BatchPasswordHasher batchHasher = new BatchPasswordHasher(passwordSubLists.get(i), DISCOVERED_SALT);
            theBatchHashers.add(batchHasher);  // Remember this instance of BatchPasswordHasher so we can get
                                               //          hashed passwords out of it when itis done
            Thread aThread = new Thread(batchHasher);
            theThreads.add(aThread);  // Add the new Thread to the List of threads we want wait on

            aThread.start();  // Automatically call the run() method one the class assigned to the Thread
        }
        // Now that all threads are created and started, we need to wait for them to all finish
        //     before we copy their hashed passwords to our returned Map
        waitForThreadsToComplete(theThreads);

        // Now that all threads have completed and each BatchPasswordHasher for the thread has thier hashed password
        //     we need to copy their hashed passwords to our returned Map
        // Loop through the List of BatchPasswordHasher and copy their hashed password
        for(BatchPasswordHasher aBatchPasswordHasher : theBatchHashers) {
            passwordToHashes.putAll(aBatchPasswordHasher.getPasswordToHashes());
        }

        return passwordToHashes;  // return the Map containing all the hashed password
    }

    /**
     * Given a List of Threads, this method will not return until all Threads in the List have completed
     * Makes the thread calling this method wait until passed in threads are done executing before proceeding.
     *
     * @param threads to wait on
     * @throws InterruptedException
     */
    public static void waitForThreadsToComplete(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {   // Loop through the List of Therads given
            thread.join();                //      and tell Java to wait until they are all done
                                          // .join() will wait for a Thread to complete
        }
    }

    /**
     * Writes pairs of password and its hash to a file.
     */
    static void writePasswordsAndHashes(Map<String, String> passwordToHashes) {
        File file = new File(PASSWORDS_AND_HASHES_FILE);
        try (
            BufferedWriter writer = Files.newBufferedWriter(file.toPath());
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            for (Map.Entry<String, String> passwordToHash : passwordToHashes.entrySet()) {
                final String password = passwordToHash.getKey();
                final String hash = passwordToHash.getValue();

                csvPrinter.printRecord(password, hash);
            }
            System.out.println("Wrote output of batch hashing to " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
