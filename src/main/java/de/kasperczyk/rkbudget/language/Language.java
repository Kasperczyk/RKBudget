package de.kasperczyk.rkbudget.language;

public enum Language {

    ENGLISH("en"),
    GERMAN("de");
//    SPANISH("es"),
//    DUTCH("nl");

    private static final String MESSAGE_PREFIX = "language_supportedLanguage_";

    private String countryCode;

    Language(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getMessageKey() {
        return MESSAGE_PREFIX + countryCode;
    }
}
