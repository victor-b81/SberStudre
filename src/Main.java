import CityUtils.Utils;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String catalogSource = "resources/city_ru.csv";

        List<CityModelClasses.City> cities =
                Utils.loadFileToCollectionsCities(catalogSource);  // ������� ����� ����������� � �������� �������� �������

        Utils.cityNameSortDesc(cities);         // ���������� ������ ������� �� ������������ � ���������� ������� �� ��������
        Utils.cityNameDistrictSortDesc(cities); // ���������� ������ ������� �� ������������ ������ � ������������ ������

        Utils.countDistrictsCities(cities).forEach((key, value) -> System.out.println(key + " - " + value));    // ����� ���������� ������� �� �������

//      System.out.println(Utils.getIndexMaxPopulationCity(cities));  // getIndexMaxPopulationCity() ���������� ������ � ��������� �������
//      cities.forEach(System.out::println);                          // ����� ������ ������� � �������

    }
}
