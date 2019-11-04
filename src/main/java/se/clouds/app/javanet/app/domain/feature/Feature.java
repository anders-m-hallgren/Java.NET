package se.clouds.app.javanet.app.domain.feature;

import java.util.HashMap;
import java.util.Map;

public class Feature
{
    public String Name;
    public boolean Activated;
    public String Description;

    public Feature(String name, boolean activated, String description) {
        Name = name;
        Activated = activated;
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
        map.put("description", Description);
        return map;
    }
}
