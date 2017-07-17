package de.kasperczyk.rkbudget.util;

import javax.faces.context.FacesContext;

import static org.mockito.Mockito.mock;

public abstract class FacesContextMock extends FacesContext {

    public static FacesContext getMock() {
        FacesContext facesContextMock = mock(FacesContext.class);
        setCurrentInstance(facesContextMock);
        return facesContextMock;
    }
}
