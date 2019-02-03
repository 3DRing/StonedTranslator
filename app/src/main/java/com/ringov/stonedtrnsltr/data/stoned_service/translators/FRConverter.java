package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.Set;

public class FRConverter extends LatinBaseConverter {
    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = super.setVowels();
        vowels.add('é');
        vowels.add('à');
        vowels.add('è');
        vowels.add('ù');
        vowels.add('â');
        vowels.add('ê');
        vowels.add('î');
        vowels.add('ô');
        vowels.add('û');
        vowels.add('ë');
        vowels.add('ï');
        vowels.add('ü');
        vowels.add('ÿ');
        vowels.add('œ');
        return vowels;
    }
}
