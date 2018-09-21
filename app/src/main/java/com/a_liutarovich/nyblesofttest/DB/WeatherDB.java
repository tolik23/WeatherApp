package com.a_liutarovich.nyblesofttest.DB;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "weather")
public class WeatherDB {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "adress")
    private String adress;

    @DatabaseField(canBeNull = false, columnName = "date")
    private Long date;

    @DatabaseField(columnName = "latitude")
    private Double latitude;

    @DatabaseField(columnName = "longitude")
    private Double longitude;

    @DatabaseField(columnName = "temp")
    private String temp;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "pressure")
    private String pressure;

    @DatabaseField(columnName = "humidity")
    private String humidity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherDB{" +
                "id=" + id +
                ", adress='" + adress + '\'' +
                ", date=" + date +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", temp='" + temp + '\'' +
                ", description='" + description + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
