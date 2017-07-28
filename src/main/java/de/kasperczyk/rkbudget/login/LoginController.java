package de.kasperczyk.rkbudget.login;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserController;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Controller
@Scope("request")
@Join(path = "/login", to = "/pages/login.xhtml")
public class LoginController {

    private final LoginService loginService;
    private final UserController userController;

    private String emailOrUserName;
    private String password;

    @Autowired
    public LoginController(LoginService loginService, UserController userController) {
        this.loginService = loginService;
        this.userController = userController;
    }

    public String login() {
        try {
            User user = loginService.login(emailOrUserName, password);
            userController.setCurrentUser(user);
            userController.initializeFields();
            return "/pages/dashboard?faces-redirect=true";
        } catch (Exception e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage facesMessage = new FacesMessage(e.getMessage());
            facesContext.addMessage(null, facesMessage);
        }
        return "";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/pages/entry/login?faces-redirect=true";
    }

    public String getEmailOrUserName() {
        return emailOrUserName;
    }

    public void setEmailOrUserName(String emailOrUserName) {
        this.emailOrUserName = emailOrUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
