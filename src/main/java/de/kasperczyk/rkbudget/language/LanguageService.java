package de.kasperczyk.rkbudget.language;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
class LanguageService {

    List<Language> getAllSupportedLanguages() {
        return Arrays.asList(Language.values());
    }

    Language getLanguageByName(String name) {
        return Language.valueOf(name);
    }
}
