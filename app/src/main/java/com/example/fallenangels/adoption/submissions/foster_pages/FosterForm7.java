package com.example.fallenangels.adoption.submissions.foster_pages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fallenangels.R;
import com.example.fallenangels.adoption.submissions.adoption_pages.AdoptionForm1;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FosterForm7 extends Fragment
{
    private DatabaseReference dbRef;
    private FirebaseUser fUser;
    private FirebaseAuth mAuth;
    private String userID;

    private CheckBox untilAdoption;
    private CheckBox holiday;
    private EditText nameOfFoster;
    private EditText signature;
    private EditText date;

    private TextView txtViewRules;
    private AppCompatButton btnBack;
    private AppCompatButton btnSubmit;

    public FosterForm7()
    {
        // Required empty public constructor
    }

    public static FosterForm7 newInstance(String param1, String param2)
    {
        FosterForm7 fragment = new FosterForm7();
        Bundle args = new Bundle();
        args.putString("FosterForm", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foster_form7, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        untilAdoption = getView().findViewById(R.id.f_checkAdoption);
        holiday = getView().findViewById(R.id.f_checkHoliday);
        nameOfFoster = getView().findViewById(R.id.f_name_foster_applicant);
        signature = getView().findViewById(R.id.f_applicant_signature);
        date = getView().findViewById(R.id.edt_f_currentDate);

        //Finding ID's
        txtViewRules = getView().findViewById(R.id.txtViewTerms2);
        btnBack = getView().findViewById(R.id.f_btnBack6);
        btnSubmit = getView().findViewById(R.id.f_btnSubmit);

        txtViewRules.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ViewTermsConditions(view);
            }
        });


        //Listeners
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_layout, new FosterForm6());
                ft.commit();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(signature.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "Signature is required to Submit!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    saveUserInput();
                    if (mAuth != null)
                    {
                        //They ARE logged in
                        submitApplicationDialog();
                    }
                    else
                    {
                        //not logged in
                    }
                }

            }
        });
    }

    private void saveUserInput()
    {
        FosterForm1.newForm.setPg7_typeOfFostor(getTypeOfFostor().trim());
        FosterForm1.newForm.setPg7_nameOfFoster(nameOfFoster.getText().toString());
        FosterForm1.newForm.setPg7_Signature(signature.getText().toString().trim());
        FosterForm1.newForm.setPg7_CurrentDate(date.getText().toString());
    }

    //------------------------------ Show terms & conditions dialogue ------------------------------
    public void ViewTermsConditions(View view) {
        Dialog dialog = new Dialog(getContext(), R.style.DialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialogue_ts_and_cs_foster);

        AppCompatButton btnClose = dialog.findViewById(R.id.f_btnCloseRules);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    //----------------------------------------------------------------------------------------------

    private void submitApplicationDialog()
    {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());

        //Setting message manually and performing action on button click
        builder.setMessage("Would you like to submit this Adoption Application?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //  Action for 'Yes' Button
                        final ProgressDialog pd = new ProgressDialog(getView().getContext());
                        pd.setTitle("Uploading Submission...");
                        pd.show();

                        dbRef = FirebaseDatabase.getInstance().getReference("Forms").child(userID).child("FosterForms");
                        dbRef.push().setValue(FosterForm1.newForm);

                        pd.dismiss();
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Submission Complete.", Snackbar.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //  Action for 'No' Button
                        dialog.dismiss();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Adoption Form");
        alert.show();
    }

    private String getTypeOfFostor()
    {
        String reason;
        String r1 = "", r2 = "";

        if (untilAdoption.isChecked() == true)
        {
            r1 = untilAdoption.getText().toString().trim();
        }
        if (holiday.isChecked() == true)
        {
            r2 = holiday.getText().toString().trim();
        }

        reason = r1 + " " + r2;

        return reason;
    }
}