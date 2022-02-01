package net.iesseveroochoa.fernandomartinezperez.practica5_2021.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;

@Database(entities = {DiaDiario.class}, version = 1)
public abstract class DiaRoomDatabase extends RoomDatabase {
@TypeConverters({TransformaFechaSQLite.class})
    public abstract DiarioDao diarioDAO();

    private static volatile DiaRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DiaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DiaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DiaRoomDatabase.class,
                            "Dia_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                DiarioDao mDao = INSTANCE.diarioDAO();
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
                DiaDiario diaDiario = null;

                Date date = new Date();
                try {
                    diaDiario = new DiaDiario(formatoDelTexto.parse("12-3-2020"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                            "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis ");
                    mDao.insert(diaDiario);
                    diaDiario = new DiaDiario(formatoDelTexto.parse("12-3-2020"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                            "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis ");
                    mDao.insert(diaDiario);
                    diaDiario = new DiaDiario(formatoDelTexto.parse("12-3-2020"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                            "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis ");
                    mDao.insert(diaDiario);
                    diaDiario = new DiaDiario(formatoDelTexto.parse("12-3-2020"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                            "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis ");
                    mDao.insert(diaDiario);
                    diaDiario = new DiaDiario(formatoDelTexto.parse("12-3-2020"), 5, "Actualización de antivirus", "Lorem ipsum dolor sit amet, " +
                            "consectetur adipiscing elit. Mauris laoreet aliquam sapien, quis mattis ");
                    mDao.insert(diaDiario);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
