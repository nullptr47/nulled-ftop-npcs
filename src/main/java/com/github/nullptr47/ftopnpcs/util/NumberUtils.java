package com.github.nullptr47.ftopnpcs.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

    public static String[] symbols = {"", "K", "M", "B", "T", "Q", "QQ"};

    /**
     *
     * @param value value to be formatted
     * @return value formatted (2.000.000)
     */
    public static String numberFormat(double value) {

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));

        numberFormat.setMaximumFractionDigits(1);


        return numberFormat.format(value);

    }

    /**
     *
     * @param valor value to be formatted
     * @return value formatted (2 Milhões)
     */
    public static String numberKFormat(double valor) {

        int index;

        for (index = 0; valor / 999.9 >= 1.0; valor /= 999.9, ++index);

        DecimalFormat decimalFormat = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR")));

        return decimalFormat.format(valor) + symbols[index];

    }

    /**
     *
     * @param string string to be parsed
     * @return string parsed to integer
     */
    public static Integer integerParser(String string) {

        try { return Integer.parseInt(string); }

        catch (Exception exception) { return null; }

    }

    /**
     *
     * @param string string to be parsed
     * @return string parsed to double
     */
    public static Double doubleParser(String string) {

        try { return Double.parseDouble(string); }

        catch (Exception exception) { return null; }

    }

}
