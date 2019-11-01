package se.clouds.app.javanet.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeatherForecast {
    public Date Date;
    public int TemperatureC;
    public int TemperatureF;
    public String Summary;

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public int getTemperatureC() {
        return 32 + (int)(TemperatureC / 0.5556);
    }

    public int getTemperatureF() {
        return TemperatureC;
    }

    public void setTemperatureC(int temperatureC) {
        TemperatureC = temperatureC;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public WeatherForecast(int temperatureC, String summary) {
        Date = new Date();
        TemperatureC = temperatureC;
        Summary = summary;
    }

    public Map<String, Object> AsMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", this.Date);
        map.put("temperatureC", TemperatureC);
        map.put("summary", Summary);
        return map;
    }
}
