package com.example.usuario.catering;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.usuario.catering.adapters.DishListAdapter;
import com.example.usuario.catering.interfaces.OnFragmentInteractionListener;
import com.example.usuario.catering.models.DishListModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.usuario.catering.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DishList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DishList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView dishListView;
    private DishListModel dishListModel;

    private OnFragmentInteractionListener mListener;

    public DishList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DishList.
     */
    // TODO: Rename and change types and number of parameters
    public static DishList newInstance(String param1, String param2) {
        DishList fragment = new DishList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View dishView = inflater.inflate(R.layout.fragment_dish_list, container, false);
        initUI(dishView);
        getData();
        return dishView;
    }

    private void getData() {
        new NetServices(new OnBackgroundTaskCallback() {
            @Override
            public void onTaskCompleted(String response) {
                parseJSON(response);
                performAction();
            }

            @Override
            public void onTaskError(String error) {

            }
        }, new VisibleAnimation(getActivity().findViewById(R.id.dishes_progress_bar)), "/dish").execute(NetServices.WS_CALL_GET);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        dishListModel = gson.fromJson(response, DishListModel.class);
        setAdapter(dishListModel);
    }

    private void setAdapter(DishListModel dishListModel) {
        DishListAdapter dishListAdapter = new DishListAdapter(getActivity(), dishListModel.getDishList());
        dishListView.setAdapter(dishListAdapter);
    }

    private void performAction() {

    }

    private void initUI(View dishView) {
        dishListView = (ListView) dishView.findViewById(R.id.listDishes);
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


}
