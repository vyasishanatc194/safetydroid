package com.mysafetynet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.utils.AppPref;

public class SplashScreenActivity extends AppCompatActivity {

    private AppPref mAppPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppPref = new AppPref(this);


        getFirebaseToken();
        if (mAppPref.isLogged()) {
            if (mAppPref.getUserType().equalsIgnoreCase(ApiConstants.USER_CHILD)) {
                Intent intent = new Intent(SplashScreenActivity.this, ChildTabActivity.class);
                navigateToHome(intent);
            } else {
                Intent intent = new Intent(SplashScreenActivity.this, ParentTabActivity.class);
                navigateToHome(intent);
            }

        } else {
            Intent intent = new Intent(SplashScreenActivity.this, LoginTypeActivity.class);
            startActivity(intent);
        }

    }

    public void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
                mAppPref.setFirebasetoken(newToken);
            }
        });

    }
    public void navigateToHome(Intent intent) {

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
