package com.madgeeklabs.healthfridge.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.madgeeklabs.healthfridge.R;
import com.madgeeklabs.healthfridge.shared.HealthFridgeShared;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Limits.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Limits#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Limits extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.pickerCalories)
    NumberPicker pickerCarlories;

    @InjectView(R.id.pickerCarbs)
    NumberPicker pickerCarbs;

    @InjectView(R.id.pickerChol)
    NumberPicker pickerChol;

    @InjectView(R.id.pickerFat)
    NumberPicker pickerFat;

    @InjectView(R.id.pickerGlucid)
    NumberPicker pickerGlucid;

    @InjectView(R.id.pickerProtein)
    NumberPicker pickerProtein;

    @InjectView(R.id.save)
    Button saveStuff;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private HealthFridgeShared shared;

    public static Limits newInstance(String param1, String param2) {
        Limits fragment = new Limits();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Limits() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        shared = new HealthFridgeShared(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_limits, container, false);
        ButterKnife.inject(this,v);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pickerCarlories.setMinValue(1);
        pickerCarlories.setMaxValue(4000);
        pickerCarlories.setValue(shared.getMaxCalories());

        pickerCarbs.setMinValue(1);
        pickerCarbs.setMaxValue(1000);
        pickerCarbs.setValue(shared.getMaxCarbs());

        pickerChol.setMinValue(1);
        pickerChol.setMaxValue(500);
        pickerChol.setValue(shared.getMaxCholesterol());

        pickerFat.setMinValue(1);
        pickerFat.setMaxValue(1000);
        pickerFat.setValue(shared.getMaxFat());

        pickerGlucid.setMinValue(1);
        pickerGlucid.setMaxValue(1000);
        pickerGlucid.setValue(shared.getMaxProtein());

        pickerProtein.setMinValue(1);
        pickerProtein.setMaxValue(350);
        pickerProtein.setValue(shared.getMaxProtein());

        saveStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared.setMaxCalories(pickerCarlories.getValue());
                shared.setMaxCarbs(pickerCarbs.getValue());
                shared.setMaxFat(pickerFat.getValue());
                shared.setMaxProtein(pickerProtein.getValue());
                shared.setMaxColesterol(pickerChol.getValue());
            }
        });
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
