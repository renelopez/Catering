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
import com.example.usuario.catering.net.NetServices;
import com.example.usuario.catering.net.OnBackgroundTaskCallback;
import com.example.usuario.catering.net.VisibleAnimation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


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
    private ArrayList<String> currentWeek = new ArrayList<>();
    private Button setMenuBtn;

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
        setCurrentWeekdays();
        wireEvents();
        return view;
    }

    private void setCurrentWeekdays() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        int delta = now.get(GregorianCalendar.DAY_OF_WEEK) - (now.getFirstDayOfWeek() + 1);
        now.add(Calendar.DAY_OF_MONTH, -delta);
        for (int i = 0; i < 5; i++) {
            currentWeek.add(format.format(now.getTime()));
            now.add(Calendar.DAY_OF_MONTH, 1);
        }


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
                intent.putExtra("DOW", currentWeek.get(0));
                startActivityForResult(intent, 300);
                mondayCounter++;
                mondayBtn.setText("Monday " + numOptions + "/" + mondayCounter);
            }
        });
        tuesdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                intent.putExtra("DOW", currentWeek.get(1));
                startActivityForResult(intent, 300);
                tuesdayCounter++;
                tuesdayBtn.setText("Tuesday " + numOptions + "/" + tuesdayCounter);
            }
        });
        wednesdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                intent.putExtra("DOW", currentWeek.get(2));
                startActivityForResult(intent, 300);
                wednesdayCounter++;
                tuesdayBtn.setText("Wednesday " + numOptions + "/" + wednesdayCounter);
            }
        });
        thursdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                intent.putExtra("DOW", currentWeek.get(3));
                startActivityForResult(intent, 300);
                thursdayCounter++;
                thursdayBtn.setText("Thursday " + numOptions + "/" + thursdayCounter);
            }
        });
        fridayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DishListActivity.class);
                intent.putExtra("DOW", currentWeek.get(4));
                startActivityForResult(intent, 300);
                fridayCounter++;
                fridayBtn.setText("Friday " + numOptions + "/" + fridayCounter);
            }
        });

        setMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuModel.setDishes(dishList);
                List<NameValuePair> listBody = new ArrayList<NameValuePair>(1);
                listBody.add(new BasicNameValuePair("menuDTO", menuModel.toString()));
                new NetServices(new OnBackgroundTaskCallback() {
                    @Override
                    public void onTaskCompleted(String response) {

                    }

                    @Override
                    public void onTaskError(String error) {

                    }
                }, new VisibleAnimation(view.findViewById(R.id.dishes_progress_bar)), "/menu").execute(NetServices.WS_CALL_POST);
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
        setMenuBtn = (Button) view.findViewById(R.id.set_menu_button);
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
