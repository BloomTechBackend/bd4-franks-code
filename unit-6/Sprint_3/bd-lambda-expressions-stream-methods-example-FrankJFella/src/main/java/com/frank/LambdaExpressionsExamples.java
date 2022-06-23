package com.frank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaExpressionsExamples {

    static List<String> names     = new ArrayList<>();

    static List<Integer> numList  = new ArrayList<>();

    public static void main(String[] args) {
        /***************************************************************************************
         * Perform Initial/Setup processing for the application
         **************************************************************************************/
        setup();      // Perform start of main processing to initialize the names and numList ArrayLists

        System.out.println("\n-------- Display elements in a List ---------");


        // forEach() List method - preferred over stream().forEach()
        //
        // List forEach() uses an Iterator to traverse the data structure so order of data is maintained
        //                you can add or remove from the List in teh Lambda expression without ConcurrentModificationExcecption
        // stream().forEach() - the order of the elements is unpredicatable
        //
        // forEach() will loop through the List one element at a time,
        //      passing the element to the Lambda Expresses with name aName
        // The Lambda expression display the element it is passed
        //
        // Without the forEach:
        //   for(String aName : names) {
        //      System.out.println("Name: " + aName);
        //   }
        names.forEach( (aName) -> {System.out.println("Name: " + aName);});  // List version of forEach()
        names.forEach(  aName  -> {System.out.println("Name: " + aName);});  // only 1 argument to Lambda Expression
                                                                             //      no need for () around argument
        System.out.println("Display using stream().forEach()");
        names.stream().forEach((aName) -> {System.out.println("Name: " + aName);});


        /***************************************************************************************
         * stream().map() Stream Interface function
         **************************************************************************************/
        System.out.println("\n-------- Showing first initials   ---------");

        List<String> firstInitials = names.stream().map((aName) -> {return (aName.substring(0,1));})
                .collect(Collectors.toList());

        firstInitials.forEach( (aName) -> {System.out.println("Name: " + aName);});

        /***************************************************************************************
         * stream().filter() Stream Interface function
         **************************************************************************************/
        System.out.println("\n-------- Removing Frank from List ---------");

        List<String> noFrank = names.stream().filter(aName -> {return (!aName.equals("Frank"));})
                                             .collect(Collectors.toList());

        noFrank.forEach( (aName) -> {System.out.println("Name: " + aName);});

        /***************************************************************************************
         * stream().filter() Stream Interface function
         **************************************************************************************/
        System.out.println("\n-------- Find values in ArrayList evenly divisble by 3 ---------");

        System.out.println("Values in numList ArrayList: ");
        numList.forEach( (aNum) -> {System.out.println("Value in List: " + aNum);});

        List<Integer> divBy3 = numList.stream().filter((aNum) -> {return (aNum % 3 == 0 ? true : false); }).collect(Collectors.toList());

        divBy3.forEach( (aNum) -> {System.out.println("\tnumber divisible by 3: " + aNum);});

        /***************************************************************************************
         * stream().reduce() Stream Interface function
         **************************************************************************************/
        System.out.println("\n-------- Using stream().reduce to sum an ArrayList   ---------");

        System.out.println("Values in numList ArrayList: ");
        numList.forEach( (aNum) -> {System.out.println("Value in List: " + aNum);});
        
        int listTotal = numList.stream().reduce(0, (sum, anElem) -> sum + anElem);
        System.out.println("Sum of elements in ArrayList is: " + listTotal);

        /***************************************************************************************
         * Arrays.reduce() methods can be used with simple array
         **************************************************************************************/
        System.out.println("\n-------- Using Arrays.reduce() to sum an array   ---------");

        int numArray[]  = {10, 20, 30,40};
        for(int i=0; i < numArray.length; i++) {
            System.out.println("Array elem " + i + ": " + numArray[i]);
        }

        // The Arrays class provided a stream().reduce() method for a simple array
        int total = Arrays.stream(numArray).reduce(0, (sum, anElem) -> sum + anElem);
        System.out.println("Sum of elements in array is: " + total);


        takeDown();   // Perform end of main() processings
    } // End of main()

    public static void setup() {

        System.out.println("-".repeat(50) + "\nWelcome to Lambda Expressions Example\n" + "-".repeat(50));

        names.add("Frank");
        names.add("Kirk");
        names.add("Spock");
        names.add("Worf");
        names.add("Data");

        numList.add(100);
        numList.add(200);
        numList.add(300);
        numList.add(400);
        numList.add(500);
        numList.add(600);
    }

    public static void takeDown() {
        System.out.println("-".repeat(50) + "\nThanks for visiting Lambda Expressions Example\n" + "-".repeat(50));
    }

} // End if LambdaExpressionsExample class
