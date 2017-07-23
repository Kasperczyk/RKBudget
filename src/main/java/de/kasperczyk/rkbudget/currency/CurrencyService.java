package de.kasperczyk.rkbudget.currency;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {

    List<Currency> getAllSupportedCurrencies() {
        return Arrays.asList(Currency.values());
    }

    Currency getCurrencyByName(String name) {
        return Currency.valueOf(name);
    }
}
