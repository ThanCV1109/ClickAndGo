package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.rest.InvokeWS;
import vn.techplus.clickandgo.util.CheckInput;

/**
 * Created by ThanCV on 10/6/2015.
 */
public class DetailSignUpActivity extends Activity {

    private String[] arrGender;
    private String[] arrCode;
    private Spinner mSpinnerGender;
    private Button mBack;
    private Button mSignUp;
    private EditText mFirstName, mLastName, mEmail, mPass;
    private CheckInput mCheckInput;
    private InvokeWS mInvokeWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_signup);

        //Initialize
        arrGender = new String[]{"Male", "Female"};
        mSpinnerGender = (Spinner) findViewById(R.id.spnGender);
        mBack = (Button) findViewById(R.id.btnBack);
        mSignUp = (Button) findViewById(R.id.btnSignUp);
        mFirstName = (EditText) findViewById(R.id.edFirstName);
        mLastName = (EditText) findViewById(R.id.edLastName);
        mEmail = (EditText) findViewById(R.id.edEmail);
        mPass = (EditText) findViewById(R.id.edPassword);
        mCheckInput = new CheckInput();
        mInvokeWS = new InvokeWS(this);

        //Set adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrGender);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinnerGender.setAdapter(adapter);

        //set event for buttons
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Hiden key board
                InputMethodManager inputMethodManager = (InputMethodManager) DetailSignUpActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(DetailSignUpActivity.this.getCurrentFocus().getWindowToken(), 0);

                //get text from editexts
                String inputGender = mSpinnerGender.getSelectedItem().toString();
                String inputFirstName = mFirstName.getText().toString();
                String inputLastName = mLastName.getText().toString();
                String inputEmail = mEmail.getText().toString();
                String inputPass = mPass.getText().toString();

                //check input
                boolean checkFirstName = mCheckInput.isNotNull(inputFirstName);
                boolean checkLastName = mCheckInput.isNotNull(inputLastName);
                boolean checkEmail = mCheckInput.CheckEmail(inputEmail);
                boolean checkPass = mCheckInput.CheckPassWord(inputPass);

                String tittleError = "";

                if (!checkFirstName) {
                    tittleError += "FirstName ";
                }
                if (!checkLastName) {
                    tittleError += "LastName ";
                }
                if (!checkEmail) {
                    tittleError += "Email ";
                }
                if (!checkPass) {
                    tittleError += "PassWord ";
                }

                //Check input from user is true or false
                if (!checkFirstName || !checkLastName || !checkEmail || !checkPass) {
                    new AlertDialog.Builder(DetailSignUpActivity.this)
                            .setTitle("Sing Up")
                            .setMessage("Information of " + tittleError + " is incorrect!")
                            .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    //Set params to send to server
                    RequestParams params = new RequestParams();
                    params.put("email", inputEmail);
                    params.put("firstname", inputFirstName);
                    params.put("lastname", inputLastName);
                    params.put("password", inputPass);
                    params.put("website_id", 1);
                    params.put("store_id", 1);
                    params.put("group_id", 1);
                    params.put("gender", inputGender);

                    //Connect to server
                    mInvokeWS.restPostSignup(params);
                }
            }
        });
    }
}
