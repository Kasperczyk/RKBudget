package de.kasperczyk.rkbudget.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component
public class LanguageConverter implements Converter {

    private final LanguageService languageService;

    @Autowired
    public LanguageConverter(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String name) {
        return languageService.getLanguageByName(name);
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
