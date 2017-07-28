package de.kasperczyk.rkbudget.currency;

import java.util.Arrays;
import java.util.List;

public enum Currency {

    EURO("€", "euro",
            "ad", "at", "be", "cy", "ee", "fi", "fr", "de", "gr",
            "ie", "rs", "lv", "lt", "lu", "mt", "mc", "me", "nl",
            "pt", "sm", "sk", "si", "es", "va"),
    US_DOLLAR("$", "dollar",
            "us", "pr", "ec", "sv", "zw", "gu", "vi", "tl", "as",
            "mp", "fm", "pk", "mh");

    private static final String MESSAGE_PREFIX = "currency_supportedCurrency_";

    private String sign;
    private String code;
    private List<String> countriesWithCurrency;

    Currency(String sign, String code, String... countryCodes) {
        this.sign = sign;
        this.code = code;
        this.countriesWithCurrency = Arrays.asList(countryCodes);
    }

    public static Currency valueBy(String countryCode) {
        return Arrays.stream(values())
                .filter(currency -> currency.countriesWithCurrency.contains(countryCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No currency for country with country code " +
                        countryCode + " found"));
    }

    public String getSign() {
        return sign;
    }

    public String getMessageKey() {
        return MESSAGE_PREFIX + code;
    }
}
