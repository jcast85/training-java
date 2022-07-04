package com.jc.training.java.streams;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.ListAssert;
import reactor.core.scheduler.Schedulers;

public class ExerciseOnGenericsTest {
    @Test
    public void test() {
        List<Integer> elementList = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elementList::add);
        ListAssert.assertThatList(elementList).containsExactly(1, 2, 3, 4);
    }
    @Test
    public void test1() {
        List<Integer> elementList = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elementList.add(integer);
                        System.out.println("onNext(" + integer + ")");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
        ListAssert.assertThatList(elementList).containsExactly(1, 2, 3, 4);
    }

    @Test
    public void test2() {
        List<Integer> elementList = new ArrayList<>();
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(4);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elementList.add(integer);
                        onNextAmount++;
                        if (onNextAmount % 4 == 0) {
                            s.request(4);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {}

                    @Override
                    public void onComplete() {}
                });
        ListAssert.assertThatList(elementList).containsExactly(1, 2, 3, 4, 5, 6, 7, 8);
    }
    @Test
    public void test3() {
        List<Integer> elementList = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> {
                    System.out.println(i);
                    System.out.println(elementList.size());
                    return i * 2;
                })
                .subscribe(elementList::add);
        ListAssert.assertThatList(elementList).containsExactly(2, 4, 6, 8);
    }

    @Test
    public void test4() {
        List<String> elementList = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .zipWith(Flux.range(0, 5),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(elementList::add);

        ListAssert.assertThatList(elementList).containsExactly(
                "First Flux: 2, Second Flux: 0",
                "First Flux: 4, Second Flux: 1",
                "First Flux: 6, Second Flux: 2",
                "First Flux: 8, Second Flux: 3");
    }

    @Test
    public void test5() {
        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
                    while(true) {
                        fluxSink.next(System.currentTimeMillis());
                    }
                })
                .sample(Duration.ofSeconds(2))
                .publish();

        publish.subscribe(System.out::println);
        publish.subscribe(System.out::println);

        publish.connect();
    }

    @Test
    public void test6() {
        List<Integer> elementList = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .subscribeOn(Schedulers.parallel())
                .subscribe(elementList::add);
        ListAssert.assertThatList(elementList).containsExactly(2, 4, 6, 8);
    }
}
