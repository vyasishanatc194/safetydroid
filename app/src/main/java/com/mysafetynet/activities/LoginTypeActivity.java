package com.mysafetynet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginTypeActivity extends AppCompatActivity {

    @BindView(R.id.btParentLogin)
    MySafetyText btParentLogin;
    @BindView(R.id.btChildLogin)
    MySafetyText btChildLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btParentLogin, R.id.btChildLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btParentLogin:
                Intent Parent=new Intent(LoginTypeActivity.this,ParentLoginActivity.class);
                startActivity(Parent);
                break;
            case R.id.btChildLogin:
                Intent Child=new Intent(LoginTypeActivity.this,ChildLoginActivity.class);
                startActivity(Child);
                break;
        }
    }
}
