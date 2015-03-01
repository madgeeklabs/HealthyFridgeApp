package com.madgeeklabs.healthfridge.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.madgeeklabs.healthfridge.R;
import com.madgeeklabs.healthfridge.api.HealthFridgeApi;
import com.madgeeklabs.healthfridge.models.Item;
import com.madgeeklabs.healthfridge.utils.Credentials;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HealthStatus.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HealthStatus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthStatus extends Fragment implements Callback<List<Item>> {

    private static final String TAG = HealthStatus.class.getName();
    private OnFragmentInteractionListener mListener;
    private HealthFridgeApi api;
    @InjectView(R.id.chart) PieChart chart;
    @InjectView(R.id.calorieschart)
    HorizontalBarChart caloriestChart;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment healthStatus.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthStatus newInstance(String param1, String param2) {
        HealthStatus fragment = new HealthStatus();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HealthStatus() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Credentials.SERVER)
                .build();

        api = adapter.create(HealthFridgeApi.class);

        api.getMyConsumptions("123", this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_health_status, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void success(List<Item> items, Response response) {
        Log.d(TAG, "" + items.toString());

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("CALORIES TODAY");
        xVals.add("MAX PER DAY");

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(400, 0));
        yVals1.add(new BarEntry(2000, 1));

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData dataBar = new BarData(xVals, dataSets);
        dataBar.setValueTextSize(10f);

        set1.setColors(colors);

        caloriestChart.setMaxVisibleValueCount(2000);

        caloriestChart.setData(dataBar);

        caloriestChart.animateY(1000);

        caloriestChart.invalidate();

        /// CALORIES CHART

        int globalCalories = 0;
        int globalFat = 0;
        int globalProteins = 0;
        int globalColesterol = 0;
        int globalCarbohidrates = 0;

        for (Item item : items) {
            int calories = Integer.parseInt(item.getNutritionFacts().getCalories());
            globalCalories = globalCalories + calories;
            int fat = Integer.parseInt(item.getNutritionFacts().getFat());
            globalFat = globalFat + fat;
            int proteins = Integer.parseInt(item.getNutritionFacts().getProteins());
            globalProteins += proteins;
            int cholesterol = Integer.parseInt(item.getNutritionFacts().getCholesterol());
            globalColesterol += cholesterol;
            int carbohidrates = Integer.parseInt(item.getNutritionFacts().getCarbohidrates());
            globalCarbohidrates += carbohidrates;
        }

        ArrayList<Entry> data = new ArrayList<Entry>();
        data.add(new Entry(globalCarbohidrates,0));
        data.add(new Entry(globalFat, 1));
        data.add(new Entry(globalProteins,2));
        data.add(new Entry(globalColesterol, 3));

        ArrayList<String> fields = new ArrayList<String>();

        fields.add("FAT");
        fields.add("PROTEINS");
        fields.add("CHOLESTEROL");
        fields.add("CARBOHYDRATES");

        PieDataSet dataSet = new PieDataSet(data, "YOUR DAILY NUTRITIONAL FACTS");
        dataSet.setSliceSpace(3f);

        dataSet.setValueTextSize(12f);

        dataSet.setColors(colors);

        PieData dataForPie = new PieData(fields, dataSet);

        chart.setData(dataForPie);

        chart.invalidate();

        chart.animateXY(1800, 1800);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d(TAG, "" + error.getMessage());

        ArrayList<Entry> data = new ArrayList<Entry>();
        data.add(new Entry(12,0));
        data.add(new Entry(29, 1));
        data.add(new Entry(29, 2));
        data.add(new Entry(2, 3));
        data.add(new Entry(45, 1));

        ArrayList<String> fields = new ArrayList<String>();

        fields.add("CALORIES");
        fields.add("FAT");
        fields.add("PROTEINS");
        fields.add("CHOLESTEROL");
        fields.add("CARBOHYDRATES");

        PieDataSet dataSet = new PieDataSet(data, "Election Results");
        dataSet.setSliceSpace(3f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        dataSet.setValueTextSize(12f);

        dataSet.setColors(colors);

        PieData dataForPie = new PieData(fields, dataSet);

        chart.setData(dataForPie);

        chart.invalidate();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
