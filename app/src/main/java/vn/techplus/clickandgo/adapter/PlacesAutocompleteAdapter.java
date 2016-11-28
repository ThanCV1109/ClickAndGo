package vn.techplus.clickandgo.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import vn.techplus.clickandgo.model.AddressAutoComplete;

/**
 * Created by ThanCV on 10/26/2015.
 */
public class PlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private static final String LOG_TAG = "ExampleApp";
    private static final String PLACES_API = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyA97zfY3G-Ok0bCsFJKzi5i0jMONMDP_eI";

    public ArrayList<AddressAutoComplete> resultList;
    private static IHandleAddressAutoComplete handleAddressAutoComplete;

    public PlacesAutocompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.handleAddressAutoComplete = (IHandleAddressAutoComplete) context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index).getAddressName();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }


    public static ArrayList<AddressAutoComplete> autocomplete(String input) {
        ArrayList<AddressAutoComplete> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            Log.e("URL: ", "" + url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            resultList = new ArrayList<AddressAutoComplete>(predsJsonArray.length());
            AddressAutoComplete autoComplete;
            for (int i = 0; i < predsJsonArray.length(); i++) {
                autoComplete = new AddressAutoComplete();
                JSONObject placeId = predsJsonArray.getJSONObject(i);
                autoComplete.setPlaceId(placeId.getString("place_id"));
                autoComplete.setAddressName(predsJsonArray.getJSONObject(i).getString("description"));
                resultList.add(autoComplete);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }
        //add data for list of interface
        handleAddressAutoComplete.getListAddress(resultList);

        return resultList;
    }

    //Send list for SearchAddressActivity
    public interface IHandleAddressAutoComplete {
        void getListAddress(ArrayList<AddressAutoComplete> list);
    }
}