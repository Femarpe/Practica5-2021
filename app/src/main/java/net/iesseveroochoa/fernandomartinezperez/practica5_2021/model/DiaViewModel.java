package net.iesseveroochoa.fernandomartinezperez.practica5_2021.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaViewModel extends AndroidViewModel {
    private MutableLiveData<List<DiaDiario>> listaDiasLiveData;

    private List<DiaDiario> listaDias;

    public DiaViewModel(@NonNull Application application) {
        super(application);

        listaDiasLiveData = new MutableLiveData<List<DiaDiario>>();
        crearDatos();
        listaDiasLiveData.setValue(listaDias);

    }


    public LiveData<List<DiaDiario>> getListaDias() {
        return listaDiasLiveData;
    }


    public void setListaDias(List<DiaDiario> listaDias) {
        this.listaDias = listaDias;
    }

    /**
     * Este metodo sirve par añadir una dia
     */
    public void addDia(DiaDiario dia) {
        int index = 0;

        index = listaDias.indexOf(dia);
        if (index < 0) {
            listaDias.add(dia);
            listaDiasLiveData.setValue(listaDias);

        } else {
            listaDias.remove(index);
            listaDias.add(index, dia);
            listaDiasLiveData.setValue(listaDias);

        }
    }

    /**
     * Este metodo sirve para borrar una dia
     */
    public void delDia(DiaDiario dia) {
        if (listaDias.size() > 0) {
            listaDias.remove(dia);
            listaDiasLiveData.setValue(listaDias);
        }
    }


    public void crearDatos() {
        Date date = new Date();
        listaDias = new ArrayList<DiaDiario>();
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");

        try {
        DiaDiario dia = new DiaDiario(formatoDelTexto.parse("12-3-2003"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                "convallis");
        listaDias.add(dia);
        dia = new DiaDiario(formatoDelTexto.parse("12-3-2003"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                "convallis");
        listaDias.add(dia);
        dia = new DiaDiario(formatoDelTexto.parse("12-3-2003"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                "convallis");
        listaDias.add(dia);
        dia = new DiaDiario(formatoDelTexto.parse("12-3-2003"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                "convallis");
        listaDias.add(dia);
        dia = new DiaDiario(formatoDelTexto.parse("12-3-2003"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                "convallis");
        listaDias.add(dia);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void setDatos(int indice, Date fecha, int valoracionDia, String resumen, String contenido) {


        if (indice > 0) {
            listaDias.get(indice).setFecha(fecha);
            listaDias.get(indice).setContenido(contenido);
            listaDias.get(indice).setValoracionDia(valoracionDia);
            listaDias.get(indice).setResumen(resumen);
            listaDiasLiveData.setValue(listaDias);

        }
    }
}
