package com.bloomtech.catsanddogs.controllers;

import com.bloomtech.catsanddogs.repositories.CatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController  // Tell the server there are controllers in this file to handle URLs it receives
public class CatController
{
    private CatRepository catRepo = new CatRepository();

    // Tell the server when you receive a GET request with the path /cats, run this method
    @GetMapping(value = "/cats", produces = {"application/json"})
    public ResponseEntity<?> findAllDogs()  // the name o ft his method doesn't matter - not used anywhere
    {
        System.out.println("GET with the path /cats was received");
        return new ResponseEntity<>(catRepo.getCats(), HttpStatus.OK);
    }
}
