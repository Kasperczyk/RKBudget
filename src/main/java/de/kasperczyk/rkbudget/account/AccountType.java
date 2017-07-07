package de.kasperczyk.rkbudget.account;

public enum AccountType {

    GIRO_ACCOUNT("giro");

    private static final String MESSAGE_PREFIX = "account_accountType_";

    private String key;

    AccountType(String key) {
        this.key = key;
    }

    public String getKey() {
        return MESSAGE_PREFIX + key;
    }
}
