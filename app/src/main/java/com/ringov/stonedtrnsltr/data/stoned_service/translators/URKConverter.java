package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.Set;

public class URKConverter extends KirillBaseConverter {
    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = super.setVowels();
        vowels.add('є');
        vowels.add('і');
        vowels.add('ї');
        return vowels;
    }
}
