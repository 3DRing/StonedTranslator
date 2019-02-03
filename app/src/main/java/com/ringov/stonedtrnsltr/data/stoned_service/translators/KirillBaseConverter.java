package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.HashSet;
import java.util.Set;

public class KirillBaseConverter extends BaseConverter {

    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = new HashSet<>();
        vowels.add('а');
        vowels.add('е');
        vowels.add('и');
        vowels.add('о');
        vowels.add('у');
        vowels.add('ы');
        vowels.add('э');
        vowels.add('ю');
        vowels.add('я');
        return vowels;
    }
}
