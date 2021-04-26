package com.odde.securetoken;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public static Map<String, String> profiles;

    static
    {
        profiles = new HashMap<>();
        profiles.put("joey", "91");
        profiles.put("mei", "99");
    }

    public static String getPassword(String key)
    {
        return profiles.get(key);
    }
}
