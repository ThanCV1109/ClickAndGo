package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.techplus.clickandgo.R;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class LoginSignupActivity extends Activity {

    private Button mLogin;
    private Button mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        mLogin = (Button) findViewById(R.id.btnLogin);
        mSignup = (Button) findViewById(R.id.btnFreeSignUp);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignupActivity.this, DetailLogInActivity.class);
                startActivity(intent);
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignupActivity.this, DetailSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
