package com.example.recibodeventademo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recibodeventademo.ui.InputClientDataActivity;
import com.example.recibodeventademo.ui.ViewReceiptsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void onCreateReceiptClick(View view) {
        Intent intent = new Intent(MainActivity.this, InputClientDataActivity.class);
        startActivity(intent);
    }

    public void onViewReceiptsClick(View view) {
        Intent intent = new Intent(MainActivity.this, ViewReceiptsActivity.class);
        startActivity(intent);
    }
}