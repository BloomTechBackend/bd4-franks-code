package com.amazon.ata.introthreads.classroom;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to hash a batch of passwords in a separate thread.
 * Add implements Runnable interface rather than extends Thread
 * Allows this class to be run on separate threads
 */
public class BatchPasswordHasher implements Runnable{

    // Note: final attribute is used on member data to make it immutable as required for concurrency
    //       (concurrency multiple instances of the same process running on separate threads at the same time)
    private final List<String> passwords;               // passwords to be hashed - parameter to ctor
    private final Map<String, String> passwordToHashes; // Contains the hashed passwords
    private final String salt;                          // salt value to be used in hashing the passwords

    // ctor receive a List of passwords and a salt
    public BatchPasswordHasher(List<String> passwords, String salt) {
        //this.passwords = passwords;  // replaced by defensive copy for immutability
        this.passwords = new ArrayList<>(passwords);  // defensive copy of an object
        this.salt = salt;
        passwordToHashes = new HashMap<>();
    }

    /**
     *  Hashes all of the passwords, and stores the hashes in the passwordToHashes Map.
     *  Called from run() method when the thread is started
     */
    public void hashPasswords() {
        try {
            for (String password : passwords) {
                final String hash = PasswordUtil.hash(password, salt);
                passwordToHashes.put(password, hash);
            }
            System.out.println(String.format("Completed hashing batch of %d passwords.", passwords.size()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Returns a map where the key is a plain text password and the key is the hashed version of the plaintext password
     * and the class' salt value.
     *
     * @return passwordToHashes - a map of passwords to their hash value.
     */
    public Map<String, String> getPasswordToHashes() {
        return passwordToHashes;
    }

    // run() is required by the Runnable interface
    // run() is automatically called when the thread assigned to this class is started
    //       like main() for a Java app or handleRequest() for AWS Lambda function
    @Override
    public void run() {
        hashPasswords(); // call the method in this class to hash the passwords
    }
}
