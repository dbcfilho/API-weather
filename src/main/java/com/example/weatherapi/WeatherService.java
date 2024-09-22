package com.example.weatherapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherService {

    private static final String API_KEY = "SUA_API_KEY"; // Insira sua chave da API aqui
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";

    public void getWeather(String cityName) {
        try {
            String response = getWeatherFromApi(cityName);

            JSONObject weatherData = new JSONObject(response);
            if (weatherData.getInt("cod") == 401) {
                System.out.println("Problema com a requisição!\nMensagem: " + weatherData.getString("message"));
            } else if (weatherData.getInt("cod") != 404) {
                String mainWeather = weatherData.getJSONArray("weather").getJSONObject(0).getString("main");
                double temperature = weatherData.getJSONObject("main").getDouble("temp");

                System.out.println("Clima em " + cityName + ": " + mainWeather);
                System.out.printf("Temperatura: %.2f°C\n", temperature - 273.15);
            } else {
                System.out.println("Cidade não encontrada!");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar os dados do clima. Tente novamente mais tarde.");
        }
    }

    private String getWeatherFromApi(String cityName) throws Exception {
        String completeUrl = BASE_URL + "appid=" + API_KEY + "&q=" + cityName;

        URL url = new URL(completeUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }
}
