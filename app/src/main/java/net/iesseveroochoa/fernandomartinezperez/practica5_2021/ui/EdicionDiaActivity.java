package net.iesseveroochoa.fernandomartinezperez.practica5_2021.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.iesseveroochoa.fernandomartinezperez.practica5_2021.R;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaDiario;

import java.util.Calendar;
import java.util.Date;

public class EdicionDiaActivity extends AppCompatActivity {

    public final static String EXTRA_DIA = "EXTRA_DIA";

    private EditText etResumen;
    private ImageButton ibfecha;
    private EditText etmContenido;
    private FloatingActionButton fabGuardar;
    private Spinner spValorDia;
    private TextView tvFecha;

    boolean esEdicion;
    DiaDiario diaDiarioAnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_dia);
        Intent intent = new Intent();
        Calendar newCalendar = Calendar.getInstance();
        ibfecha = findViewById(R.id.ibFecha);
        etResumen = findViewById(R.id.etResumen);
        etmContenido = findViewById(R.id.etmContenido);
        fabGuardar = findViewById(R.id.fabGuardar);
        spValorDia = findViewById(R.id.spValorDia);
        tvFecha = findViewById(R.id.tvFecha);
        diaDiarioAnt = getIntent().getParcelableExtra(EXTRA_DIA);

        ibfecha.setOnClickListener(view -> {
            DatePickerDialog dialogo = new DatePickerDialog(this, new
                    DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int
                                monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            Date fecha = calendar.getTime();
                        }
                    },

                    newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(Calendar.DAY_OF_MONTH));
            dialogo.show();
        });

        if (diaDiarioAnt != null) {
            esEdicion = true;
            spValorDia.setSelection(getIndex(spValorDia, String.valueOf(diaDiarioAnt.getValoracionDia())));
            tvFecha.setText(diaDiarioAnt.getFecha().toString());
            etResumen.setText(diaDiarioAnt.getResumen());
            etmContenido.setText(diaDiarioAnt.getContenido());
        } else {
            esEdicion = false;
        }

        /**Aqui si se pulsa 'guardar' se recogen los datos en pantalla y se envian a la acividad principal*/
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etResumen.getText().toString().isEmpty() || etmContenido.getText().toString().isEmpty()) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(EdicionDiaActivity.this);

                    builder.setMessage(getString(R.string.cvacioMensage))
                            .setTitle(R.string.campovacio)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder.show();
                } else {

                    DiaDiario diaDiario;


                    if (esEdicion == true) {
                        diaDiario = new DiaDiario(diaDiarioAnt.getId(), newCalendar.getTime(), (int) spValorDia.getSelectedItem(), etResumen.getText().toString(), etmContenido.getText().toString());

                    } else {
                        diaDiario = new DiaDiario(newCalendar.getTime(), (int) spValorDia.getSelectedItem(), etResumen.getText().toString(), etmContenido.getText().toString());

                    }

                    intent.putExtra(EXTRA_DIA, diaDiario);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });


    }

    private int getIndex(Spinner spinner, String valor) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(valor)) {
                return i;
            }
        }
        return 0;
    }
}