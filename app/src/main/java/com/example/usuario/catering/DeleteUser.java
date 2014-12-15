package com.example.usuario.catering;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.catering.interfaces.OnFragmentInteractionListener;
import com.example.usuario.catering.models.UserModel;
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.usuario.catering.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button searchUserBtn;
    private Button deleteUserBtn;
    private UserModel userModel;

    public DeleteUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteUser.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteUser newInstance(String param1, String param2) {
        DeleteUser fragment = new DeleteUser();
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
        View rootView = inflater.inflate(R.layout.fragment_delete_user, container, false);
        getActivity().setTitle("Delete User");
        initUI(rootView);
        setClicks();
        return rootView;

    }

    private void setClicks() {
        searchUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                startActivityForResult(intent, 300);
            }
        });
        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
    }

    private void deleteUser() {
        new NetServices(new OnBackgroundTaskCallback() {
            @Override
            public void onTaskCompleted(String response) {
                performAction();
            }

            @Override
            public void onTaskError(String error) {

            }
        }, new VisibleAnimation(getActivity().findViewById(R.id.users_progress_bar)), userModel.getId(), "/user").execute(NetServices.WS_CALL_DELETE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 500) {
            userModel = (UserModel) data.getSerializableExtra("itemSelected");
            TextView txtView = (TextView) getActivity().findViewById(R.id.user_to_delete_text);
            txtView.setText(userModel.getFirstName() + " " + userModel.getLastName());
        }

    }

    private void performAction() {
        Toast.makeText(getActivity(), "User deleted succesfully", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getActivity().getFragmentManager();
        manager.beginTransaction().replace(R.id.users_content_frame, UserList.newInstance("", "")).commit();
    }


    private void initUI(View view) {
        searchUserBtn = (Button) view.findViewById(R.id.search_user_button);
        deleteUserBtn = (Button) view.findViewById(R.id.delete_user_button);
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
