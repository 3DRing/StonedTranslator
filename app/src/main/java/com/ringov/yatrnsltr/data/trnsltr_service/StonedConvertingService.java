package com.ringov.yatrnsltr.data.trnsltr_service;

import com.ringov.yatrnsltr.data.lang.Language;
import com.ringov.yatrnsltr.translation_module.entities.LangPairData;
import com.ringov.yatrnsltr.translation_module.entities.TranslationData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 14.04.2017.
 */
public class StonedConvertingService {

    public static String convert(String string, Language lang) {
        return ConverterFactory.getConverter(lang).convert(string);
    }

    public static TranslationData convert(TranslationData translation, LangPairData langPair) {
        // converting source lang
        String convertedOriginalText = convert(translation.getOriginal(), langPair.getSourceLang());

        // converting translations
        StonedConverter converter = ConverterFactory.getConverter(langPair.getTargetLang());
        List<String> convertedTranslations = new ArrayList<>();

        for (String s :
                translation.getTranslations()) {
            convertedTranslations.add(converter.convert(s));
        }
        return new TranslationData(convertedOriginalText, convertedTranslations, langPair);
    }

}
