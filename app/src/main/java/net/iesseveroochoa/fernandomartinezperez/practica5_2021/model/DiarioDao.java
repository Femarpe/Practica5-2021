package net.iesseveroochoa.fernandomartinezperez.practica5_2021.model;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;


@Dao
public interface DiarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DiaDiario diaDiario);

    @Query("DELETE FROM "+DiaDiario.TABLE_NAME)
    void deleteAll();

    @Delete
    void deleteByDiaDiario(DiaDiario diaDiario);

    @Update
    void update(DiaDiario diaDiario);

    @Query("SELECT * FROM "+DiaDiario.TABLE_NAME+" ORDER BY _id")
    LiveData<List<DiaDiario>> getAllDiaDiario();


    @Query("SELECT COUNT(*) from DIARIO")
    int countDiaDiario();





    @Query("SELECT * FROM  " +DiaDiario.TABLE_NAME +
            " ORDER BY " +
            "CASE WHEN :sort_by = 'id'   AND :sort = 'ASC' THEN _id END ASC, "+
            "CASE WHEN :sort_by = 'id'   AND :sort = 'DESC' THEN _id END DESC"


    )
    LiveData<List<DiaDiario>> getDiaDiarioOrderBy(String sort_by, String sort);

    @Query("SELECT * FROM "+DiaDiario.TABLE_NAME+" where resumen LIKE  '%' || :resumen || '%' OR contenido LIKE '%' || :resumen || '%'")
    LiveData<List<DiaDiario>> findByResumen(String resumen);
}
