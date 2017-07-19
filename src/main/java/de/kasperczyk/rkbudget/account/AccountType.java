package de.kasperczyk.rkbudget.account;

public enum AccountType {

    GIRO("giro"),
    SAVINGS("savings"),
    CREDIT("credit"),
    CASH("cash"),
    CUSTOM("custom");

    private static final String MESSAGE_PREFIX = "account_accountType_";

    private String key;

    AccountType(String key) {
        this.key = key;
    }

    public String getMessageKey() {
        return MESSAGE_PREFIX + key;
    }
}
