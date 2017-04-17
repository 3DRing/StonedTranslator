package com.ringov.yatrnsltr.data.stoned_service.translators;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 14.04.2017.
 */
public class RUConverter extends BaseConverter {

    @Override
    Set<Character> setVowels() {
        Set<Character> vowels = new HashSet<>();
        vowels.add('а');
        vowels.add('е');
        vowels.add('ё');
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
