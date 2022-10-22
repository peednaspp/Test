package com.test.utils;

/**
 * Custom util methods / operations on string.
 */
public class StringUtils {

    public static String reverseWords(String str) {

        // initialize return string with full capacity
        StringBuilder output = new StringBuilder(str.length());

        int curWordStart = 0;

        // scan the input string character by character.
        for (int i = 0; i < str.length(); i++) {
            char curLetter = str.charAt(i);
            if (!Character.isAlphabetic(curLetter) && !Character.isDigit(curLetter)) {
                // the current word has ended--output it in reverse
                for (int j = i-1; j >= curWordStart; j--) {
                    output.append(str.charAt(j));
                }
                // output the current letter
                output.append(curLetter);
                // the next current word starts just after the current letter
                curWordStart = i + 1;
            }
        }
        // The last current word (if any) ends with the end of string,
        // not a special character, so add it (reversed) as well to output
        for (int j = str.length() - 1; j >= curWordStart; j--) {
            output.append(str.charAt(j));
        }

        return output.toString();
    }
}
