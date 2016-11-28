package vn.techplus.clickandgo.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import vn.techplus.clickandgo.activity.DetailLogInActivity;
import vn.techplus.clickandgo.activity.GoNowMainActivity;
import vn.techplus.clickandgo.activity.MainActivity;
import vn.techplus.clickandgo.activity.SubGoNowActivity;
import vn.techplus.clickandgo.activity.ViewPricesActivity;
import vn.techplus.clickandgo.database.DatabaseHandle;
import vn.techplus.clickandgo.model.Favorite;
import vn.techplus.clickandgo.util.MyAlertDiaglog;

/**
 * Created by ThanCV on 10/21/2015.
 */
public class InvokeWS {
    private final int DEFAULT_TIMEOUT = 120 * 1000;
    private Context context;
    private ProgressDialog progressDialog;
    private final String MY_KEY = "AIzaSyA97zfY3G-Ok0bCsFJKzi5i0jMONMDP_eI";
    private String urlPostSignUp = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/users/create";
    private String urlPostOrder = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/orders/reservations/create";
    private String urlCreateFavorites = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/favorites/create";
    private String urlFavoriteUserId = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/user/favorites/";
    private String urlHistoryReservation = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/taxis/reservations/";
    private final String TITLE = "Log In";
    private final String TITLE2 = "Sign Up";
    private final String TITLE3 = "View Prices";
    private final String TITLE4 = "Check Address";
    private final String TITLE5 = "Oder";
    private final String MESSAGE = "Information is incorrect!";
    private final String MESSAGE2 = "No internet connection!";
    private final String MESSAGE3 = "Acount already exists!";
    private final String MESSAGE4 = "Url not found!";
    private MyAlertDiaglog myAlertDiaglog;

    public InvokeWS(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        myAlertDiaglog = new MyAlertDiaglog(context);
    }

