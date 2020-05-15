package com.fresh.forum.dto;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.Map;

public class ViewObject {

    public static ViewObject build(String key, Object value) {
        return new ViewObject().set(key, value);
    }

    private Map<String, Object> objs = new HashMap<>();

    public ViewObject set(String key, Object value) {
        objs.put(key, value);
        return this;
    }

    public Object get(String key) {
        return objs.get(key);
    }

    public Map<String, Object> toMap() {
        return objs;
    }
}
