/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.troy.markup.utilities;

/**
 *
 * @author Troy
 */
public class AnnotationLetterFactory {

    private static int index = -1;
    private final static int a = 65;
    private final static int numLettersInAlphebet = 26;
    public static char RESET = (char) -1;

    private AnnotationLetterFactory() {

    }

    public static String createLetter() {
        String l;
        index++;
        //If index < Z
        if (index < numLettersInAlphebet) {
            l = Character.toString((char) (index + a));
        } else {
            l = "Z" + ((index + 1) - numLettersInAlphebet);
        }

        return l;
    }

    public static void setCurrentLetter(char letter) {
        //Capitilize letter
        if (letter == (char)-1) {
            index = -1;
        } else {
            String l = Character.toString(letter).toUpperCase();
            index = l.charAt(0) - a;
        }
    }

    public static void setCurrentZPostfixNumber(int num) {
        index = (numLettersInAlphebet - 1) + num;

    }

}
