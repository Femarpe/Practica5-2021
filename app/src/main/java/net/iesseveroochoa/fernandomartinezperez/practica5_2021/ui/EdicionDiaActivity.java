package net.iesseveroochoa.fernandomartinezperez.practica5_2021.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import net.iesseveroochoa.fernandomartinezperez.practica5_2021.R;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaDiario;

import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class EdicionDiaActivity extends AppCompatActivity {

    public final static String EXTRA_DIA = "EXTRA_DIA";
    private static final int STATUS_CODE_SELECCION_IMAGEN = 300;
    private static final int MY_PERMISSIONS = 100;
    private EditText etResumen;
    private ImageButton ibfecha;
    private EditText etmContenido;
    private FloatingActionButton fabGuardar;
    private Spinner spValorDia;
    private TextView tvFecha;
    private Uri uriFoto = null;
    private ImageView ivFotoDia;
    boolean esEdicion;
    private Button addImg;
    DiaDiario oldDiaDiario;
    DiaDiario dia;
    private View clPrincipal;
    Date fechaselec;


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
        ivFotoDia = findViewById(R.id.ivFotoDia);
        addImg = findViewById(R.id.btAddImg);
        fechaselec = new Date();

        oldDiaDiario = getIntent().getParcelableExtra(EXTRA_DIA);
        if (oldDiaDiario != null) {
            esEdicion = true;
            spValorDia.setSelection(getIndex(spValorDia, String.valueOf(oldDiaDiario.getValoracionDia())));
            tvFecha.setText(oldDiaDiario.getFechaFormatoLocal());
            etResumen.setText(oldDiaDiario.getResumen());
            etmContenido.setText(oldDiaDiario.getContenido());
            if (oldDiaDiario.getFotoUri() != null) {
                uriFoto = Uri.parse(oldDiaDiario.getFotoUri());
                muestraFoto();
            }
        } else {
            esEdicion = false;
            tvFecha.setText(DiaDiario.getFechaFormatoLocal(fechaselec));
        }

        ibfecha.setOnClickListener(view -> {
            DatePickerDialog dialogo = new DatePickerDialog(this, new
                    DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);

                            fechaselec = calendar.getTime();

                            if (esEdicion) {
                                oldDiaDiario.setFecha(fechaselec);
                                tvFecha.setText(oldDiaDiario.getFechaFormatoLocal());
                            } else {
                                tvFecha.setText(DiaDiario.getFechaFormatoLocal(fechaselec));
                            }
                        }
                    },

                    newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(Calendar.DAY_OF_MONTH));
            dialogo.show();

        });

        /**Aqui si se pulsa 'guardar' se recogen los datos en pantalla y se envian a la acividad principal*/
        fabGuardar.setOnClickListener(v -> {
            if (etResumen.getText().toString().isEmpty() || etmContenido.getText().toString().isEmpty()) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(EdicionDiaActivity.this);

                builder.setMessage(getString(R.string.cvacioMensage))
                        .setTitle(R.string.campovacio)
                        .setPositiveButton("Ok", (dialog, id) -> dialog.cancel());

                builder.show();
            } else {

                DiaDiario newDiaDiario;
                int valordia = Integer.parseInt(spValorDia.getSelectedItem().toString());

                if (esEdicion) {
                    newDiaDiario = new DiaDiario(
                            oldDiaDiario.getId(),
                            oldDiaDiario.getFecha(),
                            valordia,
                            etResumen.getText().toString(),
                            etmContenido.getText().toString());

                } else {
                    newDiaDiario = new DiaDiario(
                            fechaselec,
                            valordia,
                            etResumen.getText().toString(),
                            etmContenido.getText().toString());

                }
                ocultarTeclado();
                if (uriFoto != null) {
                    newDiaDiario.setFotoUri(uriFoto.toString());
                }
                intent.putExtra(EXTRA_DIA, newDiaDiario);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


        addImg.setOnClickListener(view -> {
            if (pedirPermosos()) {
                muestraOpcionesImagen();
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

    private void elegirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Seleccione una imagen"), STATUS_CODE_SELECCION_IMAGEN);
    }

    private void muestraFoto() {
        Glide.with(this)
                .load(uriFoto)
                .into(ivFotoDia);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case STATUS_CODE_SELECCION_IMAGEN:
                    uriFoto = data.getData();
                    muestraFoto();
                    break;
            }
        }
    }

    private void muestraOpcionesImagen() {
        final CharSequence[] option = {"Hacer foto", "Elegir de la galería",
                getString(android.R.string.cancel)};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(android.R.string.dialog_alert_title);
        builder.setItems(option, (dialog, which) -> {
            switch (which) {
                case 0:
                    abrirCamara();
                    break;
                case 1:
                    elegirGaleria();
                    break;
            }
            dialog.dismiss();
        });
        builder.show();
    }

    private boolean pedirPermosos() {

        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))
            return true;

        else if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))
                || (shouldShowRequestPermissionRationale(CAMERA))) {

            Snackbar.make(clPrincipal,
                    "Es necesario acptar los permisos para esta funcion",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok,
                    v -> requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,
                            CAMERA}, MY_PERMISSIONS)).show();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},
                    MY_PERMISSIONS);
        }
        return false;
    }


    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(etResumen.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(etmContenido.getWindowToken(), 0);
        }
    }

    public void abrirCamara() {

    }
}