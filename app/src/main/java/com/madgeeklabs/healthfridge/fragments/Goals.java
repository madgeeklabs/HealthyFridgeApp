package com.madgeeklabs.healthfridge.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.madgeeklabs.healthfridge.R;
import com.madgeeklabs.healthfridge.api.HealthFridgeApi;
import com.madgeeklabs.healthfridge.models.Item;
import com.madgeeklabs.healthfridge.shared.HealthFridgeShared;
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
 * {@link Goals.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Goals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Goals extends Fragment implements Callback<List<Item>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = Goals.class.getName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private HealthFridgeApi api;
    @InjectView(R.id.objetiveschart)
    BarLineChartBase caloriestChart;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Goals.
     */
    // TODO: Rename and change types and number of parameters
    public static Goals newInstance(String param1, String param2) {
        Goals fragment = new Goals();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Goals() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        View v = inflater.inflate(R.layout.fragment_goals, container, false);
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
        HealthFridgeShared shared = new HealthFridgeShared(getActivity());

        ArrayList<Integer> colors = new ArrayList<Integer>();

        int globalCalories = 0;
        int globalFat = 0;
        int globalProteins = 0;
        int globalColesterol = 0;
        int globalCarbohidrates = 0;

        for (Item item : items) {
            int calories = Integer.parseInt(item.getNutritionFacts().getCalories());
            globalCalories += calories;
            int fat = Integer.parseInt(item.getNutritionFacts().getFat());
            globalFat += fat;
            int proteins = Integer.parseInt(item.getNutritionFacts().getProteins());
            globalProteins += proteins;
            int cholesterol = Integer.parseInt(item.getNutritionFacts().getCholesterol());
            globalColesterol += cholesterol;
            int carbohidrates = Integer.parseInt(item.getNutritionFacts().getCarbohidrates());
            globalCarbohidrates += carbohidrates;
        }

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        ArrayList<String> xVals = new ArrayList<String>();

//        xVals.add("CALORIES TODAY / MAX");
        xVals.add("FAT TODAY / MAX");
        xVals.add("CARBS TODAY / MAX");
        xVals.add("PROTEIN TODAY / MAX");
        xVals.add("CHOLESTEROL TODAY / MAX");

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

//        float[] values = new float[] { globalCalories, shared.getMaxCalories() - globalCalories};
//        yVals1.add(new BarEntry(values, 0));

        float[] valuesFat = new float[] { globalFat, Math.abs(shared.getMaxFat() - globalFat)};
        yVals1.add(new BarEntry(valuesFat, 0));
        Log.d(TAG, "FAT:" + globalFat + valuesFat[1]);

        float[] valuesCarbs = new float[] { globalCarbohidrates, Math.abs(shared.getMaxCarbs() - globalCarbohidrates)};
        yVals1.add(new BarEntry(valuesCarbs, 1));

        float[] valuesProtein = new float[] { globalProteins, Math.abs(shared.getMaxProtein() - globalProteins)};
        yVals1.add(new BarEntry(valuesProtein, 2));

        float[] valuesChol = new float[] { globalColesterol, Math.abs(shared.getMaxCholesterol() - globalColesterol)};
        yVals1.add(new BarEntry(valuesChol, 3));

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData dataBar = new BarData(xVals, dataSets);
        dataBar.setValueTextSize(10f);

        set1.setColors(colors);

        caloriestChart.setMaxVisibleValueCount(2000);

        caloriestChart.setData(dataBar);

        caloriestChart.animateXY(1000, 3000);

        caloriestChart.invalidate();
    }

    @Override
    public void failure(RetrofitError error) {

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
