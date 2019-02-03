package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.Set;

public class ESConverter extends LatinBaseConverter {
    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = super.setVowels();
        vowels.add('ü');
        return vowels;
    }
}
