package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, JSONObject> countrycode;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);
            countrycode = new HashMap<String, JSONObject>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                countrycode.put(country.optString("alpha3"), country);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        ArrayList<String> result = new ArrayList<>();

        JSONObject temp = countrycode.get(country);
        for (String key: temp.keySet()) {
            if (!("alpha2".equals(key) || ("alpha3".equals(key)) || ("id".equals(key)))) {
                result.add(key);
            }
        }

        return new ArrayList<>(result);
    }

    @Override
    public List<String> getCountries() {
        ArrayList<String> result = new ArrayList<>();
        for (String key: this.countrycode.keySet()) {
            JSONObject temp = this.countrycode.get(key);
            if (temp.keySet().size() > 3) {
                result.add(key);
            }
        }

        return new ArrayList<>(result);
    }

    @Override
    public String translate(String country, String language) {
        JSONObject temp = this.countrycode.get(country);
        String result = temp.optString(language);
        if ("".equals(result)) {
            return null;
        }
        return result;
    }
}
