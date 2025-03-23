package com.example.recibodeventademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recibodeventademo.R;

public class InputClientDataActivity extends AppCompatActivity {
    private EditText etClientFirstName, etClientLastName, etDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_data);

        etClientFirstName = findViewById(R.id.etClientFirstName);
        etClientLastName = findViewById(R.id.etClientLastName);
        etDNI = findViewById(R.id.etDNI);
    }

    public void onNextClick(View view) {
        String firstName = etClientFirstName.getText().toString().trim();
        String lastName = etClientLastName.getText().toString().trim();
        String dniStr = etDNI.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dniStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos del cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dniStr.length() > 8) {
            Toast.makeText(this, "La cédula no puede tener más de 8 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        int dni;
        try {
            dni = Integer.parseInt(dniStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "La cédula debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dni <= 0) {
            Toast.makeText(this, "La cédula debe ser un número positivo", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, InputIssuerDataActivity.class);
        intent.putExtra("clientFirstName", firstName);
        intent.putExtra("clientLastName", lastName);
        intent.putExtra("clientDNI", dni);
        startActivity(intent);
    }

    public void onBackClick(View view) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Estás seguro de que deseas salir? Se perderán los datos ingresados.")
                .setPositiveButton("Salir", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
