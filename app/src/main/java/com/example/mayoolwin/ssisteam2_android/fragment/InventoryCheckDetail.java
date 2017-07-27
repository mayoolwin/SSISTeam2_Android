package com.example.mayoolwin.ssisteam2_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayoolwin.ssisteam2_android.InventoryCheck;
import com.example.mayoolwin.ssisteam2_android.MonthlyCheckActivity;
import com.example.mayoolwin.ssisteam2_android.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InventoryCheckDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InventoryCheckDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryCheckDetail extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private InventoryCheck inventoryCheck;
    private int index;

    private OnFragmentInteractionListener mListener;

    public InventoryCheckDetail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InventoryCheckDetail newInstance(InventoryCheck inventoryCheck, int index) {
        InventoryCheckDetail fragment = new InventoryCheckDetail();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, inventoryCheck);
        args.putInt(ARG_PARAM2, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inventoryCheck = (InventoryCheck) getArguments().getSerializable(ARG_PARAM1);
            index = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory_check_detail, container, false);

        final InventoryCheck inventoryCheck = (InventoryCheck) getArguments().getSerializable(ARG_PARAM1);

        TextView descriptionText = (TextView) view.findViewById(R.id.inventoryCheckDetailText1);
        descriptionText.setText(inventoryCheck.getItemDescription());
        TextView categoryText = (TextView) view.findViewById(R.id.inventoryCheckDetailText2);
        categoryText.setText(inventoryCheck.getCategoryName());
        TextView currentQtyText = (TextView) view.findViewById(R.id.inventoryCheckDetailText3);
        currentQtyText.setText(String.valueOf(inventoryCheck.getCurrentQuantity()));

        final NumberPicker actualQtyText = (NumberPicker) view.findViewById(R.id.inventoryCheckDetailNumPicker);
        actualQtyText.setMinValue(1);
        actualQtyText.setMaxValue(10000);
        actualQtyText.setValue(inventoryCheck.getActualQuantity());
        actualQtyText.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (i != i1) {
                    inventoryCheck.setActualQuantity(i1);
                }
            }
        });

        final EditText reasonText =  (EditText) view.findViewById(R.id.inventoryCheckDetailEdit);
        reasonText.setText(inventoryCheck.getReason());

        Button confirmButton = (Button) view.findViewById(R.id.inventoryCheckDetailConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inventoryCheck.setActualQuantity(actualQtyText.getValue());
                inventoryCheck.setReason(reasonText.getText().toString());
                String test = inventoryCheck.getReason() + String.valueOf(inventoryCheck.getActualQuantity());
//                Toast.makeText(getActivity().getApplicationContext(), test, Toast.LENGTH_LONG).show();
                if (mListener != null) {
                    mListener.onFragmentInteraction(reasonText.getText().toString(), actualQtyText.getValue(), index);
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(inventoryCheck.getReason(), inventoryCheck.getActualQuantity(), index);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof InventoryCheckDetail.OnFragmentInteractionListener) {
            mListener = (InventoryCheckDetail.OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String reason, int actualQty, int index);
    }
}
