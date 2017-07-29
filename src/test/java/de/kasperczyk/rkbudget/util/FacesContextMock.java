package de.kasperczyk.rkbudget.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class FacesContextMock extends FacesContext {

    public static FacesContext getMock() {
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);
        setCurrentInstance(facesContextMock);
        return facesContextMock;
    }
}
