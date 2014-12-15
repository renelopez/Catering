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
import com.example.usuario.catering.models.UserModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.usuario.catering.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText usernameTxt, passwordTxt, firstNameTxt, lastNameTxt;
    private Button createUserBtn;


    private OnFragmentInteractionListener mListener;
    private UserModel userModel;

    public CreateUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateUser.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateUser newInstance(String param1, String param2) {
        CreateUser fragment = new CreateUser();
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
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
        initUI(view);
        setClicks();
        return view;
    }

    private void setClicks() {


        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<NameValuePair> listBody = new ArrayList<NameValuePair>(4);
                listBody.add(new BasicNameValuePair("username", usernameTxt.getText().toString()));
                listBody.add(new BasicNameValuePair("password", passwordTxt.getText().toString()));
                listBody.add(new BasicNameValuePair("firstName", firstNameTxt.getText().toString()));
                listBody.add(new BasicNameValuePair("lastName", lastNameTxt.getText().toString()));
                new NetServices(new OnBackgroundTaskCallback() {
                    @Override
                    public void onTaskCompleted(String response) {
                        parseJSON(response);
                        performAction();
                    }

                    @Override
                    public void onTaskError(String error) {

                    }
                }, new VisibleAnimation(getActivity().findViewById(R.id.users_progress_bar)), listBody, "/user").execute(NetServices.WS_CALL_POST);
            }
        });
    }

    private void performAction() {
        Toast.makeText(getActivity(), "User created succesfully", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.users_content_frame, UserList.newInstance("", "")).commit();
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        userModel = gson.fromJson(response, UserModel.class);
    }

    private void initUI(View view) {
        usernameTxt = (EditText) view.findViewById(R.id.user_username);
        passwordTxt = (EditText) view.findViewById(R.id.user_password);
        firstNameTxt = (EditText) view.findViewById(R.id.user_first_name);
        lastNameTxt = (EditText) view.findViewById(R.id.user_last_name);
        createUserBtn = (Button) view.findViewById(R.id.user_create_user);
        getActivity().setTitle("Create User");
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


