package se.clouds.javanet.app.domain.model;

import java.util.HashMap;
import java.util.Map;

public class SharedFeature
{
    public String Name;
    public String Value;
    public boolean Activated;
    public String Description;

    public SharedFeature(String name, boolean activated, String value, String description) {
        Name = name;
        Activated = activated;
        Value = value;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isActivated() {
        return Activated;
    }

    public void setActivated(boolean activated) {
        Activated = activated;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Map<String, Object> AsMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", Name);
        map.put("activated", Activated);
        map.put("value", Value);
        map.put("description", Description);
        return map;
    }
    public static SharedFeature FromMap(Map<String, Object> map)
    {
        return new SharedFeature(
            (String)map.get("name"),
            (boolean)map.get("activated"),
            (String)map.get("value"),
            (String)map.get("description"));
    }
}
