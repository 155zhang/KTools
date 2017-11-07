package com.jiangkang.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.MainThread;

import com.jiangkang.tools.King;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author jiangkang
 * @date 2017/9/24
 *
 * SharedPreference 工具类
 */

public class SpUtils {


    private SharedPreferences preferences;

    private static final String DEFAULT_PREF_NAME = "ktools_pref";

    private SharedPreferences.Editor editor;


    public static SpUtils getInstance(Context context,String name){
        return new SpUtils(context,name);
    }


    private SpUtils(Context context, String name) {
        preferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public <T> SpUtils put(String key, T value){
        if (value instanceof String){
            editor.putString(key, (String) value);
        }else if (value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if (value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if (value instanceof Long){
            editor.putLong(key, (Long) value);
        }else if (value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else {
            throw new IllegalArgumentException("value can not support");
        }
        return this;
    }

    public SpUtils putString(String key, String value){
        editor.putString(key,value).commit();
        return this;
    }


    public SpUtils putBoolean(String key, boolean value){
        editor.putBoolean(key, value).commit();
        return this;
    }


    public SpUtils putInt(String key, int value){
        editor.putInt(key,value).commit();
        return this;
    }


    public SpUtils putFloat(String key, float value){
        editor.putFloat(key, value).commit();
        return this;
    }

    public SpUtils putLong(String key, long value){
        editor.putLong(key, value).commit();
        return this;
    }


    public SpUtils putJsonObject(String key, JSONObject object){
        editor.putString(key,object.toString());
        return this;
    }


    public JSONObject getJsonObject(String key){
        String jsonString = preferences.getString(key,"{}");
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            return null;
        }
    }


    public String getString(String key,String defaultValue){
        return preferences.getString(key,defaultValue);
    }


    public boolean getBoolean(String key, boolean defaultValue){
        return preferences.getBoolean(key, defaultValue);
    }


    public int getInt(String key, int defaultValue){
        return preferences.getInt(key, defaultValue);
    }


    public long getLong(String key, long defaultValue){
        return preferences.getLong(key, defaultValue);
    }


    public float getFloat(String key,float defaultValue){
        return preferences.getFloat(key, defaultValue);
    }


    public Map<String,?> getAll(){
        return preferences.getAll();
    }


}
