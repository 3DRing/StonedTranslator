package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.Set;

/**
 * Created by Сергей on 14.04.2017.
 */
public class RUConverter extends KirillBaseConverter {

    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = super.setVowels();
        vowels.add('ё');
        vowels.add('э');
        return vowels;
    }

}
