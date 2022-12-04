/**
 * ����������� �����, � �������� ��������� ����������� �������
 * ������ ����� ������:
 * loadFileToCollectionsCities - ��������� �����. ��������� ���������� �� � ������ City � �������� ��������� cities.
 * parseLine                   - ���������� �����. ������ ������ �� ��������� �������� � �������� �������� � ������ City.
 * cityNameSortDesc            - ��������� �����. ���������� ������ ������� �� ������������ � ���������� ������� �� ��������
 *                               ��� ����� ��������.
 * cityNameDistrictSortDesc    - ��������� �����. ���������� ������ ������� �� ������������ ������ � ������������ ������
 *                               ������ ������� ������������ ������ � ���������� ������� �� �������� � ������ ��������.
 * getIndexMaxPopulationCity   - ��������������� ������ ������� � ������ � ����� �������� ������� ������� ������ ��������
 *                               � �������� � ���������� ����������� ������� ������. ���������� String ��������.
 * countDistrictsCities        - ���������� ���������� ������� � ������� ��������.
 */

package CityUtils;

import CityModelClasses.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

public class Utils {

    /**
     * ������ ��������� ���������� � ��������� �������� ���������� ������ Utils
     */
    private Utils(){}

    /**
     * ��������� ����� loadFileToCollectionsCities
     * ��������� ���������� ��������� � ������ City � �������� ������ � ��������� cities �� ��������� ������ parseLine.
     */
    public static List<CityModelClasses.City> loadFileToCollectionsCities(String filePath){

        List<CityModelClasses.City> cities = new ArrayList<>();                         // ������� ��������� �������

        try (Scanner sc = new Scanner(new File(filePath), StandardCharsets.UTF_8)) {    // ������� ��������� ����
            while (sc.hasNextLine()) {
                cities.add(parseLine(sc.nextLine()));           // ������ ������, �������� � ����� parseLine, �������� ������� ������ City
            }
        } catch (FileNotFoundException e) {
            System.out.println("��������� ���� �� ���������");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cities;                                          // ���������� ��������� �������
    }

    /**
     * ���������� ����� parseLine
     * ������ ������ �� ��������� �������� � �������� �������� � ������ City. ���������� City
     * � ����� loadFileToCollectionsCities � ��������� cities
     * @param line
     * @return {@link City}
     */
    private static CityModelClasses.City parseLine (String line){
        Scanner sc = new Scanner(line);
        sc.useDelimiter(";");                   // ��������� ����������� ������
        sc.skip("\\d*");                        // ���������� ������ ������� (����� ������)
        String name = sc.next();
        String region = sc.next();
        String district = sc.next();
        int population = sc.nextInt();
        String foundation = null;                      // �.�. �� ������ ��������� ���� ���������, �� ��������� null
        if (sc.hasNext()) {
            foundation = sc.next();                    // ���� ���� ��������� �������, �� �������� �� � ������ City
        }
        sc.close();

        return new City(name, region, district, population, foundation); //���������� ������ City
    }

    /**
     * ��������� ����� cityNameSortDesc
     * ���������� ������ ������� �� ������������ � ���������� ������� �� �������� ��� ����� ��������.
     * @param objCity
     */
    public static void cityNameSortDesc(List<CityModelClasses.City> objCity){
        objCity.sort(Comparator.comparing(City::getName, Comparator.reverseOrder()));
    }

    /**
     * ��������� ����� cityNameDistrictSortDesc
     * ���������� ������ ������� �� ������������ ������ � ������������ ������ ������ ������� ������������ ������
     * � ���������� ������� �� �������� � ������ ��������.
     * @param objCity
     */
    public static void cityNameDistrictSortDesc(List<CityModelClasses.City> objCity){
        objCity.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName, Comparator.reverseOrder()));
    }


    /**
     * ��������� ����� getIndexMaxPopulationCity
     * ��������������� ������ ������� � ������ � ����� �������� ������� ������� ������ �������� � �������� � ����������
     * ����������� ������� ������. ���������� String ��������
     * @param objCity
     * @return {String}
     */
    public static String getIndexMaxPopulationCity(List<CityModelClasses.City> objCity) {
        City[] array = new City[objCity.size()];
        objCity.toArray(array);                         // ��������� �������� � ������
        City current = array[0];                        // ������� ������ � ������� ����� ���������� ����� � ���������� ����������� �������
        int index = 0;                                  // ������ ������ � ���������� ����������� �������

        for (int i = 1; i < array.length; i++) {
            if (array[i].getPopulation() > current.getPopulation()) {
                current = array[i];
                index = i;
            }
        }
        return MessageFormat.format("[{0}] = {1}", index+1, array[index].getPopulation()); // ���������� ��������������� �����, ��� ������ = + 1
    }

    /**
     * ��������� ����� countDistrictsCities
     * ���������� ���������� ������� � ������� ��������.
     * @param objCity
     * @return {Map}
     */
    public static Map<String, Integer> countDistrictsCities(List<CityModelClasses.City> objCity){
        Map<String, Integer> countDistricts = new HashMap<>();      // HashMap � ������� ����� �������� �����-����� � ��������-���������� �������
        City[] cityArray = new City[objCity.size()];                // ������ ��� ����������� �������� � ��������� ������ �� ��������
        objCity.toArray(cityArray);                                 // �������� ��������� � ������

        for (City cityEntry : cityArray) {                          // ����������� ��� ������� �������
            String tempDistricts = cityEntry.getDistrict();
            if (!countDistricts.containsKey(tempDistricts)) {       // � ���� ����� ������ ��� �� ���������� � countDistricts �� ��������� 1
                countDistricts.put(tempDistricts, 1);
            } else {
                countDistricts.put(tempDistricts, countDistricts.get(tempDistricts) + 1); // ���� ����� ������ ����, ���������� � countDistricts �� ��������� +1
            }
        }
        return countDistricts;                  // ���������� HashMap countDistricts
    }

}

