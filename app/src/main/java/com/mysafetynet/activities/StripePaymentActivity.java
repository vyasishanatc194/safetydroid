package com.mysafetynet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mysafetynet.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.Calendar;

public class StripePaymentActivity extends AppCompatActivity {

    private static final String TAG = StripePaymentActivity.class.getSimpleName();
    private String publishableKey = "pk_test_K2ZO7DcZcQKuJ4PQjO9XOvRT";
    private String errorMessage = "";
    private ArrayList<String> monthList, yearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);
        monthList = new ArrayList<>();
        yearList = new ArrayList<>();
        initMonths();
    }

    private void initMonths() {
        for (int i = 1; i < 13; i++) {
            monthList.add(String.valueOf(i));
        }
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i < 2062; i++) {
            yearList.add(String.valueOf(i));
        }
    }

    private void generateToken() {
        String cardNumber = "";
        int month = 1;
        int year = 2021;
        String cvc = "131";

        Card card = new Card(cardNumber, month, year, cvc);

        Stripe stripe = new Stripe(this);
        try {
            stripe.createToken(card, publishableKey, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    error.printStackTrace();
                    Log.e(TAG, "onError: " + error.getMessage());
                }

                @Override
                public void onSuccess(Token token) {
                    Log.e(TAG, "onSuccess: " + token.getId());
                }
            });
        } catch (Exception stripeEx) {
            errorMessage = stripeEx.getMessage();
        }
    }
}
