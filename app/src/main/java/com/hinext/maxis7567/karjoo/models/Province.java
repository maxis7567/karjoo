package com.hinext.maxis7567.karjoo.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Province {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String provinceName;

    @SerializedName("counties")
    private List<County> counties;

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class County {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String countyName;

        @SerializedName("cities")
        private List<City> cities;

        public List<City> getCities() {
            return cities;
        }

        public void setCities(List<City> cities) {
            this.cities = cities;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class City {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String cityName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}
