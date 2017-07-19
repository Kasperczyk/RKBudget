package de.kasperczyk.rkbudget.user;

public enum Currency {

    EURO("€", "euro"),
    US_DOLLAR("$", "dollar");

    private static final String MESSAGE_PREFIX = "user_supportedCurrency_";

    private String sign;
    private String code;

    Currency(String sign, String code) {
        this.sign = sign;
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public String getMessageKey() {
        return MESSAGE_PREFIX + code;
    }
}
