package com.bd4.spring.boot.myControllers;

import com.bd4.spring.boot.model.Question;
import org.springframework.web.bind.annotation.*;

// Data may be sent with an HTTP request:
//
// GET  or DELETE - data is in  the URL query parameter - ?name=value - @RequestParam
// POST or PUT    - data is in the body of the request - @RequestBody



@RestController // Tell the server there are controllers in this class
public class theControllers {
    // Controller to handle a GET request for the path /silence?name=value
    @GetMapping (value="/silence")
    // @RequestParam will copy the query parameter from the URL named what is specified
    //     to a program variable
    public String anynameYouWantItDoesntMatter(@RequestParam String name) {
        System.out.println("Controller for path /silence was run");
        System.out.println("with the query paramter name set to: " + name);
        return "Silence Speaks Louder THan Words";
    }

    // Controller to handle a GET request for the root path (/ or nothing)
    @GetMapping(value="/")
    public String rootController() {
        System.out.println("Controller for root path was run");
        return "You called the root controller";
    }

    // Controller to handle a POST request with some data in the body of the request
    @PostMapping (value="/")    // handle the root path URL for a POST
    // @RequestBody is how you get data from the request body into a Java object
    // The server will automatically copy the JSON from the request into the Java object
    //            using the standard named setters for the class
    //                                    class    object-name
    public String magic8Ball(@RequestBody Question aQuestion) {
        System.out.println("Controller for root path for a POST was run");
        System.out.println("with the Question: " + aQuestion);
        return "Answer pending";
    }

}
