package de.kasperczyk.rkbudget.dashboard;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
@Join(path = "/dashboard", to = "/pages/dashboard.xhtml")
public class DashboardController {

}
