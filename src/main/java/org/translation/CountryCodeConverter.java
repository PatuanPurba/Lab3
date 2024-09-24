package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


/**
 * This class provides the service of converting countries codes to their names.
 */
public class CountryCodeConverter {

    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
    private Map<String, String> countrycode;
    private Map<String, String> codecountry;

    /**
     * Default constructor which will load the countries codes from "countries-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the countries code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // TODO Task: use lines to populate the instance variable(s)
            countrycode = new HashMap<>();
            codecountry = new HashMap<>();
            for (String line : lines.subList(1, lines.size())) {
                String[] temp = line.split("\t");
                String country = temp[0];
                String code = temp[2];

                countrycode.put(country, code);
                codecountry.put(code, country);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the countries for the given countries code.
     * @param code the 3-letter code of the countries
     * @return the name of the countries corresponding to the code
     */
    public String fromCountryCode(String code) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return codecountry.get(code.toUpperCase());
    }

    /**
     * Returns the code of the countries for the given countries name.
     * @param country the name of the countries
     * @return the 3-letter code of the countries
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countrycode.get(country).toLowerCase();
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return countrycode.size();
    }
}
