package com.example.usuario.catering;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.usuario.catering.adapters.UserListAdapter;
import com.example.usuario.catering.interfaces.OnFragmentInteractionListener;
import com.example.usuario.catering.models.UserListModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.usuario.catering.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView userListView;
    private UserListModel userListModel;

    public UserList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserList.
     */
    // TODO: Rename and change types and number of parameters
    public static UserList newInstance(String param1, String param2) {
        UserList fragment = new UserList();
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
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        initUI(view);
        getData();
        return view;
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
        }, new VisibleAnimation(getActivity().findViewById(R.id.users_progress_bar)), "/user").execute(NetServices.WS_CALL_GET);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();
        userListModel = gson.fromJson(response, UserListModel.class);
        setAdapter(userListModel);
    }

    private void setAdapter(UserListModel userListModel) {
        UserListAdapter userListAdapter = new UserListAdapter(getActivity(), userListModel.getUserList());
        userListView.setAdapter(userListAdapter);
    }

    private void performAction() {

    }

    private void initUI(View view) {
        userListView = (ListView) view.findViewById(R.id.list_users);
        getActivity().setTitle("Current Users");
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
