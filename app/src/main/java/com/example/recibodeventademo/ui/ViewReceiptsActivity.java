package com.example.recibodeventademo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recibodeventademo.R;
import com.example.recibodeventademo.data.Receipt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ViewReceiptsActivity extends AppCompatActivity {
    private ListView lvReceipts;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipts);

        lvReceipts = findViewById(R.id.lvReceipts);
        btnExit = findViewById(R.id.btnExit);

        List<Receipt> receipts = loadReceipts();

        List<String> receiptSummaries = new ArrayList<>();
        for (Receipt receipt : receipts) {
            int totalItems = 0;
            for (Map.Entry<String, Integer> entry : receipt.getProductCounts().entrySet()) {
                totalItems += entry.getValue();
            }

            String summary = "Cliente: " + receipt.getClient().getFullName() +
                    "\nEmisor: " + receipt.getIssuer().getName() +
                    "\nCantidad de ítems: " + totalItems +
                    "\nMonto total: $" + receipt.getTotal();

            receiptSummaries.add(summary);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, receiptSummaries);
        lvReceipts.setAdapter(adapter);
    }

    private List<Receipt> loadReceipts() {
        List<Receipt> receipts = new ArrayList<>();
        try {
            String[] files = fileList();
            Gson gson = new Gson();

            for (String fileName : files) {
                if (fileName.startsWith("receipt_")) {
                    FileInputStream fis = openFileInput(fileName);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();

                    Type receiptType = new TypeToken<Receipt>() {}.getType();
                    Receipt receipt = gson.fromJson(sb.toString(), receiptType);

                    if (receipt != null) {
                        receipts.add(receipt);
                    } else {
                        System.err.println("Error: JSON inválido en el archivo " + fileName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar los recibos", Toast.LENGTH_SHORT).show();
        }
        return receipts;
    }

    public void onExitClick(View view) {
        finish();
    }
}
