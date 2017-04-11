package com.ringov.yatrnsltr.data;

/**
 * Created by Sergey Koltsov on 11.04.2017.
 */

public class TranslationRepositoryProvider {
    static TranslationRepository translationRepository;

    public static TranslationRepository getTranslationRepository() {
        if (translationRepository == null) {
            translationRepository = new TranslationRepositoryImpl();
        }
        return translationRepository;
    }

}
