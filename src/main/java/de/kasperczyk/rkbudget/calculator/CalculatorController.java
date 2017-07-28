package de.kasperczyk.rkbudget.calculator;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
@Join(path = "/calculators", to = "/pages/calculators.xhtml")
public class CalculatorController {

}
