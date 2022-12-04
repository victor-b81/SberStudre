/**
 * ��������� ����� City
 */

package CityModelClasses;

public class City {
    private final String name,    // ������������ ������
                        region,         // ������
                        district,       // ����������� �����
                        foundation;     // ���� ��������� ��� ������ ����������
    private final int population; // ���������

    public City(String name, String region, String district, int population, String foundation) {   // ����������� �� ���������
        this.name = name;
        this.region = region;
        this.district = district;
        this.population = population;
        this.foundation = foundation;
    }

    @Override
    public String toString() {                                                 // �������������� ����� toString
        return "City{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                ", foundation=" + foundation +
                '}';
    }

    public String getDistrict() {
        return district;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getFoundation() {
        return foundation;
    }

    public int getPopulation() {
        return population;
    }
}
