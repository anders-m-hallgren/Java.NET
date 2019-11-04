package se.clouds.app.javanet.core.configuration;

import java.net.UnknownHostException;

import org.json.JSONObject;

public class Configuration implements IConfiguration
{
    private String myDevelopmentHost = "Anders-Mac.local";
    private static JSONObject _configuration;

    public static String Get(String section, String key)
    {
        return _configuration.optJSONObject(section).getString(key);
    }

    public void SetupConfiguration()
    {
        StringBuilder builder = new StringBuilder();
        String hostName=null;

        try {
            hostName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if ( hostName.equals(myDevelopmentHost) )
            AppSettings.readAppAsettings(builder, "appsettings.Development.json");
        else
            AppSettings.readAppAsettings(builder, "appsettings.json");

        System.out.println("--- Configuration loaded:" + builder.toString());
        _configuration = new JSONObject(builder.toString());
    }
}
