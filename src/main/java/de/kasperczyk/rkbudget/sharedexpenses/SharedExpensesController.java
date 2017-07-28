package de.kasperczyk.rkbudget.sharedexpenses;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
@Join(path = "/shared-expenses", to = "/pages/shared-expenses.xhtml")
public class SharedExpensesController {

}
