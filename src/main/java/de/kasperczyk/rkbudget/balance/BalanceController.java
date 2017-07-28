package de.kasperczyk.rkbudget.balance;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
@Join(path = "/balance", to = "/pages/balance.xhtml")
public class BalanceController {

}
