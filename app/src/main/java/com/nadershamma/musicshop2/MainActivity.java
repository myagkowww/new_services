package com.nadershamma.musicshop2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int quantity = 0;
    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter;

    HashMap goodsMap;
    String goodsName;
    double price;
    EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.nameEditText);
        createSpinner();
        createMap();
    }

    public void click(View v) {
        Intent i=new Intent(this, MediaService.class);
        if (v.getId()==R.id.start) {
            startService(i);
        }
        else {
            stopService(i);
        }
    }
    void createSpinner(){
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerArrayList = new ArrayList();

        spinnerArrayList.add("guitar");
        spinnerArrayList.add("drums");
        spinnerArrayList.add("keyboard");
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }
    void createMap(){
        goodsMap = new HashMap();
        goodsMap.put("guitar", 500.0);
        goodsMap.put("drums", 1500.0);
        goodsMap.put("keyboard", 1000.0);
    }
    public void increaseQuantity(View view){
        quantity = quantity + 1;
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);
    }
    public void decreaseQuantity(View view){
        quantity = quantity - 1;
        if(quantity < 0){
            quantity = 0;
        }
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);
    }
    public  void onItemSelected(AdapterView<?> parent, View view, int position, long iol){
        goodsName = spinner.getSelectedItem().toString();
        price = (double) goodsMap.get(goodsName);
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText("" + quantity * price);
        ImageView goodsImageView = findViewById(R.id.img);
        switch (goodsName){
            case "guitar":
                goodsImageView.setImageResource(R.drawable.guitar);
                break;
            case "drums":
                goodsImageView.setImageResource(R.drawable.drums);
                break;
            case "keyboard":
                goodsImageView.setImageResource(R.drawable.keyboard);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.guitar);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    public void addToCart(View view){
        Order order = new Order();

        order.userName = userNameEditText.getText().toString();

        order.goodsName = goodsName;

        order.quantity = quantity;

        order.orderPrice = quantity * price;

        Intent orderIntent;
        orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userNameForIntent",order.userName);
        orderIntent.putExtra("goodsName",order.goodsName);
        orderIntent.putExtra("quantity",order.quantity);
        orderIntent.putExtra("orderPrice",order.orderPrice);
        startActivity(orderIntent);
    }
}