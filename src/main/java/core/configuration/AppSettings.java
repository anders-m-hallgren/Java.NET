package core.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class AppSettings {

    static void readAppAsettings(StringBuilder builder, String uriPath) {
        try {
            File file = new File(uriPath);
            var url = file.toURI().toURL();
            //var urlInputStream = url.openConnection().getInputStream();
            readFile(builder, url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void readFile(StringBuilder builder, InputStream in) throws IOException {

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            builder.append(inputStr);

        new JSONObject(builder.toString());

    }
}
