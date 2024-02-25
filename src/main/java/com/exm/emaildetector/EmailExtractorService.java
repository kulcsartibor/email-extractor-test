package com.exm.emaildetector;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailExtractorService {

    private static final Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");

    /**
     * Extracts emails from the given text
     * @param text the text to extract emails from
     * @return a list of emails found in the text
     */
    public List<String> extractEmailsFromText(String text) {

        if(text == null || text.isEmpty()) {
            return List.of();
        }

        var emailList = new LinkedList<String>();
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            emailList.add(matcher.group());
        }

        return emailList;
    }

}
