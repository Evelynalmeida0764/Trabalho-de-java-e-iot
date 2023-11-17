package com.javaCrud;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SensorData implements Comparable<SensorData> {


    private String id;
    private String datetime;
    private int umidadeAr;
    private int umidadeSolo;
    private int temperatura;

    public SensorData(String id, String datetime, int umidadeAr, int umidadeSolo, int temperatura) {
        this.id = id;
        this.datetime = datetime;
        this.umidadeAr = umidadeAr;
        this.umidadeSolo = umidadeSolo;
        this.temperatura = temperatura;
    }

    public String getId() {
        return id;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getUmidadeAr() {
        return umidadeAr;
    }

    public int getUmidadeSolo() {
        return umidadeSolo;
    }

    public int getTemperatura() {
        return temperatura;
    }

    @Override
    public int compareTo(SensorData other) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss.SSSSSS");
            Date thisDate = sdf.parse(this.datetime);
            Date otherDate = sdf.parse(other.datetime);
            return thisDate.compareTo(otherDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Trate o erro de parsing conforme necessário
        }
    }

    @Override
    public String toString() {
        return "ID do Documento: " + id +
                "\nDados do Documento: {datetime=" + datetime +
                ", umidade_ar=" + umidadeAr +
                ", humidade_solo=" + umidadeSolo +
                ", temperatura=" + temperatura + "}";
    }
}
