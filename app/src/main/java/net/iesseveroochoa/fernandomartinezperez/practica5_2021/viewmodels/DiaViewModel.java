package net.iesseveroochoa.fernandomartinezperez.practica5_2021.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaDiario;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.repository.DiarioRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DiaViewModel extends AndroidViewModel {
    private LiveData<List<DiaDiario>> listaDiasLiveData;
    private DiarioRepository repository;
    private List<DiaDiario> listaDias;
    private MutableLiveData<String> condicionBusquedaLiveData;
    private int salida;

    public int getSalida() {
        return salida;
    }

    public void setSalida(int salida) {
        this.salida = salida;
    }

    public DiaViewModel(@NonNull Application application) {
        super(application);
        repository = DiarioRepository.getInstance(application);
        listaDiasLiveData = repository.getAllDias();
        condicionBusquedaLiveData = new MutableLiveData<String>();
        condicionBusquedaLiveData.setValue("");


        listaDiasLiveData = Transformations.switchMap(condicionBusquedaLiveData,
                resumen -> repository.getByResumen(resumen));


    }


    public LiveData<List<DiaDiario>> getListaDias() {
        return listaDiasLiveData;
    }


    public void setListaDias(List<DiaDiario> listaDias) {
        this.listaDias = listaDias;
    }

    /**
     * Este metodo sirve para añadir un dia
     */
    public void addDia(DiaDiario dia) {

        repository.insert(dia);


    }

    /**
     * Este metodo sirve para borrar una dia
     */
    public void delDia(DiaDiario dia) {
        repository.delete(dia);

    }


    public void crearDatos() {
        Date date = new Date();
        listaDias = new ArrayList<DiaDiario>();
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");

        try {
            DiaDiario dia = new DiaDiario(formatoDelTexto.parse("12-3-2001"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                    "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                    "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                    "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                    "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                    "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                    "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                    "convallis");
            listaDias.add(dia);
            dia = new DiaDiario(formatoDelTexto.parse("12-3-2002"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
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
            dia = new DiaDiario(formatoDelTexto.parse("12-3-2004"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                    "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis " +
                    "diam pretium vel. Integer nec tincidunt turpis. Vestibulum interdum " +
                    "accumsan massa, sed blandit ex fringilla at. Vivamus non sem vitae nisl " +
                    "viverra pharetra. Pellentesque pulvinar vestibulum risus sit amet tempor. " +
                    "Sed blandit arcu sed risus interdum fermentum. Integer ornare lorem urna, " +
                    "eget consequat ante lacinia et. Phasellus ut diam et diam euismod " +
                    "convallis");
            listaDias.add(dia);
            dia = new DiaDiario(formatoDelTexto.parse("12-3-2005"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
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

        }
    }

    public int getMediaValorDias() {

        Single<Integer> interm = repository.getMediaVida();
        interm.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                salida = integer;
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        return salida;
    }

    public void setResumen(String query) {
        condicionBusquedaLiveData.setValue(query);

    }
}
