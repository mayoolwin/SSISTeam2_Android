package com.example.mayoolwin.ssisteam2_android.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mayoolwin.ssisteam2_android.InventoryCheck;
import com.example.mayoolwin.ssisteam2_android.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InventoryCheckList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InventoryCheckList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryCheckList extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<InventoryCheck> inventoryChecks;
    ListView listView;
    int index;

    public InventoryCheckList() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InventoryCheckList newInstance(ArrayList<InventoryCheck> inventoryChecks) {
        InventoryCheckList fragment = new InventoryCheckList();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, inventoryChecks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inventoryChecks = (ArrayList<InventoryCheck>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory_check_list, container, false);
        listView = (ListView) view.findViewById(R.id.inventoryCheckFragmentList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//                Rate rate = new Rate((HashMap<String, String>) adapterView.getAdapter().getItem(position));
                InventoryCheck inventoryCheck = InventoryCheck
                        .fromHashMap((HashMap<String, String>) adapterView.getAdapter().getItem(position));
                index = position;
                if (mListener != null) {
                    mListener.onFragmentInteraction(inventoryCheck, index);
                }
            }
        });
        ArrayList<HashMap<String, String>> maps = new ArrayList<>();

            for (InventoryCheck i: inventoryChecks
             ) {
            //Toast.makeText(container.getContext(), i.toHashMap().get("itemDescription"), Toast.LENGTH_SHORT).show();
            maps.add(i.toHashMap());
        }

        listView.setAdapter(new SimpleAdapter(getActivity(), maps, R.layout.row_inventory_check,
                new String[]{"itemDescription", "categoryName", "currentQuantity"},
                new int[]{R.id.inventoryCheckRowText1, R.id.inventoryCheckRowText2, R.id.inventoryCheckRowText3}));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(InventoryCheck inventoryCheck) {
        if (mListener != null) {
            mListener.onFragmentInteraction(inventoryCheck, index);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
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
        void onFragmentInteraction(InventoryCheck inventoryCheck, int index);
    }
}
