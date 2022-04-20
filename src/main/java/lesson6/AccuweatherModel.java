package lesson6;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static lesson6.Period.FIVE_DAYS;

public class AccuweatherModel implements WeatherModel{
    //http://dataservice.accuweather.com/forecasts/v1/daily/5day/

    private static final String PROTOKOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String FIVE_DAYS = "5day";
    private static final String API_KEY = "pXJd8MokcZCdrd2MsoGl2DBZAyCa0zvv";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LOCATIONS = "locations";
    private static final String CITIES = "cities";
    private static final String AUTOCOMPLETE = "autocomplete";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void getWeather(String selectedCity, Period period) throws IOException {
        switch (period){
            case FIVE_DAYS:
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAYS)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .build();



                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();

                Response ForecastResponse = okHttpClient.newCall(request).execute();
                String weatherResponse = ForecastResponse.body().string();
                System.out.println(weatherResponse);

                //TODO: сделать человекочитаемый вывод погоды. Выбрать параметры для вывода на свое усмотрение
                //Например: Погода в городе Москва - 5 градусов по цельсию Expect showers late Monday night
                //dataBaseRepository.saveWeatherToDataBase(new Weather()) - тут после парсинга добавляем данные в БД

                break;



        }

    }

    private String detectCityKey(String selectedCity) throws IOException {
        //http://dataservice.accuweather.com/locations/v1/cities/search

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOKOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(AUTOCOMPLETE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter("q", selectedCity)
                .build();


        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response cityResponse = okHttpClient.newCall(request).execute();
        String weatherResponse = cityResponse.body().string();

        String cityKey = objectMapper.readTree(weatherResponse).get(0).at("/Key").asText();

        System.out.println(cityKey);

        return cityKey;

    }

    public static void main(String[] args) throws IOException {

        UserInterfaceView userInterfaceView =new UserInterfaceView();
        userInterfaceView.runInterface();

       // AccuweatherModel accuweatherModel = new AccuweatherModel();
        //accuweatherModel.getWeather( "Saint Petersburg", Period.FIVE_DAYS);



    }
}
