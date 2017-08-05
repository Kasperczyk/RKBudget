package de.kasperczyk.rkbudget.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import javax.faces.context.FacesContext;
import java.util.Map;

public class ViewScope implements Scope {

    @Override
    public Object get(String key, ObjectFactory<?> objectFactory) {
        if (FacesContext.getCurrentInstance().getViewRoot() != null) {
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            if (viewMap.containsKey(key)) {
                return viewMap.get(key);
            } else {
                Object object = objectFactory.getObject();
                viewMap.put(key, object);
                return object;
            }
        }
        return null;
    }

    @Override
    public Object remove(String key) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(key);
    }

    @Override
    public void registerDestructionCallback(String key, Runnable runnable) {
        // not supported
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
