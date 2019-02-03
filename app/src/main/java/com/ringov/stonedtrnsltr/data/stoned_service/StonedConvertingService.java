package com.ringov.stonedtrnsltr.data.stoned_service;

import com.ringov.stonedtrnsltr.data.lang.Language;
import com.ringov.stonedtrnsltr.translation_module.entities.TranslationData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 14.04.2017.
 */
public class StonedConvertingService {

    public static String convert(String string, Language lang) {
        return ConverterFactory.getConverter(lang).convert(string);
    }

    public static TranslationData convert(TranslationData translation) {
        // converting source lang
        String convertedOriginalText = convert(translation.getOriginal(), translation.getLangPair().getSourceLang());

        // converting translations
        StonedConverter converter = ConverterFactory.getConverter(translation.getLangPair().getTargetLang());
        List<String> convertedTranslations = new ArrayList<>();

        for (String s :
                translation.getTranslations()) {
            convertedTranslations.add(converter.convert(s));
        }
        return new TranslationData(convertedOriginalText, convertedTranslations, translation.getLangPair());
    }

}
