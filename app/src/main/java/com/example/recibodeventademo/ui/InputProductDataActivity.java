package com.example.recibodeventademo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recibodeventademo.R;
import com.example.recibodeventademo.adapters.ProductListAdapter;
import com.example.recibodeventademo.data.Client;
import com.example.recibodeventademo.data.Issuer;
import com.example.recibodeventademo.data.Product;
import com.example.recibodeventademo.data.Receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputProductDataActivity extends AppCompatActivity {
    private EditText etProductCode, etProductDescription, etProductPrice;
    private Button btnIncrement, btnDecrement, btnTotalize;
    private TextView tvSubtotal, tvQuantity;
    private ListView lvProductList;
    private List<Product> productList;
    private ProductListAdapter productAdapter;
    private double subtotal = 0;
    private Product currentProduct;
    private int currentQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_data);

        etProductCode = findViewById(R.id.etProductCode);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductPrice = findViewById(R.id.etProductPrice);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnIncrement = findViewById(R.id.btnIncrement);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnTotalize = findViewById(R.id.btnTotalize);
        lvProductList = findViewById(R.id.lvProductList);

        productList = new ArrayList<>();
        productAdapter = new ProductListAdapter(this, productList);
        lvProductList.setAdapter(productAdapter);

        btnTotalize.setEnabled(false);
        btnIncrement.setOnClickListener(v -> incrementQuantity());
        btnDecrement.setOnClickListener(v -> decrementQuantity());
    }

    private void incrementQuantity() {
        currentQuantity++;
        tvQuantity.setText(String.valueOf(currentQuantity));
    }

    private void decrementQuantity() {
        if (currentQuantity > 1) {
            currentQuantity--;
            tvQuantity.setText(String.valueOf(currentQuantity));
        }
    }

    public void onAddProductClick(View view) {
        String code = etProductCode.getText().toString().trim();
        String description = etProductDescription.getText().toString().trim();
        String priceStr = etProductPrice.getText().toString().trim();

        if (code.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos del producto", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El precio debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (price <= 0) {
            Toast.makeText(this, "El precio debe ser un número positivo", Toast.LENGTH_SHORT).show();
            return;
        }

        Product exitingProduct = null;
        for (Product product : productList) {
            if (product.getCode().equals(code)) {
                exitingProduct = product;
                break;
            }
        }

        if (exitingProduct != null) {
            exitingProduct.setQuantity(exitingProduct.getQuantity() + currentQuantity);
        } else {
            Product product = new Product(code, description, price);
            product.setQuantity(currentQuantity);
            productList.add(product);
        }

        recalculateSubtotal();

        tvSubtotal.setText("Subtotal: $" + subtotal);

        productAdapter.notifyDataSetChanged();

        if (lvProductList.getVisibility() == View.GONE) {
            lvProductList.setVisibility(View.VISIBLE);
        }

        if (!productList.isEmpty()) {
            btnTotalize.setEnabled(true);
        }

        resetFields();
    }

    private void recalculateSubtotal() {
        subtotal = 0;
        for (Product product : productList) {
            subtotal += product.getSubtotal();
        }
        tvSubtotal.setText("Subtotal: $" + subtotal);
    }

    private void resetFields() {
        etProductCode.setText("");
        etProductDescription.setText("");
        etProductPrice.setText("");
        currentQuantity = 1;
        tvQuantity.setText(String.valueOf(currentQuantity));
    }

    private Map<String, Integer> getProductCounts() {
        Map<String, Integer> productCounts = new HashMap<>();
        for (Product product : productList) {
            productCounts.put(product.getCode(), product.getQuantity());
        }
        return productCounts;
    }

    public void onTotalizeClick(View view) {
        String issuerName = getIntent().getStringExtra("issuerName");
        String issuerRif = getIntent().getStringExtra("rif");
        String clientFirstName = getIntent().getStringExtra("clientFirstName");
        String clientLastName = getIntent().getStringExtra("clientLastName");
        int clientDni = getIntent().getIntExtra("clientDNI", 0);

        Receipt receipt = new Receipt();
        receipt.setIssuer(new Issuer(issuerName, issuerRif));
        receipt.setClient(new Client(clientFirstName, clientLastName, clientDni));
        receipt.setProductCounts(getProductCounts());
        receipt.setTotal(subtotal);

        Intent intent = new Intent(this, ReceiptActivity.class);
        intent.putExtra("receipt", receipt);
        intent.putExtra("productList", (ArrayList<Product>) productList); // Agregar la lista de productos
        startActivity(intent);
    }

    public void onBackClick(View view) {
        finish();
    }
}
