package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.rest.InvokeWS;
import vn.techplus.clickandgo.util.CheckInput;

/**
 * Created by ThanCV on 10/6/2015.
 */
public class DetailLogInActivity extends Activity {

    private Button mBack;
    private Button mLogIn;
    private EditText mEmail, mPass;
    private String mInputEmail, mInputPass;
    private InvokeWS mInvokeWS;
    private CheckInput mCheckInput;
    private String mUrl;
    private TextView mForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_login);

        //Initialize
        mEmail = (EditText) findViewById(R.id.edLoginEmail);
        mPass = (EditText) findViewById(R.id.edLoginPassword);
        mBack = (Button) findViewById(R.id.btnBack);
        mLogIn = (Button) findViewById(R.id.btnLogin);
        mForgotPass = (TextView) findViewById(R.id.tvForgotPass);

        mInvokeWS = new InvokeWS(this);
        mCheckInput = new CheckInput();

        //set event for buttons
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailLogInActivity.this, ConfirmEmailActivity.class);
                startActivity(intent);
            }
        });

        mLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Hiden key board
                InputMethodManager inputMethodManager = (InputMethodManager) DetailLogInActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(DetailLogInActivity.this.getCurrentFocus().getWindowToken(), 0);

                //gettext
                mInputEmail = mEmail.getText().toString();
                mInputPass = mPass.getText().toString();

                mUrl = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/users/connect/" + mInputEmail + "/" + mInputPass + "";

                //Check input
                boolean checkEmail = mCheckInput.CheckEmail(mInputEmail);
                boolean checkPass = mCheckInput.CheckPassWord(mInputPass);

                //Initialize title notifycation
                String tittleError = "";

                if (!checkEmail) {
                    tittleError += "Email ";
                }
                if (!checkPass) {
                    tittleError += "PassWord ";
                }

                //Check input from user is true or false
                if (!checkEmail || !checkPass) {
                    new AlertDialog.Builder(DetailLogInActivity.this)
                            .setTitle("Log In")
                            .setMessage("Information of " + tittleError + " is incorrect!")
                            .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    //Get data from server
                    mInvokeWS.restGetLogin(mUrl);
                }

            }
        });
    }

}
