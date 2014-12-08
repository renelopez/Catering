package com.example.usuario.catering;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.catering.interfaces.OnFragmentInteractionListener;
import com.example.usuario.catering.models.DishModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class CreateDish extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText dishNameTxt, dishDescriptionTxt;
    Button createDishBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DishModel dishModel;

    private OnFragmentInteractionListener mListener;

    public CreateDish() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateDish.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateDish newInstance(String param1, String param2) {
        CreateDish fragment = new CreateDish();
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
        View rootView = inflater.inflate(R.layout.fragment_create_dish, container, false);
        initUI(rootView);
        setClicks();
        return rootView;
    }

    private void setClicks() {
        createDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDish();
            }
        });
    }

    private void createDish() {
        List<NameValuePair> listBody = new ArrayList<NameValuePair>(2);
        listBody.add(new BasicNameValuePair("name", dishNameTxt.getText().toString()));
        listBody.add(new BasicNameValuePair("description", dishDescriptionTxt.getText().toString()));
        new NetServices(new OnBackgroundTaskCallback() {
            @Override
            public void onTaskCompleted(String response) {
                parseJSON(response);
                performAction();
            }

            @Override
            public void onTaskError(String error) {
                Toast.makeText(getActivity(), "Error:" + error, Toast.LENGTH_SHORT).show();
            }
        }, new VisibleAnimation(getActivity().findViewById(R.id.dishes_progress_bar)), listBody, "/dish").execute(NetServices.WS_CALL_POST);
    }

    private void performAction() {
        Toast.makeText(getActivity(), "Dish created succesfully", Toast.LENGTH_SHORT).show();
        FragmentManager manager=getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.dishes_content_frame, DishList.newInstance("", "")).commit();
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        dishModel = gson.fromJson(response, DishModel.class);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void initUI(View rootView) {
        dishNameTxt = (EditText) rootView.findViewById(R.id.dish_name);
        dishDescriptionTxt = (EditText) rootView.findViewById(R.id.dish_description);
        createDishBtn = (Button) rootView.findViewById(R.id.createDish);
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
