package com.github.javafaker;

import org.junit.Test;

import static com.github.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.junit.Assert.assertThat;

public class FoodTest extends AbstractFakerTest {

    @Test
    public void ingredient() {
        for (int i=0;i < 100;i++) {
            assertThat(faker.food().ingredient(), matchesRegularExpression("[A-Za-z ]+"));
        }
    }

    @Test
    public void spice() {
        for (int i=0;i < 100;i++) {
            assertThat(faker.food().spice(), matchesRegularExpression("[A-Za-z1-9- ]+"));
        }
    }

    @Test
    public void measurement() {
        for (int i=0;i < 100;i++) {
            assertThat(faker.food().measurement(), matchesRegularExpression("[A-Za-z1-9/ ]+{2}"));
        }
    }
}
