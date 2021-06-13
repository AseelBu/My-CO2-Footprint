package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountSettingsFragmentDialog extends DialogFragment {

    private Context context;
    private TextView name;
    private TextView email;
    private Button okBtn;
    FirebaseUser user=null;

    public AccountSettingsFragmentDialog() {
        // Required empty public constructor
    }


    public static AccountSettingsFragmentDialog newInstance() {
        AccountSettingsFragmentDialog fragment = new AccountSettingsFragmentDialog();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account_settings, container, false);
        name = (TextView) view.findViewById(R.id.nameset);
        email = (TextView) view.findViewById(R.id.emailaccount);
        okBtn = (Button) view.findViewById(R.id.okAccountBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        context = getContext();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        return view;
    }

}