package com.example.recibodeventademo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recibodeventademo.MainActivity;
import com.example.recibodeventademo.R;
import com.example.recibodeventademo.data.Product;
import com.example.recibodeventademo.data.Receipt;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class ReceiptActivity extends AppCompatActivity {
    private TextView tvIssuerName, tvIssuerRif, tvClientFullName, tvClientDNI, tvTotal;
    private LinearLayout productsContainer;
    private Button btnFinalize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        tvIssuerName = findViewById(R.id.tvIssuerName);
        tvIssuerRif = findViewById(R.id.tvIssuerRif);
        tvClientFullName = findViewById(R.id.tvClientFullName);
        tvClientDNI = findViewById(R.id.tvClientDNI);
        tvTotal = findViewById(R.id.tvTotal);
        productsContainer = findViewById(R.id.productsContainer);
        btnFinalize = findViewById(R.id.btnFinalize);

        Receipt receipt = (Receipt) getIntent().getSerializableExtra("receipt");

        List<Product> productList = (List<Product>) getIntent().getSerializableExtra("productList");

        if (productList == null) {
            Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
            return;
        }

        populateReceipt(receipt, productList);

        btnFinalize.setOnClickListener(v -> showSaveReceiptDialog(receipt));
    }

    private void populateReceipt(Receipt receipt, List<Product> productList) {
        tvIssuerName.setText(receipt.getIssuer().getName());
        tvIssuerRif.setText(receipt.getIssuer().getRif());
        tvClientFullName.setText("Cliente: " + receipt.getClient().getFullName());
        tvClientDNI.setText(" CI: " + receipt.getClient().getDNI());

        for (Map.Entry<String, Integer> entry : receipt.getProductCounts().entrySet()) {
            String productCode = entry.getKey();
            int quantity = entry.getValue();

            Product product = findProductByCode(productCode, productList);

            if (product != null) {
                LinearLayout productView = (LinearLayout) getLayoutInflater().inflate(R.layout.item_product, null);

                TextView tvProductCode = productView.findViewById(R.id.tvProductCode);
                TextView tvProductQuantityPrice = productView.findViewById(R.id.tvProductQuantityPrice);
                TextView tvProductDescription = productView.findViewById(R.id.tvProductDescription);
                TextView tvProductSubtotal = productView.findViewById(R.id.tvProductSubtotal);

                tvProductCode.setText("Código: " + product.getCode());
                tvProductQuantityPrice.setText(quantity + " x $" + String.format("%.2f", product.getPrice()));
                tvProductDescription.setText(product.getDescription());
                tvProductSubtotal.setText("$" + String.format("%.2f", quantity * product.getPrice()));

                productsContainer.addView(productView);
            } else {
                Toast.makeText(this, "Producto con código " + productCode + " no encontrado", Toast.LENGTH_SHORT).show();
            }
        }

        tvTotal.setText("$" + String.format("%.2f", receipt.getTotal()));
    }

    private Product findProductByCode(String code, List<Product> productList) {
        for (Product product : productList) {
            if (product.getCode().equals(code)) {
                return product;
            }
        }
        return null;
    }


    private void showSaveReceiptDialog(Receipt receipt) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Guardar recibo")
                .setMessage("¿Deseas guardar este recibo?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    saveReceiptLocally(receipt);
                    navigateToHome();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    navigateToHome();
                })
                .show();
    }

    private void saveReceiptLocally(Receipt receipt) {
        try {
            Gson gson = new Gson();

            String receiptJson = gson.toJson(receipt);

            String fileName = "receipt_" + System.currentTimeMillis() + ".json";
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(receiptJson.getBytes());
            fos.close();

            System.out.println("Recibo guardado: " + receiptJson);

            Toast.makeText(this, "Recibo guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el recibo", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}