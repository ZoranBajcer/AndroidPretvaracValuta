package com.bank.zoran.myconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    JSONArray arr;
    Spinner ssItems;
    Spinner sItems;
    int indexTwo;
    int indexOne;
    List<String> spinnerArray;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List of data for display in spinner
        spinnerArray = new ArrayList<>();
        spinnerArray.add("AUD");
        spinnerArray.add("CAD");
        spinnerArray.add("CZK");
        spinnerArray.add("DKK");
        spinnerArray.add("HUF");
        spinnerArray.add("JPY");
        spinnerArray.add("NOK");
        spinnerArray.add("SEK");
        spinnerArray.add("CHF");
        spinnerArray.add("GBP");
        spinnerArray.add("USD");
        spinnerArray.add("BAM");
        spinnerArray.add("EUR");
        spinnerArray.add("PLN");
        spinnerArray.add("HRK");

        adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, spinnerArray);

        //spiner jedan
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.spinnerOne);
        sItems.setAdapter(adapter);

        //spinner dva
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ssItems = (Spinner) findViewById(R.id.spinnerTwo);
        ssItems.setAdapter(adapter);


        new GetJSON().execute();

        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    String aud = arr.getJSONObject(0).getString("Srednji za devize");
                String cad = arr.getJSONObject(1).getString("Srednji za devize");
                String czk = arr.getJSONObject(2).getString("Srednji za devize");
                String dkk = arr.getJSONObject(3).getString("Srednji za devize");
                String huf = arr.getJSONObject(4).getString("Srednji za devize");
                String jpy = arr.getJSONObject(5).getString("Srednji za devize");
                String nok = arr.getJSONObject(6).getString("Srednji za devize");
                String sek = arr.getJSONObject(7).getString("Srednji za devize");
                String chf = arr.getJSONObject(8).getString("Srednji za devize");
                String gbp = arr.getJSONObject(9).getString("Srednji za devize");
                String usd = arr.getJSONObject(10).getString("Srednji za devize");
                String bam = arr.getJSONObject(11).getString("Srednji za devize");
                String eur = arr.getJSONObject(12).getString("Srednji za devize");
                String pln = arr.getJSONObject(13).getString("Srednji za devize");

                String nl = "\n";

                String str ="AUD = "+aud + nl + "CAD = "+cad +nl + "CZK = "+czk + nl + "DKK = "+dkk + nl +"HUF = " +huf
                        + nl + "JPY = "+jpy+nl+"NOK = " + nok + nl + "SEK = "+sek+nl+"CHF = "+chf+nl+"GBP = "+gbp+nl+
                        "USD = "+usd + nl+"BAM = "+bam+nl+ "EUR = "+eur +nl+"PLN = "+pln;

                    Intent in = new Intent(MainActivity.this, Lista.class);
                    in.putExtra("podaci", str);
                    startActivity(in);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                        indexOne = sItems.getSelectedItemPosition();
                        indexTwo = ssItems.getSelectedItemPosition();

                    final EditText status =(EditText)findViewById(R.id.status);

                    TextView tekst = (TextView)findViewById(R.id.textView);

                    if((indexOne == 14) && (indexTwo != 14)) {
                            try {
                                String valutaTwo = arr.getJSONObject(indexTwo).getString("Valuta");
                                String vrijednostTwo = arr.getJSONObject(indexTwo).getString("Srednji za devize");
                                String vrijednostTwoTwo = vrijednostTwo.replaceFirst(",", ".");
                                String textValue = status.getText().toString();
                                double doubleValue = Double.parseDouble(textValue);
                                double valutaDoubleTwo = Double.parseDouble(vrijednostTwoTwo);
                                double sum = 1*doubleValue;
                                double finishSum = sum/valutaDoubleTwo;
                                //solving the problem with too many decimals-setting only two decimals
                                DecimalFormat df = new DecimalFormat("#.00");
                                String formated = (df.format(finishSum));
                                tekst.setText(textValue+" KN =" + formated + " "+valutaTwo);
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this,"Greška - 103",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if((indexTwo == 14)&&(indexOne !=14)) {
                            try {
                                String valutaOne = arr.getJSONObject(indexOne).getString("Valuta");
                                String vrijednostOne = arr.getJSONObject(indexOne).getString("Srednji za devize");
                                String vrijednostOneOne = vrijednostOne.replaceFirst(",", ".");
                                String textValue = status.getText().toString();
                                double doubleValue = Double.parseDouble(textValue);
                                double valutaDoubleOne = Double.parseDouble(vrijednostOneOne);
                                double sum = 1*doubleValue;
                                double finishSum = sum*valutaDoubleOne;
                                //solving the problem with too many decimals-setting only two decimals
                                DecimalFormat df = new DecimalFormat("#.00");
                                String formated = (df.format(finishSum));
                                tekst.setText(textValue+" "+valutaOne +" = "+ formated+" "+"KN");
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this,"Greška - 123",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if((indexOne == indexTwo)&&(indexOne !=14)) {
                            String valutaTwo;
                            try {
                                valutaTwo = arr.getJSONObject(indexTwo).getString("Valuta");
                                String textValue = status.getText().toString();
                                tekst.setText(textValue+" "+valutaTwo );
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this,"Greška - 134",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if((indexOne != 14) && (indexTwo != 14) && (indexOne != indexTwo))
                            try {
                                String valutaOne = arr.getJSONObject(indexOne).getString("Valuta");
                                String vrijednostOne = arr.getJSONObject(indexOne).getString("Srednji za devize");
                                String valutaTwo = arr.getJSONObject(indexTwo).getString("Valuta");
                                String vrijednostTwo = arr.getJSONObject(indexTwo).getString("Srednji za devize");

                                //solving the problem in syntax, replacing comma for dot
                                String vrijednostOneOne = vrijednostOne.replaceFirst(",", ".");
                                String vrijednostTwoTwo = vrijednostTwo.replaceFirst(",", ".");
                                String textValue = status.getText().toString();
                                //parsing the value from string to double
                                double doubleValue = Double.parseDouble(textValue);
                                double valutaDoubleOne = Double.parseDouble(vrijednostOneOne);
                                double valutaDoubleTwo = Double.parseDouble(vrijednostTwoTwo);
                                //simple calculation logic
                                double sum = valutaDoubleOne*doubleValue;
                                double finishSum = sum/valutaDoubleTwo;
                                //solving the problem with too many decimals-setting only two decimals
                                DecimalFormat df = new DecimalFormat("#.00");
                                String formated = (df.format(finishSum));
                                tekst.setText(textValue+ " "+ valutaOne+ " = "+formated+" "+valutaTwo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this,"Greška - 165",Toast.LENGTH_LONG).show();
                            }
                        else if((indexOne == 14)&&(indexTwo == 14)){
                            String textValue = status.getText().toString();
                            tekst.setText("Rezultat:    "+ textValue + "KN");
                        }
                }
                catch(NullPointerException e){
                    Toast.makeText(MainActivity.this,"Greška - 173 \n --provjerite internet vezu--",Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    private class GetJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            URLconnection urlConn = new URLconnection();
            // Making a request to url and getting response
            String url = "http://api.hnb.hr/tecajn/v1";
            //.........connection.........
            String response = urlConn.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + response);
            if (response != null) {
                try {
                     arr = new JSONArray(response);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: 216",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Ups..Something went wrong...",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }
}