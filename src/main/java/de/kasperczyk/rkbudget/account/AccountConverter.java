package de.kasperczyk.rkbudget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component
public class AccountConverter implements Converter {

    private final AccountService accountService;

    @Autowired
    public AccountConverter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String iban) {
        return accountService.getAccountByIban(iban);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        if (o == null) {
            return null;
        } else {
            return ((Account)o).getIban();
        }
    }
}
