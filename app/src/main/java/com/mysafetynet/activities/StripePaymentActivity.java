package com.mysafetynet.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.mysafetynet.R;
import com.mysafetynet.adapters.CustomSpinnerAdapter;
import com.mysafetynet.customs.CreditCardEdittext;
import com.mysafetynet.utils.OtherCardTextWatcher;
import com.mysafetynet.utils.Util;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StripePaymentActivity extends AppCompatActivity {

    private static final String TAG = StripePaymentActivity.class.getSimpleName();
    @BindView(R.id.edtCreditCardName)
    TextInputEditText edtCreditCardName;
    @BindView(R.id.edtCreditCardNumber)
    CreditCardEdittext edtCreditCardNumber;
    @BindView(R.id.imgbtnMonth)
    ImageButton imgbtnMonth;
    @BindView(R.id.spMonth)
    Spinner spMonth;
    @BindView(R.id.spYear)
    Spinner spYear;
    @BindView(R.id.edtCreditCardCvv)
    TextInputEditText edtCreditCardCvv;
    @BindView(R.id.btnCheckout)
    Button btnCheckout;
    private String publishableKey = "pk_test_K2ZO7DcZcQKuJ4PQjO9XOvRT";
    private String errorMessage = "";
    private ArrayList<String> monthList, yearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);
        ButterKnife.bind(this);
        monthList = new ArrayList<>();
        yearList = new ArrayList<>();
        initMonths();
        setRegisterListener();
    }

    private void setRegisterListener() {
        edtCreditCardNumber.setTextWatcher(new OtherCardTextWatcher(edtCreditCardNumber));
    }

    private void initMonths() {
        for (int i = 1; i < 13; i++) {
            monthList.add(String.valueOf(i));
        }
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i < 2062; i++) {
            yearList.add(String.valueOf(i));
        }
        CustomSpinnerAdapter adap1 = new CustomSpinnerAdapter(this, R.layout.content_spinner_list_item, monthList, getResources().getString(R.string.font_light));
        spMonth.setAdapter(adap1);
        CustomSpinnerAdapter adap = new CustomSpinnerAdapter(this, R.layout.content_spinner_list_item, yearList, getResources().getString(R.string.font_light));
        spYear.setAdapter(adap);
    }

    private void generateToken(Card card) {


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

    @OnClick(R.id.btnCheckout)
    public void onViewClicked() {
        String credit_name = edtCreditCardName.getText().toString().trim();
        String credit_card = edtCreditCardNumber.getText().toString().trim();
        String credit_month = spMonth.getSelectedItem().toString();
        String credit_year = spYear.getSelectedItem().toString();
        String credit_cvv = edtCreditCardCvv.getText().toString().trim();
        if (TextUtils.isEmpty(credit_name)) {
            edtCreditCardName.setError(getString(R.string.errCardName));
            edtCreditCardName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
        } else if (TextUtils.isEmpty(credit_card)) {
            edtCreditCardNumber.setError(getString(R.string.errCardNumber));
            edtCreditCardNumber.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
        } else if (TextUtils.isEmpty(credit_cvv)) {
            edtCreditCardCvv.setError(getString(R.string.errCardCvv));
            edtCreditCardCvv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
        } else if (!Util.isValidCardCvv(credit_cvv)) {
            edtCreditCardCvv.setError(getString(R.string.errCardValidCvv));
            edtCreditCardCvv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
        } else {
            edtCreditCardNumber.setError(null);
            edtCreditCardCvv.setError(null);
            Card card = new Card(credit_card, Integer.parseInt(credit_month), Integer.parseInt(credit_year), credit_cvv);
            if (!card.validateNumber()) {
                edtCreditCardNumber.setError(getString(R.string.errCardValidNumber));
                edtCreditCardNumber.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            } else if (!card.validateCVC()) {
                edtCreditCardCvv.setError(getString(R.string.errCardValidCvvOriginal));
                edtCreditCardCvv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            } else {
                edtCreditCardNumber.setError(null);
                edtCreditCardCvv.setError(null);
                generateToken(card);

            }

        }
    }
}
