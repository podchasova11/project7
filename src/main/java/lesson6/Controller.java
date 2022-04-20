package lesson6;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private WeatherModel weatherModel = new AccuweatherModel();
    private Map<Integer, Period> variants = new HashMap<>();

    public Controller() {
        variants.put(5, Period.FIVE_DAYS);
    }

    public void  getWeather(String userInput, String selectedCity) {
        Integer userIntegerInput = Integer.parseInt(userInput);

        switch (variants.get(userIntegerInput)) {
            case FIVE_DAYS:
                try {
                    weatherModel.getWeather(selectedCity, Period.FIVE_DAYS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

}
