package com.example.livecricketapp.user.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.livecricketapp.R;
import com.example.livecricketapp.databinding.ActivityPaymentBinding;
import com.example.livecricketapp.model.AdBanner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityPaymentBinding binding;
    private String TAG = "Payment";
    private String orderId = "order_I1bj0c9wRTIXZ0";
    private AdBanner banner = new AdBanner();
    private FirebaseUser user;
    private FirebaseFirestore db;
    private String date, time;
    private ProgressDialog dialog;
    private String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        user = FirebaseAuth.getInstance().getCurrentUser();
        banner = (AdBanner) getIntent().getSerializableExtra("banner");

        Checkout.preload(getApplicationContext());

        body = "{  \"amount\": " + String.valueOf(banner.getAmountPaid()*100) + ",  \"currency\": \"INR\",  \"receipt\": \"receipt#2\"}";

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setKeyID("rzp_test_vhqEmsYLvrY3RO");
        checkout.setImage(R.drawable.logo);

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Cricket Corner");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(banner.getAmountPaid()));//pass amount in currency subunits
            options.put("prefill.email", "anshtandonlmp@gmail.com");
            options.put("prefill.contact", "8931902676");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(PaymentActivity.this, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    public void checkout(View view) {
        try {
            get_order_id();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

    private void get_order_id() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://api.razorpay.com/v1/orders", new JSONObject(body), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    orderId = response.get("id").toString();
                    Log.d(TAG, orderId);
                    startPayment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Basic cnpwX3Rlc3RfdmhxRW1zWUx2clkzUk86eTZyVFVhSUlHUWsxZTJtTHJZWERXWGNj");
                map.put("content-type", "application/json");
                return map;
            }
        };
        queue.add(request);
    }


    private void create_ad_request( String transactionId )
    {
        banner.setAdId(orderId);
        banner.setTransactionId(transactionId);
        banner.setRequestDate(date);
        banner.setRequestTime(time);
        upload_on_firebase();
    }

    private void upload_on_firebase ()
    {
        db.collection("Ads").document(banner.getAdId()).set(banner);
        Intent intent = new Intent(this , PaymentSuccessful.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentSuccess(String s) {
        create_ad_request(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        finish();
    }
}