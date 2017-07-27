package de.kasperczyk.rkbudget.register;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
@Join(path = "/register", to = "/pages/entry/register.xhtml")
public class RegisterController {
}
