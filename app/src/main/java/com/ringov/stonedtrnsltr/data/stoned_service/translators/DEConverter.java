package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.Set;

public class DEConverter extends LatinBaseConverter {
    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = super.setVowels();
        vowels.add('ä');
        vowels.add('ö');
        vowels.add('ü');
        return vowels;
    }
}
