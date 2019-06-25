package com.example.spincarsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mSearchEditText;
    private String mQuery;
    private EditText mPrimeNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchEditText = findViewById(R.id.search_bar_edittext);
        Button button = findViewById(R.id.button_search);
        mPrimeNumberEditText = findViewById(R.id.prime_number_edittext);


        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mQuery = mSearchEditText.getText().toString();
                isPrime();
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuery = mSearchEditText.getText().toString();
                isPrime();
            }
        });
    }

    public void isPrime() {
        String primeString = mPrimeNumberEditText.getText().toString();
        if (mQuery.isEmpty()) {
            Toast.makeText(MainActivity.this, "Box cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            int primeNumber;
            if (primeString.isEmpty()) {
                primeNumber = 0;
                Toast.makeText(MainActivity.this, "Box cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                primeNumber = Integer.parseInt(primeString);
            }
            if (primeNumber <= 2) {
                Toast.makeText(MainActivity.this, "Please input a prime number", Toast.LENGTH_SHORT).show();
            } else if (mQuery.isEmpty()) {
                Toast.makeText(MainActivity.this, "Box cannot be empty", Toast.LENGTH_SHORT).show();
            }
            for (int i = 2; i < primeNumber; i++) {
                if (primeNumber % i == 0) {
                    Toast.makeText(MainActivity.this, "Please input a prime number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("primeNumber", primeNumber);
                    extras.putString("query", mQuery);
                    intent.putExtras(extras);
                    startActivity(intent);
                    break;
                }
            }
        }
    }
}


