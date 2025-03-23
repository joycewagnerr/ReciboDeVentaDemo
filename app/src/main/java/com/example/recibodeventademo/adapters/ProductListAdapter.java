package com.example.recibodeventademo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.recibodeventademo.R;
import com.example.recibodeventademo.data.Product;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private final Context context;
    private final List<Product> products;

    public ProductListAdapter(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        Product product = products.get(position);

        TextView tvProductCode = convertView.findViewById(R.id.tvProductCode);
        TextView tvProductQuantityPrice = convertView.findViewById(R.id.tvProductQuantityPrice);
        TextView tvProductSubtotal = convertView.findViewById(R.id.tvProductSubtotal);
        TextView tvProductDescription = convertView.findViewById(R.id.tvProductDescription);

        tvProductCode.setText("Código: " + product.getCode());
        tvProductQuantityPrice.setText(product.getQuantity() + " x $" + product.getPrice());
        tvProductSubtotal.setText("$" + (product.getQuantity() * product.getPrice()));
        tvProductDescription.setText(product.getDescription());

        return convertView;
    }
}
