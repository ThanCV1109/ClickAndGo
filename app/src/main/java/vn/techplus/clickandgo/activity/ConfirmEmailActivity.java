package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.techplus.clickandgo.R;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class ConfirmEmailActivity extends Activity {

    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        mBack = (Button) findViewById(R.id.btnBack);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
