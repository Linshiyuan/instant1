package com.example.linsh.instant1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int number = 2;

    boolean addCream = false;
    boolean addChocolate = false;
    String name = "";
    String messgeDisplay="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void checkboxClicked(View view) {

        boolean checked = ( (CheckBox) view ).isChecked();
        switch (view.getId()) {
            case R.id.checkBoxCream:
                if (checked)
                    addCream = true;
                else
                    addCream = false;
                break;
            case R.id.checkBoxChocolate:
                if (checked)
                    addChocolate = true;
                else
                    addChocolate = false;
                break;
        }

    }


    public void Button(View view) {
        switch (view.getId()) {
            case R.id.buttonMinus:
                number -= 1;
                if (number < 0){
                    number = 0;
                    toastDisplay("Can't be negative");
                }
                display(number);
                break;
            case R.id.buttonPlus:
                number += 1;
                if(number>10){
                    number=10;
                    toastDisplay("Too much");
                }
                display(number);
                break;
            case R.id.buttonOrder:
                getName();
                int price = caculatePrice(5);
                 messgeDisplay = creatOrderSummary(name, price, addCream, addChocolate);
                displayMessage(messgeDisplay);
                break;
            case R.id.button_sendEmail:
                Log.i("MainActivity","button send has been pressed");
                String subject="Java coffe for "+name;
                String text=messgeDisplay;
                composeEmail(subject,text);

        }
    }


    public void composeEmail(String subject, String text) {
        Log.i("mainActivity","get in composeEmail");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            Log.i("main","date correct email send");
        }


    }

    private  void getName(){
        EditText edte=(EditText)findViewById(R.id.editText);
        name=edte.getText().toString();
    }
    private int caculatePrice(int pricePerCpu) {
        pricePerCpu += ( addCream ? 1 : 0 ) + ( addChocolate ? 1 : 0 )*2;
        int price = number * pricePerCpu;
        return price;
    }

    private String creatOrderSummary(String name, int price, boolean addCream, boolean addChocolate) {
        String summary = "Name: " + name + "\n" +
                "Add whipped cream? " + addCream + "\n" +
                "Add chocolate? " + addChocolate + "\n" +
                "Quantity: " + number + "\n" +
                "Total:$ " + price + "\n" +
                "Thank you!";
        return summary;
    }


    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.NumberOrder);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.orderSummary);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.orderSummary);
        priceTextView.setText(message);
    }

    private  void toastDisplay(String str){
        Context context = getApplicationContext();
        CharSequence text=str;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

}
