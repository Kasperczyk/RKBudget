package de.kasperczyk.rkbudget.category;

import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@org.springframework.stereotype.Component
public class PredefinedColorConverter implements Converter {

    private final CategoryService categoryService;

    @Autowired
    public PredefinedColorConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String name) {
        return categoryService.getPredefinedColorByName(name);
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
