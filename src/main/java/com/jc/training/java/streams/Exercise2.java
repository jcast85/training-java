package com.jc.training.java.streams;

import reactor.core.publisher.Flux;

import java.io.IOException;

public class Exercise2 {
    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux() and ReactiveSources.userFlux()

        // Print all numbers in the ReactiveSources.intNumbersFlux stream
        Flux<Integer> integerFlux = ReactiveSources.intNumbersFlux();
        integerFlux//.log()
                .subscribe(System.out::println);
        integerFlux.publish();

        // Print all users in the ReactiveSources.userFlux stream
        Flux<User> userFlux = ReactiveSources.userFlux();
        userFlux//.log()
                .subscribe(System.out::println);
        userFlux.publish();

        System.out.println("Press a key to end");
        System.out.println(System.in.read());
    }
}
