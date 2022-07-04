package com.jc.training.java.streams;

public class Exercise1 {
    public static void main(String[] args) {
        int exerciseNumber = 0;
        // Use StreamSources.intNumbersStream() and StreamSources.userStream()

        // Print all numbers in the intNumbersStream stream
        System.out.println("#Exercise 1." + (++exerciseNumber));
        StreamSources.intNumbersStream().forEach(System.out::println);

        // Print numbers from intNumbersStream that are less than 5
        System.out.println("#Exercise 1." + (++exerciseNumber));
        StreamSources.intNumbersStream()
                .filter(intValue -> intValue < 5)
                .forEach(System.out::println);

        // Print the second and third numbers in intNumbersStream that's greater than 5
        System.out.println("#Exercise 1." + (++exerciseNumber));
        StreamSources.intNumbersStream()
                .filter(intValue -> intValue > 5)
                .skip(1)
                .limit(2)
                .forEach(System.out::println);

        //  Print the first number in intNumbersStream that's greater than 5.
        //  If nothing is found, print -1
        System.out.println("#Exercise 1." + (++exerciseNumber));
        System.out.println(StreamSources.intNumbersStream()
                .filter(intValue -> intValue > 5)
                .findFirst()
                .orElse(-1));

        // Print first names of all users in userStream
        System.out.println("#Exercise 1." + (++exerciseNumber));
        StreamSources.userStream()
                .map(User::getFirstName)
                .forEach(System.out::println);

        // Print first names in userStream for users that have IDs from number stream
        System.out.println("#Exercise 1." + (++exerciseNumber));
        StreamSources.userStream()
                .filter(user -> StreamSources.intNumbersStream().anyMatch(intValue -> intValue.equals(user.getId())))
                .map(User::getFirstName)
                .forEach(System.out::println);
    }
}
