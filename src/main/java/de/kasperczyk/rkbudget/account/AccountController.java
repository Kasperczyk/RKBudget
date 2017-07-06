package de.kasperczyk.rkbudget.account;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class AccountController {

    private List<Account> accounts;

    public AccountController() {
        accounts = new ArrayList<>();
    }
}
