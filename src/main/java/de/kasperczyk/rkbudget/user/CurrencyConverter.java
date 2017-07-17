package de.kasperczyk.rkbudget.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component
public class CurrencyConverter implements Converter {

    private final UserService userService;

    @Autowired
    public CurrencyConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String name) {
        return userService.getCurrencyByName(name);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        if (o == null) {
            return null;
        } else {
            return o.toString();
        }
    }
}
