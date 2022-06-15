package com.frank;

public class FinalImmutabilityApplicationProgram {

        private  static int nums[] = {1, 2, 3, 4};

        public static void main(String[] args) {
                System.out.println("Hello from main()");

                showArray();

                aClass anObject = new aClass(nums);

                anObject.showClass();
        }

        public static void showArray() {
                System.out.println(("\nContents of array in main(): "));
                for (int i = 0; i < nums.length; i++) {
                        System.out.println("Element " + i + ": " + nums[i]);
                }
        }

}
