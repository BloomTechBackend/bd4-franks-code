package com.frank;

public class aClass {
        private int[] anArray;

        public aClass(int[] intArray) {
                anArray = intArray;
        }

        public void showClass() {
                System.out.println(("\nContents of array in aClass: "));
                for (int i = 0; i < anArray.length; i++) {
                        System.out.println("Element " + i + ": " + anArray[i]);
                }
        }
}
