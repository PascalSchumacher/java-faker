package com.github.javafaker;

import com.github.javafaker.service.CountryService;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.reflect.MethodUtils;

import java.util.Locale;
import java.util.Random;

/**
 * Provides utility methods for generating fake strings, such as names, phone
 * numbers, addresses. generate random strings with given patterns
 *
 * @author ren
 */
public class Faker implements Resolver {
    private final RandomService randomService;
    private final FakeValuesService fakeValuesService;
    private final Lorem lorem;
    private final Name name;
    private final Internet internet;
    private final PhoneNumber phoneNumber;
    private final Address address;
    private final Business business;
    private final Company company;
    private final Options options;
    private final Code code;
    private final Finance finance;
    private final CountryService countryService;
    private final DateAndTime dateAndTime;

    public Faker() {
        this(Locale.ENGLISH);
    }

    public Faker(Locale locale) {
        this(locale, null);
    }

    public Faker(Random random) {
        this(Locale.ENGLISH, random);
    }

    public Faker(Locale locale, Random random) {
        this.randomService = new RandomService(random);
        this.fakeValuesService = new FakeValuesService(locale, randomService);
        this.lorem = new Lorem(fakeValuesService, randomService);
        this.name = new Name(fakeValuesService);
        this.internet = new Internet(name, fakeValuesService, randomService);
        this.phoneNumber = new PhoneNumber(fakeValuesService);
        this.address = new Address(this, name, fakeValuesService, randomService);
        this.business = new Business(fakeValuesService);
        this.company = new Company(fakeValuesService);
        this.options = new Options(randomService);
        this.code = new Code(randomService);
        this.finance = new Finance(fakeValuesService, randomService);
        this.countryService = new CountryService(fakeValuesService, randomService);
        this.dateAndTime = new DateAndTime(randomService);
    }

    /**
     * Returns a string with the '#' characters in the parameter replaced with random digits between 0-9 inclusive.
     *
     * For example, the string "ABC##EFG" could be replaced with a string like "ABC99EFG".
     *
     * @param numberString
     * @return
     */
    public String numerify(String numberString) {
        return fakeValuesService.numerify(numberString);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * 
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     *
     * @param letterString
     * @return
     */
    public String letterify(String letterString) {
        return fakeValuesService.letterify(letterString);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     *
     * @param string
     * @return
     */
    public String bothify(String string) {
        return fakeValuesService.bothify(string);
    }

    public Name name() {
        return name;
    }

    public Internet internet() {
        return internet;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public Lorem lorem() {
        return lorem;
    }

    public Address address() {
        return address;
    }
    
    public Country country() {
        return countryService.country();
    }

    public Business business() {
        return business;
    }

    public Company company() {
        return company;
    }

    public Options options() {
        return options;
    }

    public Code code() {
        return code;
    }

    public Finance finance() { return finance; }

    public DateAndTime date() {
        return dateAndTime;
    }

    /**
     * Resolves a key in the format of class.method_name
     *
     * @param key
     * @return
     */
    public String resolve(String key) {
        String[] keySplit = key.split("\\.", 2);
        String object = keySplit[0].toLowerCase();
        String methodName = keySplit[1];

        char[] METHOD_NAME_REPLACEMENT = {'_'};
        methodName = WordUtils.capitalizeFully(methodName, METHOD_NAME_REPLACEMENT).replaceAll("_", "");
        methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
        try {
            Object objectWithMethodToInvoke = MethodUtils.invokeMethod(this, object, null);
            String value = (String) MethodUtils.invokeMethod(objectWithMethodToInvoke, methodName, null);
            System.out.println("resolving " + key + " to " + value);
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
