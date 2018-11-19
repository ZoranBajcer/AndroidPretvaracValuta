package com.bank.zoran.myconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Lista extends AppCompatActivity {

    TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        String podaci = getIntent().getExtras().getString("podaci");

        myTextView = (TextView)findViewById(R.id.lista);

        myTextView.setText(podaci);
    }
}
