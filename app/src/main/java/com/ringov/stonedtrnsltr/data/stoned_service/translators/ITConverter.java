package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.Set;

public class ITConverter extends LatinBaseConverter {

    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = super.setVowels();
        vowels.add('à');
        vowels.add('é');
        vowels.add('è');
        vowels.add('ì');
        vowels.add('í');
        vowels.add('ò');
        vowels.add('î');
        vowels.add('ó');
        vowels.add('ù');
        vowels.add('ú');
        return vowels;
    }

}
