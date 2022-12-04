/**
 * Утилитарный класс, с методами обработки справочника городов
 * Методы этого класса:
 * loadFileToCollectionsCities - Публичный метод. Загрузаем справочник но в объект City и помещаем коллекцию cities.
 * parseLine                   - Внутренний метод. Парсим строки на отдельные элементы и помещаем элементы в объект City.
 * cityNameSortDesc            - Публичный метод. Сортировка списка городов по наименованию в алфавитном порядке по убыванию
 *                               без учета регистра.
 * cityNameDistrictSortDesc    - Публичный метод. Сортировка списка городов по федеральному округу и наименованию города
 *                               внутри каждого федерального округа в алфавитном порядке по убыванию с учетом регистра.
 * getIndexMaxPopulationCity   - Преобразовывает список городов в массив и путем перебора массива находит индекс элемента
 *                               и значение с наибольшим количеством жителей города. Возвращает String значение.
 * countDistrictsCities        - Определяет количество городов в разрезе регионов.
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
     * Создаём приватный контруктор и блокируем создание экземляров класса Utils
     */
    private Utils(){}

    /**
     * Публичный метод loadFileToCollectionsCities
     * Загрузаем справочник построчно в объект City и помещаем объект в коллекцию cities по средствам метода parseLine.
     */
    public static List<CityModelClasses.City> loadFileToCollectionsCities(String filePath){

        List<CityModelClasses.City> cities = new ArrayList<>();                         // Создаем коллекцию городов

        try (Scanner sc = new Scanner(new File(filePath), StandardCharsets.UTF_8)) {    // Пробуем прочитать файл
            while (sc.hasNextLine()) {
                cities.add(parseLine(sc.nextLine()));           // Читаем строки, передаем в метод parseLine, получаем обратно обьект City
            }
        } catch (FileNotFoundException e) {
            System.out.println("Указанный файл не обнаружен");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cities;                                          // Возвращаем коллекцию городов
    }

    /**
     * Внутренний метод parseLine
     * Парсим строки на отдельные элементы и помещаем элементы в объект City. Возвращаем City
     * в метод loadFileToCollectionsCities в коллекцию cities
     * @param line
     * @return {@link City}
     */
    private static CityModelClasses.City parseLine (String line){
        Scanner sc = new Scanner(line);
        sc.useDelimiter(";");                   // указываем разделитель строки
        sc.skip("\\d*");                        // пропускаем первый элемент (номер строки)
        String name = sc.next();
        String region = sc.next();
        String district = sc.next();
        int population = sc.nextInt();
        String foundation = null;                      // т.к. не всегда указывают дату основания, по умолчанию null
        if (sc.hasNext()) {
            foundation = sc.next();                    // если дату основания указана, то передаем ее в обьект City
        }
        sc.close();

        return new City(name, region, district, population, foundation); //возвращаем объект City
    }

    /**
     * Публичный метод cityNameSortDesc
     * Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра.
     * @param objCity
     */
    public static void cityNameSortDesc(List<CityModelClasses.City> objCity){
        objCity.sort(Comparator.comparing(City::getName, Comparator.reverseOrder()));
    }

    /**
     * Публичный метод cityNameDistrictSortDesc
     * Сортировка списка городов по федеральному округу и наименованию города внутри каждого федерального округа
     * в алфавитном порядке по убыванию с учетом регистра.
     * @param objCity
     */
    public static void cityNameDistrictSortDesc(List<CityModelClasses.City> objCity){
        objCity.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName, Comparator.reverseOrder()));
    }


    /**
     * Публичный метод getIndexMaxPopulationCity
     * Преобразовывает список городов в массив и путем перебора массива находит индекс элемента и значение с наибольшим
     * количеством жителей города. Возвращает String значение
     * @param objCity
     * @return {String}
     */
    public static String getIndexMaxPopulationCity(List<CityModelClasses.City> objCity) {
        City[] array = new City[objCity.size()];
        objCity.toArray(array);                         // переводим колекцию в массив
        City current = array[0];                        // создаем обьект в который будет помещаться город с наибольшим количеством жителей
        int index = 0;                                  // индекс города с наибольшим количеством жителей

        for (int i = 1; i < array.length; i++) {
            if (array[i].getPopulation() > current.getPopulation()) {
                current = array[i];
                index = i;
            }
        }
        return MessageFormat.format("[{0}] = {1}", index+1, array[index].getPopulation()); // возвращаем форматированный ответ, где индекс = + 1
    }

    /**
     * Публичный метод countDistrictsCities
     * Определяет количество городов в разрезе регионов.
     * @param objCity
     * @return {Map}
     */
    public static Map<String, Integer> countDistrictsCities(List<CityModelClasses.City> objCity){
        Map<String, Integer> countDistricts = new HashMap<>();      // HashMap в который будем помещать Ключь-Округ и Значение-количество городов
        City[] cityArray = new City[objCity.size()];                // массив для дальнейшего принятия и обработки данных из колекции
        objCity.toArray(cityArray);                                 // копируем коллекцию в массив

        for (City cityEntry : cityArray) {                          // перечисляем все значние массива
            String tempDistricts = cityEntry.getDistrict();
            if (!countDistricts.containsKey(tempDistricts)) {       // и если имени округа нет то записываем в countDistricts со значением 1
                countDistricts.put(tempDistricts, 1);
            } else {
                countDistricts.put(tempDistricts, countDistricts.get(tempDistricts) + 1); // если именя округа есть, записываем в countDistricts со значением +1
            }
        }
        return countDistricts;                  // возвращаем HashMap countDistricts
    }

}

