package de.kasperczyk.rkbudget.inbox;

import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
@Join(path = "/inbox", to = "/pages/inbox.xhtml")
public class InboxController {

}
