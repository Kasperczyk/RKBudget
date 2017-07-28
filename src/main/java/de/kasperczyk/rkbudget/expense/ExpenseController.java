package de.kasperczyk.rkbudget.expense;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
@Join(path = "/expenses", to = "/pages/expenses.xhtml")
public class ExpenseController {

}
