package com.assignment;

import android.content.SharedPreferences;

import java.io.Serializable;

public class Session implements Serializable {
    /**
    *
    * <p>
     *     Session have following details in .xml file :
     *     <em>user_id</em> : to identify the logged in user.
     *     <em>session_created_at</em> : current login date.
    * </p>
    * @author  DSP
    */
    private SharedPreferences shared_preferences = null;
    private SharedPreferences.Editor editor = null;
    private String user_id, session_created_at;

    public Session(SharedPreferences sp) {
        this.shared_preferences = sp;
        try {
            this.user_id = this.getSessionEntry("user_id");
            this.session_created_at  = this.getSessionEntry("session_start_at");
        }
        catch (RuntimeException e)
        {
            this.session_created_at = null;
            this.user_id = null;
        }
    }

    public String getSessionEntry(String key){
        if(this.shared_preferences.contains(key))
            return this.shared_preferences.getString(key, null).toString();
        else
            throw new RuntimeException("Session has no entry named '" + key + "'.");
    }

    public boolean checkSession_entry(String key) {
        return this.shared_preferences.contains(key);
    }

    public void createUpdateSessionEntry(String key, String value){
        this.editor = this.shared_preferences.edit();
        if(this.shared_preferences.contains(key))
            this.editor.remove(key);
        this.editor.putString(key, value);
        return;
    }

    public void removeSession()
    {
        this.editor = this.shared_preferences.edit();
        this.editor.clear();
        this.editor.commit();
    }
}
