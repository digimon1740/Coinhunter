package com.coinhunter.utils.common;

import java.net.IDN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private final static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
    private final static String DOMAIN = ATOM + "+(\\." + ATOM + "+)*";
    private final static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
    private final static Pattern localPattern = Pattern.compile(ATOM + "+(\\." + ATOM + "+)*", 2);
    private final static Pattern domainPattern = Pattern.compile(DOMAIN + "|" + IP_DOMAIN, 2);

    public static boolean isValid(CharSequence value) {
        if (value != null && value.length() != 0) {
            String[] emailParts = value.toString().split("@", 3);
            return emailParts.length == 2 && !emailParts[0].endsWith(".")
                && !emailParts[1].endsWith(".")
                && matchPart(emailParts[0], localPattern)
                && matchPart(emailParts[1], domainPattern);
        } else {
            return true;
        }
    }

    private static boolean matchPart(String part, Pattern pattern) {
        try {
            part = IDN.toASCII(part);
        } catch (IllegalArgumentException var4) {
            return false;
        }

        Matcher matcher = pattern.matcher(part);
        return matcher.matches();
    }
}
