package vn.techplus.clickandgo.fragment.viewprices;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.rest.InvokeWS;

/**
 * Created by ThanCV on 10/10/2015.
 */
public class FragmentMyself extends Fragment {

    private TextView mOrigin, mDestination, mDistance, mDuration, mDepartureDate, mDepartureTime, mArrivalTime;
    private TextView mPrice;
    private Button mOrder;
    private String prix;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_self_layout, container, false);

        mOrigin = (TextView) view.findViewById(R.id.tvOrigin);
        mDestination = (TextView) view.findViewById(R.id.tvDestination);
        mDistance = (TextView) view.findViewById(R.id.tvDistance);
        mDuration = (TextView) view.findViewById(R.id.tvDuration);
        mDepartureDate = (TextView) view.findViewById(R.id.tvDepartureDate);
        mDepartureTime = (TextView) view.findViewById(R.id.tvDepartureTime);
        mArrivalTime = (TextView) view.findViewById(R.id.tvArrvivalTime);
        mPrice = (TextView) view.findViewById(R.id.tvPrice);
        mOrder = (Button) view.findViewById(R.id.btnOrder);

        //Get arguments
        final String origin = getArguments().getString("origin");
        final String destination = getArguments().getString("destination");
        final String duration = getArguments().getString("duration");
        final String distance = getArguments().getString("distance");
        final String duration_datetime = getArguments().getString("duration_datetime");
        final String departure_date = getArguments().getString("departure_date");
        final String departure_time = getArguments().getString("departure_time");
        final String arrival_time = getArguments().getString("arrival_time");

        final String departure_time2 = departure_time.substring(0, 5);
        final String arrival_time2 = arrival_time.substring(0, 5);

        //Set text for textview
        mOrigin.setText(origin);
        mDestination.setText(destination);
        mDistance.setText(distance);
        mDuration.setText(duration);
        mDepartureDate.setText(departure_date);
        mDepartureTime.setText(departure_time);
        mArrivalTime.setText(arrival_time);

        //Tariff Calculation
        String distance2 = distance.replace("km", " ").trim();
        String url = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/itinerary/tarif/" + distance2 + "/" + duration_datetime + "/" + departure_date + "/" + departure_time + "/" + arrival_time + "";
        restTariffCalculation(url);

        //set event click for button
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Click And Go")
                        .setMessage("Are you sure want to order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Random random = new Random();
                                int number = random.nextInt(100000000);
                                int skuSearch = random.nextInt(100000000);
                                String taxi_sku = "sku_" + number;

                                RequestParams params = new RequestParams();
                                params.put("taxi_id", "");
                                params.put("taxi_sku", taxi_sku);
                                params.put("status", "reserved");
                                //params.put("status", "paid");
                                params.put("sku_search", skuSearch);
                                params.put("origin", origin);
                                params.put("destination", destination);
                                params.put("duration", duration);
                                params.put("distance", distance);
                                params.put("date", departure_date);
                                params.put("heure", departure_time2);
                                params.put("heure_arrivee_estimee", arrival_time2);
                                String date_heure_depart = departure_date + " " + departure_time2;
                                String date_heure_arrivee_estimee = departure_date + " " + arrival_time2;
                                params.put("date_heure_depart", date_heure_depart);
                                params.put("date_heure_arrivee_estimee", date_heure_arrivee_estimee);
                                params.put("provenance", "Appli mobile_Android");
                                params.put("tarif_final", prix);
                                params.put("nb_passagers", 1);
                                params.put("nb_bagages", 1);
                                params.put("animaux", 1);
                                params.put("encombrant", "Oui");
                                params.put("descriptif", "ThanCV");
                                params.put("commentaire", "Rien");
                                params.put("cree_le", new SimpleDateFormat("hh:mm:ss").format(new Date()));
                                new InvokeWS(getActivity()).restPostOrder(params);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        return view;
    }

    public void restTariffCalculation(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                String str = rawJsonResponse.trim().substring(2, 5);
                String str2 = "404";
                if (str2.equals(str)) {
                    mPrice.setText("Not found");
                } else {
                    try {
                        // Get the full HTTP Data as JSONObject
                        JSONObject jsonObject = new JSONObject(rawJsonResponse);
                        prix = jsonObject.getString("prix");

                        mPrice.setText(prix + " EUR");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

}