    //Post params to server for SignUp
    public void restPostSignup(RequestParams params) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);

        client.post(urlPostSignUp, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                String str = rawJsonResponse.trim().substring(1, 6);
                String str2 = "false";
                if (str2.equals(str)) {
                    myAlertDiaglog.showAlert(TITLE2, MESSAGE3);
                } else {
                    Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, DetailLogInActivity.class);
                    context.startActivity(intent);
                }
                Log.e("response", "r" + rawJsonResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                myAlertDiaglog.showAlert(TITLE2, MESSAGE2);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //Post params to server for Order
    public void restPostOrder(RequestParams params) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);

        client.post(urlPostOrder, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                String status = rawJsonResponse.substring(1, 6);
                if ("false".equals(status)) {
                    Toast.makeText(context, "Order fail", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    Toast.makeText(context, "Order successful", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                myAlertDiaglog.showAlert(TITLE5, MESSAGE2);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //Get data login from server
    public void restGetLogin(String url) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);
        client.get(url, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                String str = rawJsonResponse.trim().substring(1, 6);
                String str2 = "false";
                if (str2.equals(str)) {
                    myAlertDiaglog.showAlert(TITLE, MESSAGE);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(rawJsonResponse);
                        String firstname = jsonObject.getString("firstname");
                        String lastname = jsonObject.getString("lastname");
                        String email = jsonObject.getString("email");
                        String gender = jsonObject.getString("prefix");
                        String customerId = jsonObject.getString("customer_id");

                        //If connect to server success then intent to MainActivity
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("firstname", firstname);
                        intent.putExtra("lastname", lastname);
                        intent.putExtra("email", email);
                        intent.putExtra("gender", gender);
                        intent.putExtra("customer_id", customerId);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                if (statusCode == 404) {
                    myAlertDiaglog.showAlert(TITLE, "Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    myAlertDiaglog.showAlert(TITLE, "Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    myAlertDiaglog.showAlert(TITLE, MESSAGE2);
                }
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //Get data Distance from server
    public void restGetDistance(String url) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);
        client.get(url, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                String str = rawJsonResponse.trim().substring(2, 6);
                String str2 = "code";
                if (str2.equals(str)) {
                    myAlertDiaglog.showAlert(TITLE3, MESSAGE4);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(rawJsonResponse);
                        String origin = jsonObject.getString("origin");
                        String destination = jsonObject.getString("destination");
                        String distance = jsonObject.getString("distance");
                        String distance_sans_km = jsonObject.getString("distance_sans_km");
                        String duration = jsonObject.getString("duration");
                        String duration_datetime = jsonObject.getString("duration_datetime");
                        String departure_date = jsonObject.getString("departure_date");
                        String departure_time = jsonObject.getString("departure_time");
                        String arrival_time = jsonObject.getString("arrival_time");

                        //If connect to server success then intent to MainActivity
                        Intent intent = new Intent(context, ViewPricesActivity.class);
                        intent.putExtra("origin", origin);
                        intent.putExtra("destination", destination);
                        intent.putExtra("distance", distance);
                        intent.putExtra("duration", duration);
                        intent.putExtra("duration_datetime", duration_datetime);
                        intent.putExtra("departure_date", departure_date);
                        intent.putExtra("departure_time", departure_time);
                        intent.putExtra("arrival_time", arrival_time);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                if (statusCode == 404) {
                    myAlertDiaglog.showAlert(TITLE3, "Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    myAlertDiaglog.showAlert(TITLE3, "Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    myAlertDiaglog.showAlert(TITLE3, "Error!");
                }
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //Get address location of start and end
    public void restGetAddress(String url) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);
        client.get(url, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                String str = rawJsonResponse.trim().substring(2, 5);
                String str2 = "404";
                if (str2.equals(str)) {
                    myAlertDiaglog.showAlert(TITLE4, MESSAGE4);
                } else {
                    try {
                        // Get the full HTTP Data as JSONObject
                        JSONObject reader = new JSONObject(rawJsonResponse);

                        JSONArray routesArray = reader.getJSONArray("routes");
                        // Grab the first route
                        JSONObject route = routesArray.getJSONObject(0);
                        // Take all legs from the route
                        JSONArray legs = route.getJSONArray("legs");
                        // Grab first leg
                        JSONObject leg = legs.getJSONObject(0);

                        JSONObject locationAObject = leg.getJSONObject("start_location");
                        double latitudeA = locationAObject.getDouble("lat");
                        double longitudeA = locationAObject.getDouble("lng");

                        JSONObject locationBObject = leg.getJSONObject("end_location");
                        double latitudeB = locationBObject.getDouble("lat");
                        double longitudeB = locationBObject.getDouble("lng");

                        String start_address = leg.getString("start_address");
                        String end_address = leg.getString("end_address");

                        //If connect to server success then intent to SubGoNowActivity
                        Intent intent = new Intent(context, SubGoNowActivity.class);
                        intent.putExtra("start_address", start_address);
                        intent.putExtra("end_address", end_address);

                        intent.putExtra("latitudeA", latitudeA);
                        intent.putExtra("longitudeA", longitudeA);

                        intent.putExtra("latitudeB", latitudeB);
                        intent.putExtra("longitudeB", longitudeB);

                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                if (statusCode == 404) {
                    myAlertDiaglog.showAlert(TITLE4, "Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    myAlertDiaglog.showAlert(TITLE4, "Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    myAlertDiaglog.showAlert(TITLE4, "Error!");
                }
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //Get LogLat from google place API detail
    public void restGetLogLatDetail(String id) {
        String urlDetail = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + id + "&key=" + MY_KEY + "";
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);
        client.get(urlDetail, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                String str = rawJsonResponse.trim().substring(2, 5);
                String str2 = "404";
                if (str2.equals(str)) {
                    myAlertDiaglog.showAlert(TITLE4, MESSAGE4);
                } else {
                    try {
                        // Get the full HTTP Data as JSONObject
                        JSONObject reader = new JSONObject(rawJsonResponse);

                        JSONObject result = reader.getJSONObject("result");
                        JSONObject geometry = result.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        String lat = location.getString("lat");
                        String lng = location.getString("lng");

                        SharedPreferences myprefs = context.getSharedPreferences("LatLng", context.MODE_PRIVATE);
                        myprefs.edit().putString("lat", lat).commit();
                        myprefs.edit().putString("lng", lng).commit();

                        Intent intent = new Intent(context, GoNowMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                if (statusCode == 404) {
                    myAlertDiaglog.showAlert(TITLE4, "Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    myAlertDiaglog.showAlert(TITLE4, "Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    myAlertDiaglog.showAlert(TITLE4, "Error!");
                }
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //Create favorites
    public void restPostFavorites(RequestParams params) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);

        client.post(urlCreateFavorites, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

                Toast.makeText(context, "Create a successful favorite", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                Toast.makeText(context, rawJsonData, Toast.LENGTH_LONG).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    //Get detail favorites by user id from server (#7,8)
    public void restGetDetailFavorite(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);
        client.get(urlFavoriteUserId + id, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                try {
                    JSONArray jsonArray = new JSONArray(rawJsonResponse);
                    Favorite favorite = null;
                    DatabaseHandle databaseHandle = new DatabaseHandle(context);
                    SQLiteDatabase database = databaseHandle.getReadableDatabase();
                    databaseHandle.deleteDataFavorite(database);

                    int len = jsonArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);
                        favorite = new Favorite();
                        favorite.setId(reader.getString("id"));
                        favorite.setUserId(reader.getString("user_id"));
                        favorite.setRefTaxi(reader.getString("ref_taxi"));
                        favorite.setReservationId(reader.getString("reservation_id"));
                        favorite.setOrigin(reader.getString("origin"));
                        favorite.setDestination(reader.getString("destination"));
                        favorite.setCreatedDate(reader.getString("created_date"));
                        favorite.setUpdatedDate(reader.getString("updated_date"));

                        if (reader.getString("id") != null) {
                            databaseHandle.insertFavorite(favorite);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    //Get History by id
    public void restGetReservationHistory(String userId) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(DEFAULT_TIMEOUT);
        client.get(urlHistoryReservation + userId, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                progressDialog.hide();
                try {
                    JSONArray jsonArray = new JSONArray(rawJsonResponse);
                    Favorite favorite = null;
                    DatabaseHandle databaseHandle = new DatabaseHandle(context);
                    SQLiteDatabase database = databaseHandle.getReadableDatabase();
                    databaseHandle.deleteDataReservation(database);

                    int len = jsonArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);
                        favorite = new Favorite();
                        favorite.setOrigin(reader.getString("origin"));
                        favorite.setDestination(reader.getString("destination"));
                        favorite.setDuration(reader.getString("duration"));
                        favorite.setDistance(reader.getString("distance"));
                        favorite.setDate(reader.getString("date"));
                        favorite.setTarifFinal(reader.getString("tarif_final"));

                        if (reader.getString("origin") != null) {
                            databaseHandle.insertReservation(favorite);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                progressDialog.hide();
                if (statusCode == 404) {
                    myAlertDiaglog.showAlert(TITLE, "Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    myAlertDiaglog.showAlert(TITLE, "Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    myAlertDiaglog.showAlert("Internet Connection", MESSAGE2);
                }
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

}