package com.example.fallenangels.adoption.submissions.adoption_pages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fallenangels.R;
import com.example.fallenangels.adoption.submissions.foster_pages.FosterForm1;


public class AdoptionForm1 extends Fragment
{
    //User input fields
    private RadioButton rb_Canine;
    private RadioButton rb_Feline;
    private RadioGroup animalChoice;

    private EditText edt_dogNameOne;
    private EditText edt_dogNameTwo;
    private EditText edt_NameSurname;
    private EditText edt_address;
    //User input fields END

    //Button to go to next page
    private AppCompatButton btnNext;

    public AdoptionForm1()
    {
        // Required empty public constructor
    }

    public static AdoptionForm1 newInstance(String param1)
    {
        AdoptionForm1 fragment = new AdoptionForm1();
        Bundle args = new Bundle();
        args.putString("AdoptForm1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adoption_form1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        //Finding ID's
        rb_Canine = getView().findViewById(R.id.rb_Canine);
        rb_Feline = getView().findViewById(R.id.rb_Feline);

        edt_dogNameOne = getView().findViewById(R.id.edt_dogNameOne);
        edt_dogNameTwo = getView().findViewById(R.id.edt_dogNameTwo);
        edt_NameSurname = getView().findViewById(R.id.edt_NameSurname);
        edt_address = getView().findViewById(R.id.edt_address);

        btnNext = getView().findViewById(R.id.btnNext1);

        //listeners
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (edt_dogNameOne.getText().toString().isEmpty() || edt_dogNameTwo.getText().toString().isEmpty() ||
                    edt_NameSurname.getText().toString().isEmpty() || edt_address.getText().toString().isEmpty() || rb_Canine.isChecked() == false)
                {
                    Toast.makeText(getActivity(), "Please ensure all fields are filled in!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frag_layout, new AdoptionForm2());
                    ft.commit();
                }
            }
        });

        rb_Canine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        rb_Feline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }
}