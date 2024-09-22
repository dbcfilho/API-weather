package com.example.weatherapi;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cityName = scanner.nextLine();

        WeatherService weatherService = new WeatherService();
        weatherService.getWeather(cityName);
    }
}
