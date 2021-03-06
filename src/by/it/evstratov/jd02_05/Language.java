package by.it.evstratov.jd02_05;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Language {

    INSTANCE;

    private final String BASE = "by.it.evstratov.jd02_05.resources.language";
    ResourceBundle bundle;

    Language() {
        setLocale(Locale.getDefault());
    }

    final void setLocale(Locale locale){
        this.bundle = ResourceBundle.getBundle(BASE, locale);
    }

    String get(String key){
        return bundle.getString(key);
    }

    Locale getLocale(){
        return bundle.getLocale();
    }
}
