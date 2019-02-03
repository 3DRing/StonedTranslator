package com.ringov.stonedtrnsltr.data.stoned_service.translators;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 14.04.2017.
 */
public class LatinBaseConverter extends BaseConverter {

    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('y');
        return vowels;
    }
}
