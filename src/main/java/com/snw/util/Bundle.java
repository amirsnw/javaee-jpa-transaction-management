package com.snw.util;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Annotating as alternative to prevent from auto instantiating.
 */
public class Bundle {

    private static final ResourceBundle NONEXISTENT_BUNDLE = new ResourceBundle() {
        public Enumeration<String> getKeys() {
            return null;
        }

        protected Object handleGetObject(String key) {
            return null;
        }

        public String toString() {
            return "NONEXISTENT_BUNDLE";
        }
    };
    private final ResourceBundle bundle;

    public Bundle() {
        this.bundle = NONEXISTENT_BUNDLE;
    }

    // TODO: remove these constructors so only ResourceBundle
    // one remains
    public Bundle(String bundlePath, Locale locale) {
        this.bundle = ResourceBundle.getBundle(bundlePath, locale);
    }

    public Bundle(String bundlePath) {
        this.bundle = ResourceBundle.getBundle(bundlePath);
    }

    public Bundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }

    public String getProperty(String key, Object... params) {
        return MessageFormat.format(getProperty(key), params);
    }

    public boolean contains(String key) {
        return bundle.containsKey(key);
    }
}
