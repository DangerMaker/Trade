package com.ez08.trade.tools;

public class CommonUtils {

    public static String deleteAllCRLF(String input) {
        return input.replaceAll("((\r\n)|\n)[\\s\t ]*", " ").replaceAll(
                "^((\r\n)|\n)", "");
    }
}
