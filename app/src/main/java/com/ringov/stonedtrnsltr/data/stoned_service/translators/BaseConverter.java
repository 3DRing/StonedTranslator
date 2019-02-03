package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import com.ringov.stonedtrnsltr.data.stoned_service.StonedConverter;

import java.util.Set;

/**
 * Created by Sergey Koltsov on 17.04.2017.
 */

public abstract class BaseConverter implements StonedConverter {

    Set<Character> vowels;

    BaseConverter() {
        vowels = setVowels();
    }

    abstract Set<Character> setVowels();

    @Override
    public String convert(String string) {
        String[] set = string.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word :
                set) {
            for (int i = 0; i < word.length(); i++) {
                boolean isVowel = vowels.contains(word.charAt(i));
                boolean firstChar = i == 0;
                boolean lastChar = i == word.length() - 1;
                if (!isVowel || firstChar || lastChar) {
                    sb.append(word.charAt(i));
                }
            }
            sb.append(" ");
        }
        return sb.toString();
    }
}
