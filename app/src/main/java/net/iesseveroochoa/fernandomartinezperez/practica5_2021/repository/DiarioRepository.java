package net.iesseveroochoa.fernandomartinezperez.practica5_2021.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaDiario;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaRoomDatabase;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiarioDao;


import java.util.List;

import io.reactivex.Single;


public class DiarioRepository {
    //implementamos Singleton
    private static volatile DiarioRepository INSTANCE;

    private DiarioDao mDiarioDao;
    private LiveData<List<DiaDiario>> mAllDias;

    //singleton
    public static DiarioRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (DiarioRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DiarioRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    private DiarioRepository(Application application) {
        DiaRoomDatabase db = DiaRoomDatabase.getDatabase(application);
        mDiarioDao = db.diarioDAO();
        mAllDias = mDiarioDao.getAllDiaDiario();
    }

    public LiveData<List<DiaDiario>> getAllDias() {
        return mAllDias;
    }

    public Single<Integer> getMediaVida(){
        return mDiarioDao.getValorVida();
    }

    public LiveData<List<DiaDiario>> getDiasOrderBy(String order_by, String order) {
        mAllDias = mDiarioDao.getDiaDiarioOrderBy(order_by, order);
        return mAllDias;
    }


    public void insert(DiaDiario diaDiario) {
        DiaRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDiarioDao.insert(diaDiario);
        });


    }


    public void delete(DiaDiario diaDiario) {
        DiaRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDiarioDao.deleteByDiaDiario(diaDiario);
        });
    }

    public LiveData<List<DiaDiario>> getByResumen(String resumen) {
        mAllDias = mDiarioDao.findByResumen(resumen);
        return mAllDias;
    }
}