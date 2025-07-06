package de.zettsystems.netzfilm.user.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    // Zeichenvorrat: Groß‑, Kleinbuchstaben, Ziffern und Sonderzeichen
    private static final String UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER   = UPPER.toLowerCase();
    private static final String DIGITS  = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_+=<>?";

    private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;
    private static final SecureRandom rnd = new SecureRandom();

    public static String generate(int length) {
        if (length < 4) throw new IllegalArgumentException("Länge mindestens 4 für alle Zeichentypen");

        List<Character> pwdChars = new ArrayList<>();
        // je ein Zeichen aus jeder Kategorie
        pwdChars.add(UPPER.charAt(rnd.nextInt(UPPER.length())));
        pwdChars.add(LOWER.charAt(rnd.nextInt(LOWER.length())));
        pwdChars.add(DIGITS.charAt(rnd.nextInt(DIGITS.length())));
        pwdChars.add(SYMBOLS.charAt(rnd.nextInt(SYMBOLS.length())));

        // Rest zufällig aus dem kompletten Zeichenvorrat
        for (int i = 4; i < length; i++) {
            pwdChars.add(ALL.charAt(rnd.nextInt(ALL.length())));
        }

        // Liste mischen und dann als String zurückgeben
        Collections.shuffle(pwdChars, rnd);
        StringBuilder sb = new StringBuilder(length);
        pwdChars.forEach(sb::append);
        return sb.toString();
    }

}
