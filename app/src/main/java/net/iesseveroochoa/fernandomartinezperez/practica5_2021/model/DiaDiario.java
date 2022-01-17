package net.iesseveroochoa.fernandomartinezperez.practica5_2021.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

class DiaDiario implements Parcelable {
    public static final String TABLE_NAME = "diario";
    public static final String ID = BaseColumns._ID;
    public static final String FECHA = "fecha";
    public static final String VALORACION_DIA = "valoracion_dia";
    public static final String RESUMEN = "resumen";
    public static final String CONTENIDO = "contenido";
    public static final String FOTO_URI = "foto_uri";
    private int id;
    private Date fecha;
    private int valoracionDia;
    private String resumen;
    private String contenido;
    private String fotoUri;

    protected DiaDiario(Parcel in) {
        id = in.readInt();
        valoracionDia = in.readInt();
        resumen = in.readString();
        contenido = in.readString();
        fotoUri = in.readString();
    }

    public static final Creator<DiaDiario> CREATOR = new Creator<DiaDiario>() {
        @Override
        public DiaDiario createFromParcel(Parcel in) {
            return new DiaDiario(in);
        }

        @Override
        public DiaDiario[] newArray(int size) {
            return new DiaDiario[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getValoracionDia() {
        return valoracionDia;
    }

    public void setValoracionDia(int valoracionDia) {
        this.valoracionDia = valoracionDia;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFotoUri() {
        return fotoUri;
    }

    public void setFotoUri(String fotoUri) {
        this.fotoUri = fotoUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(valoracionDia);
        parcel.writeString(resumen);
        parcel.writeString(contenido);
        parcel.writeString(fotoUri);
    }

    public int getValoracionResumida(int valoracionDia) {
        int valRes = 0;
        if (valoracionDia < 5) {
            valRes = 1;
        } else if (valoracionDia >= 5 && valoracionDia < 8) {
            valRes = 2;
        } else {
            valRes = 3;
        }
        return valRes;
    }

    public static int getValResStatic(int valoracionDia) {
        int valRes = 0;
        if (valoracionDia < 5) {
            valRes = 1;
        } else if (valoracionDia >= 5 && valoracionDia < 8) {
            valRes = 2;
        } else {
            valRes = 3;
        }
        return valRes;
    }

    public String getFechaFormatoLocal() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,
                Locale.getDefault());
        return df.format(fecha);
    }

}
