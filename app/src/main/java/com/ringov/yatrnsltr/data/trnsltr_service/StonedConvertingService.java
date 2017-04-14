package com.ringov.trnsltr_service;

import com.ringov.LangPairData;
import com.ringov.TranslationData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 14.04.2017.
 */
public class StonedConvertingService {

    public static String convert(String string, String lang) {
        return ConverterFactory.getConverter(lang).convert(string);
    }

    public static TranslationData convert(TranslationData translation, LangPairData langPair) {
        // converting source lang
        String convertedOriginalText = convert(langPair.getSourceLang(), translation.getOriginal());

        // converting translations
        StonedConverter converter = ConverterFactory.getConverter(langPair.getTargetLang());
        List<String> convertedTranslations = new ArrayList<>();

        for (String s :
                translation.getTranslation()) {
            convertedTranslations.add(converter.convert(s));
        }
        return new TranslationData(convertedOriginalText, convertedTranslations);
    }

}
