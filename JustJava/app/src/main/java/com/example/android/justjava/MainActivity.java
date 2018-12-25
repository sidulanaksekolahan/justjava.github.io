package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    int price = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called  when the plus button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You can not have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // You can put a ‘return’ keyword inside of an if statement, and that that will end the method early
            // exit from a method
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You can not have less than 1 coffees", Toast.LENGTH_SHORT).show();
            // You can put a ‘return’ keyword inside of an if statement, and that that will end the method early
            // exit from a method
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.cbx_whipped_cream);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameField = (EditText) findViewById(R.id.name_field);

        boolean hasWhippedCream = whippedCreamCheckBox.isChecked(); // nilai sekarang adalah false
        boolean hasChocolate = chocolateCheckBox.isChecked(); // nilai sekarang adalah false
        String name = nameField.getText().toString();

        Log.v("MainActivity", "Has whipped cream : " + hasWhippedCream);
        Log.v("MainActivity", "Has chocolate :" + hasChocolate);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:vanslash@rocketmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(priceMessage);

    }

    public String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        return "Name : " + name + "\nAdd Whipped Cream? " + addWhippedCream + "\nAdd Chocolate ? " +
                addChocolate + "\nQuantity: " +
                quantity + "\nTotal : Rp" + price + "\nThank you";
    }

    /*
     * Method ini dipanggil untuk mengkalkulasi price
     */
    public int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        // tambahkan 1 Rupiah jika user menginginkan whipped cream
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }

        // tambahkan 2 Rupiah jika user menginginkan chocolate
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}