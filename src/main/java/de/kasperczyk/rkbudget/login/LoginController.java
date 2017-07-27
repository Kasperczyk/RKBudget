package de.kasperczyk.rkbudget.login;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
@Join(path = "/login", to = "/pages/entry/login.xhtml")
public class LoginController {
}
