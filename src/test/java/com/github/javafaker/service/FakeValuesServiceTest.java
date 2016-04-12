package com.github.javafaker.service;

import java.util.Arrays;
import java.util.Locale;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class FakeValuesServiceTest {

    @Mock
    private RandomService randomService;
    private FakeValuesService fakeValuesService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        // always return the first element
        when(randomService.nextInt(anyInt())).thenReturn(0);
        fakeValuesService = new FakeValuesService(new Locale("test"), randomService);
    }

    @Test(expected = LocaleDoesNotExistException.class)
    public void localeShouldThrowException() {
        new FakeValuesService(new Locale("Does not exist"), randomService);
    }

    @Test
    public void fetchStringShouldReturnValue() {
        assertThat(fakeValuesService.fetchString("property.dummy"), is("x"));
    }

    @Test
    public void fetchShouldReturnValue() {
        assertThat(fakeValuesService.fetch("property.dummy"), Is.<Object>is("x"));
    }

    @Test
    public void fetchObjectShouldReturnValue() {
        assertThat(fakeValuesService.fetchObject("property.dummy"), Is.<Object>is(Arrays.asList("x", "y", "z")));
    }

    @Test
    public void safeFetchShouldReturnValueInList() {
        assertThat(fakeValuesService.safeFetch("property.dummy"), is("x"));
    }

    @Test
    public void safeFetchShouldReturnSimpleList() {
        assertThat(fakeValuesService.safeFetch("property.simple"), is("hello"));
    }

    @Test
    public void safeFetchShouldReturnEmptyStringWhenPropertyDoesntExist() {
        assertThat(fakeValuesService.safeFetch("property.dummy2"), isEmptyString());
    }

    private static class DummyService {
        public String firstName() {
            return "John";
        }

        public String lastName() {
            return "Smith";
        }
    }

    private static class AnotherDummyService {
        public String firstName() {
            return "John";
        }
    }
}
