////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.model;

import maksim.yarmoshyn.dal.model.interf.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base model for models.
 */
abstract class BaseEntity implements Entity {

    /**
     * Get special symbol.
     *
     * @return Special symbol.
     */
    @NotNull
    @Contract(pure = true)
    final String getSpecialSymbol() {
        return ",";
    }

    /**
     * Get special string.
     *
     * @return Special string.
     */
    @NotNull
    @Contract(pure = true)
    private String getSpecialString() {
        return "&comma;";
    }

    /**
     * Translate special symbols to special string in origin.
     *
     * @param origin String to search special symbols.
     * @return String with translated special symbols.
     */
    @NotNull
    @Contract("null -> !null")
    final String translateSpecialSymbols(final String origin) {
        if (origin == null) {
            return "null";
        }
        String result;
        Matcher sym = Pattern.compile(getSpecialSymbol()).matcher(origin);
        if (sym.find()) {
            result = sym.replaceAll(getSpecialString());
        } else {
            Matcher str = Pattern.compile(getSpecialString()).matcher(origin);
            result = str.replaceAll(getSpecialSymbol());
        }
        return result;
    }
}
