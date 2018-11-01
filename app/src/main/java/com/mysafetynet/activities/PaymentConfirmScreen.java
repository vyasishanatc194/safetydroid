package com.mysafetynet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;

import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyButton;
import com.mysafetynet.customs.MySafetyText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentConfirmScreen extends AppCompatActivity {

    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    @BindView(R.id.txtMessage)
    MySafetyText txtMessage;
    @BindView(R.id.txtTime)
    MySafetyText txtTime;
    @BindView(R.id.txtOrderId)
    MySafetyText txtOrderId;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.btnSubmit)
    MySafetyButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm_screen);
        ButterKnife.bind(this);
        hideItems();
    }

    private void hideItems() {
        imbgBack.setVisibility(View.INVISIBLE);

    }

    @OnClick({R.id.imbgBack, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imbgBack:
                break;
            case R.id.btnSubmit:
                break;
        }
    }
}
