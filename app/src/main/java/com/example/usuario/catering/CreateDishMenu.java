package com.example.usuario.catering;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.usuario.catering.interfaces.OnFragmentInteractionListener;
import com.example.usuario.catering.models.DishModel;
import com.example.usuario.catering.models.MenuModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.usuario.catering.interfaces.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateDishMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateDishMenu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<DishModel> dishList = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private SeekBar optionSeek;
    private Button mondayBtn;
    private Button tuesdayBtn;
    private Button wednesdayBtn;
    private Button thursdayBtn;
    private Button fridayBtn;
    private TextView numOptionsText;
    private int numOptions = 0, mondayCounter = 0, tuesdayCounter = 0, wednesdayCounter = 0, thursdayCounter = 0, fridayCounter = 0;
    private MenuModel menuModel = new MenuModel();

    public CreateDishMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateDishMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateDishMenu newInstance(String param1, String param2) {
        CreateDishMenu fragment = new CreateDishMenu();
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
        View view = inflater.inflate(R.layout.fragment_create_dish_menu, container, false);
        initUI(view);
        wireEvents();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 500) {
            dishList.add((DishModel) data.getSerializableExtra("itemSelected"));
        }
    }

    private void wireEvents() {
        optionSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                numOptions = i;
                numOptionsText.setText(Integer.toString(numOptions));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mondayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                startActivityForResult(intent, 300, new Bundle());
                mondayCounter++;
                mondayBtn.setText("Monday " + numOptions + "/" + mondayCounter);
            }
        });
        tuesdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                startActivityForResult(intent, 300);
                tuesdayCounter++;
                tuesdayBtn.setText("Tuesday " + numOptions + "/" + tuesdayCounter);
            }
        });
        wednesdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                startActivityForResult(intent, 300);
                wednesdayCounter++;
                tuesdayBtn.setText("Wednesday " + numOptions + "/" + wednesdayCounter);
            }
        });
        thursdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                startActivityForResult(intent, 300);
                thursdayCounter++;
                thursdayBtn.setText("Thursday " + numOptions + "/" + thursdayCounter);
            }
        });
        fridayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                startActivityForResult(intent, 300);
                fridayCounter++;
                fridayBtn.setText("Friday " + numOptions + "/" + fridayCounter);
            }
        });
    }

    private void initUI(View view) {
        optionSeek = (SeekBar) view.findViewById(R.id.option_number_seekbar);
        mondayBtn = (Button) view.findViewById(R.id.monday_button);
        tuesdayBtn = (Button) view.findViewById(R.id.tuesday_button);
        wednesdayBtn = (Button) view.findViewById(R.id.wednesday_button);
        thursdayBtn = (Button) view.findViewById(R.id.thursday_button);
        fridayBtn = (Button) view.findViewById(R.id.friday_button);
        numOptionsText = (TextView) view.findViewById(R.id.num_options_text);
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
