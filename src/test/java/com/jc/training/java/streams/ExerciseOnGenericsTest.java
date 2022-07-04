package com.jc.training.java.streams;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.ListAssert;

public class ExerciseOnGenericsTest {
    @Test
    public void test() {
        List<Integer> elementList = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elementList::add);
        ListAssert.assertThatList(elementList).containsExactly(1, 2, 3, 4);
    }
}
