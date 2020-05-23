package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;
    String selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedItem = parent.getItemAtPosition(position).toString(); //this is your selected item
                Log.d("Bitcoin","Selected Item is "+selectedItem);
                final String url = BASE_URL + selectedItem;
                Log.d("Bitcoin","Final url is "+url);
                letsDoSomeNetworking(url);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("Bitcoin", "Nothing selected");
            }
        });



    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-ba-key", "ZGM5ZWNkMTkxNjJlNGRiNmExNTczNWFmMGNhYjczM2I");
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());
                PriceDataController priceData = PriceDataController.parseJson(response);
                String price = String.valueOf(priceData.getPrice());
                Log.d("Bitcoin","Price is "+price);
                mPriceTextView.setText(price);
//                updateUI(weatherData);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }


        });


    }


}
