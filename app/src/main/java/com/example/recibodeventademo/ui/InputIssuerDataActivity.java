
package com.example.recibodeventademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recibodeventademo.R;

public class InputIssuerDataActivity extends AppCompatActivity {
    private EditText etIssuerName, etRif;
    private Spinner spinnerRifType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuer_data);

        etIssuerName = findViewById(R.id.etIssuerName);
        etRif = findViewById(R.id.etRif);
        spinnerRifType = findViewById(R.id.spinnerRifType);
    }

    public void onNextClick(View view) {
        String issuerName = etIssuerName.getText().toString().trim();
        String rif = etRif.getText().toString().trim();
        String rifType = spinnerRifType.getSelectedItem().toString();

        if (issuerName.isEmpty() || rif.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos del emisor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rif.length() > 9) {
            Toast.makeText(this, "El RIF no puede tener más de 9 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        String fullRif = rifType + rif;

        Intent previousClient = getIntent();
        String clientFirstName = previousClient.getStringExtra("clientFirstName");
        String clientLastName = previousClient.getStringExtra("clientLastName");
        int clientDni = previousClient.getIntExtra("clientDNI", 0);

        Intent intent = new Intent(this, InputProductDataActivity.class);
        intent.putExtra("issuerName", issuerName);
        intent.putExtra("rif", fullRif);
        intent.putExtra("clientFirstName", clientFirstName);
        intent.putExtra("clientLastName", clientLastName);
        intent.putExtra("clientDNI", clientDni);
        startActivity(intent);
    }

    public void onBackClick(View view) {
        finish();
    }
}
