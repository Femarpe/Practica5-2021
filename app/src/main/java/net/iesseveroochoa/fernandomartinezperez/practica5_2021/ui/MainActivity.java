package net.iesseveroochoa.fernandomartinezperez.practica5_2021.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.iesseveroochoa.fernandomartinezperez.practica5_2021.Adapter.DiaAdapter;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.R;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaDiario;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaViewModel;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_DIA = "EXTRA_DIA";
    public static final int OPTION_REQUEST_CREAR = 1;
    public static final int OPTION_REQUEST_EDITAR = 2;
    private FloatingActionButton fabnDia;
    private DiaViewModel diaViewModel;
    private DiaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabnDia = findViewById(R.id.fabNuevoDia);
        adapter = new DiaAdapter();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        diaViewModel = new ViewModelProvider(this).get(DiaViewModel.class);
        diaViewModel.getListaDias().observe(this, adapter::setListaDias);
        diaViewModel.crearDatos();


        fabnDia.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EdicionDiaActivity.class);
            int codigoNuevaTarea = OPTION_REQUEST_CREAR;
            startActivityForResult(intent, codigoNuevaTarea);
        });

        adapter.setOnItemClickBorrarListener(tarea -> {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);

            builder.setMessage(getString(R.string.mensageBorrar) + tarea.getId() + "?").setTitle(R.string.borrar)
                    .setPositiveButton("Ok", (dialog, id) -> {
                        diaViewModel.delTarea(tarea);
                        dialog.cancel();

                    })
                    .setNegativeButton(R.string.cancelar, (dialog, which) -> dialog.cancel());
            builder.show();

        });
        adapter.setOnItemClickEditarListener(tarea -> {
            Intent intent = new Intent(MainActivity.this, EdicionDiaActivity.class);
            intent.putExtra(EXTRA_DIA, tarea);
            startActivityForResult(intent, OPTION_REQUEST_EDITAR);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.accion_AcercaDe) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            AcercaDe dialogo = new AcercaDe();
            dialogo.show(fragmentManager, "tagAlerta");

            return true;
        } else if (id == R.id.accion_ordenar) {


            return false;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Aqui se recogen los datos de la nuava tarea
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            /**En caso de que la tarea sea nuava aqui se recoge y se añade al recycler view*/
            DiaDiario diario = data.getParcelableExtra(EXTRA_DIA);

            if (requestCode == OPTION_REQUEST_CREAR) {


                diaViewModel.addTarea(diario);

                /**en caso de que la tarea fuese una editada se sobreesciben los datos*/
            } else if (requestCode == OPTION_REQUEST_EDITAR) {

                diaViewModel.addTarea(diario);
            }
        }
    }
}