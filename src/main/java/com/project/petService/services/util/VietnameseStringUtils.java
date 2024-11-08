package com.project.petService.services.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class VietnameseStringUtils {
    public static String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
