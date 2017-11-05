package com.tuhin.weathora.app;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

public class AppBackupAgent  extends BackupAgentHelper {
    private static final String BACKUP_KEY = "weathoraprefs";

    @Override
    public void onCreate(){
        // This is the preference name used by PreferenceManager.getDefaultSharedPreferences
        String prefs = getPackageName() + "_weathorapreferences";
        addHelper(BACKUP_KEY, new SharedPreferencesBackupHelper(this, prefs));
    }
}
