package net.iesseveroochoa.fernandomartinezperez.practica5_2021.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.iesseveroochoa.fernandomartinezperez.practica5_2021.Adapter.DiaAdapter;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.R;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaDiario;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaViewModel;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiarioDao;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView rvDia;
    public final static String EXTRA_DIA = "EXTRA_DIA";
    public static final int OPTION_REQUEST_CREAR = 1;
    public static final int OPTION_REQUEST_EDITAR = 2;
    private FloatingActionButton fabnDia;
    private DiaViewModel diaViewModel;
    private DiaAdapter adapter;
    private SearchView svBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        svBusqueda = findViewById(R.id.svBusqueda);

        rvDia = findViewById(R.id.rvDias);

        fabnDia = findViewById(R.id.fabNuevoDia);
        adapter = new DiaAdapter();

        rvDia.setLayoutManager(new LinearLayoutManager(this));
        rvDia.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        diaViewModel = new ViewModelProvider(this).get(DiaViewModel.class);
        diaViewModel.getListaDias().observe(this, adapter::setListaDias);


        fabnDia.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EdicionDiaActivity.class);
            int codigoNuevoDia = OPTION_REQUEST_CREAR;
            startActivityForResult(intent, codigoNuevoDia);
        });

        adapter.setOnItemClickBorrarListener(dia -> {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);

            builder.setMessage(getString(R.string.mensageBorrar) + dia.getId() + "?").setTitle(R.string.borrar)
                    .setPositiveButton("Ok", (dialog, id) -> {
                        diaViewModel.delDia(dia);
                        dialog.cancel();

                    })
                    .setNegativeButton(R.string.cancelar, (dialog, which) -> dialog.cancel());
            builder.show();

        });
        adapter.setOnItemClickEditarListener(dia -> {
            Intent intent = new Intent(MainActivity.this, EdicionDiaActivity.class);
            intent.putExtra(EXTRA_DIA, dia);
            startActivityForResult(intent, OPTION_REQUEST_EDITAR);
        });

        svBusqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                diaViewModel.setResumen(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() == 0)
                    diaViewModel.setResumen("");
                return false;
            }
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

        } else if (id == R.id.accion_Valor_vida) {
            int vidaMedia = diaViewModel.getMediaValorDias();

            AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();


            View v = inflater.inflate(R.layout.vida, null);
            ImageView imgVida = v.findViewById(R.id.ivVida);
            //realizamos una media de la vida
            // int totalVida = DiaDiario.getValoracionResumida((int) mediaValoracion.floatValue());
            switch (vidaMedia) {
                case 1://triste
                    imgVida.setImageResource(R.drawable.sad);
                    break;
                case 2:
                    imgVida.setImageResource(R.drawable.neutro);
                    break;
                case 3:
                    imgVida.setImageResource(R.drawable.smile);
                    break;
            }
            dialogo.setView(v)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            dialogo.show();
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            DiaDiario diario = data.getParcelableExtra(EXTRA_DIA);

            if (requestCode == OPTION_REQUEST_CREAR) {


                diaViewModel.addDia(diario);

            } else if (requestCode == OPTION_REQUEST_EDITAR) {

                diaViewModel.addDia(diario);
            }
        }
    }
}