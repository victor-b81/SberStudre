import CityUtils.Utils;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String catalogSource = "resources/city_ru.csv";

        List<CityModelClasses.City> cities =
                Utils.loadFileToCollectionsCities(catalogSource);  // передаю адрес справочника и извлекаю колекцию городов

        Utils.cityNameSortDesc(cities);         // сортировка списка городов по наименованию в алфавитном порядке по убыванию
        Utils.cityNameDistrictSortDesc(cities); // сортировка списка городов по федеральному округу и наименованию города

        Utils.countDistrictsCities(cities).forEach((key, value) -> System.out.println(key + " - " + value));    // вывод количества городов по округам

//      System.out.println(Utils.getIndexMaxPopulationCity(cities));  // getIndexMaxPopulationCity() возвращает индекс и количесво жителей
//      cities.forEach(System.out::println);                          // вывод списка городов в консоль

    }
}
