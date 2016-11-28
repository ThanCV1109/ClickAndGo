package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.techplus.clickandgo.R;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class NewCreditCardActivity extends Activity {

    private Spinner spinnerDate, spinnerMonth, spinnerYear, spinnerType;
    private List<String> listYear;
    private List<String> listMonth;
    private List<String> listDate;
    private List<String> listType;
    private int thisYear;
    private TextView mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_credit_card);

        //Initialize
        mCancel = (TextView) findViewById(R.id.btnCancel);

        thisYear = Calendar.getInstance().get(Calendar.YEAR);
        listYear = new ArrayList<String>();
        listMonth = new ArrayList<String>();
        listDate = new ArrayList<String>();
        listType = new ArrayList<String>();

        spinnerType = (Spinner) findViewById(R.id.spnType);
        spinnerDate = (Spinner) findViewById(R.id.spnDate);
        spinnerMonth = (Spinner) findViewById(R.id.spnMonth);
        spinnerYear = (Spinner) findViewById(R.id.spnYear);

        //add implement for lists
        listYear.add("Year");
        for (int i = thisYear - 5; i <= thisYear + 85; i++) {
            listYear.add(String.valueOf(i + ""));
        }

        listMonth.add("Month");
        for (int j = 1; j <= 12; j++) {
            listMonth.add(String.valueOf(j + ""));
        }

        listDate.add("Date");
        for (int f = 1; f <= 31; f++) {
            listDate.add(String.valueOf(f + ""));
        }

        String type1 = "Type 1";
        String type2 = "Type 2";
        listType.add(type1);
        listType.add(type2);

        ArrayAdapter adapterYear = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listYear);
        adapterYear.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerYear.setAdapter(adapterYear);

        ArrayAdapter adapterMonth = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listMonth);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerMonth.setAdapter(adapterMonth);

        ArrayAdapter adapterDate = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listDate);
        adapterDate.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerDate.setAdapter(adapterDate);

        ArrayAdapter adapterType = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listType);
        adapterType.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerType.setAdapter(adapterType);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
