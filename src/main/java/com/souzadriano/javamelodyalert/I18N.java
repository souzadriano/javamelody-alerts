package com.souzadriano.javamelodyalert;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

final class I18N {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    static String getString(String key) {
        return bundle.getString(key);
    }

    static String getString(String key, Object... params) {
        return MessageFormat.format(bundle.getString(key), params);
    }
}
