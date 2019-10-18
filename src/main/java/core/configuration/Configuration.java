package core.configuration;

import org.json.JSONObject;

public class Configuration implements IConfiguration {
    private static JSONObject _configuration;

    public static String Get(String key)
    {
        return (String)_configuration.get(key);
    }

    public void SetupConfiguration() {
        StringBuilder builder = new StringBuilder();
        AppSettings.readAppAsettings(builder, "appsettings.Development.json");
        System.out.println(builder.toString());
        _configuration = new JSONObject(builder.toString());
    }
}
