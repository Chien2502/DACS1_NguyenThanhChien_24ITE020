package dao;

import java.util.prefs.Preferences;

public class PreferencesDAO {
    private static final Preferences PREFS = Preferences.userRoot().node("DormitoryApp");

    public static void saveLogin(String username) {
        PREFS.put("savedUsername", username);
    }

    public static String getSavedLogin() {
        return PREFS.get("savedUsername", "");
    }

    public static void clearSavedLogin() {
        PREFS.remove("savedUsername");
    }
}
