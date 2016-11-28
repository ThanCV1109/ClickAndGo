package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import vn.techplus.clickandgo.R;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class ModifyPassengerActivity extends Activity {
    private Spinner mSpinner;
    private TextView mCancel;
    private EditText mFirsName, mLastName, mEmail;
    private String arrGender[] = {"Male", "Female"};
    private static final String KEY = "userLI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_passenger);

        mCancel = (TextView) findViewById(R.id.btnCancel);
        mSpinner = (Spinner) findViewById(R.id.spnGender);
        mFirsName = (EditText) findViewById(R.id.edFirstName);
        mLastName = (EditText) findViewById(R.id.edLastName);
        mEmail = (EditText) findViewById(R.id.edEmail);

        handleData();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrGender);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinner.setAdapter(adapter);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void handleData() {
        //Get data from LogIn
        SharedPreferences myprefsLg = getSharedPreferences(KEY, MODE_PRIVATE);
        String firstname = myprefsLg.getString("firstname", "");
        String lastname = myprefsLg.getString("lastname", "");
        String email = myprefsLg.getString("email", "");

        //Set data for name and email from LogIn
        mFirsName.setText(firstname);
        mLastName.setText(lastname);
        mEmail.setText(email);
    }

}


