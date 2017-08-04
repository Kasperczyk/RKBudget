package de.kasperczyk.rkbudget.user;

import org.passay.*;
import org.springframework.stereotype.Component;

@Component
class PasswordValidator {

    boolean isStrongPassword(String password) {
        CharacterCharacteristicsRule characterCharacteristicsRule = new CharacterCharacteristicsRule(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        characterCharacteristicsRule.setNumberOfCharacteristics(3);
        LengthRule lengthRule = new LengthRule(10, 128);
        org.passay.PasswordValidator passwordValidator = new org.passay.PasswordValidator(characterCharacteristicsRule, lengthRule);
        RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));
        return ruleResult.isValid();
    }
}
