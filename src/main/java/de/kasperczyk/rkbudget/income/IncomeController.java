package de.kasperczyk.rkbudget.income;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
@Join(path = "/income", to = "/pages/income.xhtml")
public class IncomeController {

}
