package com.example.bhavesh.voter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin extends Fragment {

    View parentHolder;

    public Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*if(getIntent().getBooleanExtra("exit",false)){
            finish();
        }*/
        parentHolder=inflater.inflate(R.layout.fragment_admin, container, false);
        Button bt1 = (Button) parentHolder.findViewById(R.id.bt3);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText admin = (EditText) parentHolder.findViewById(R.id.et1);
                EditText pw = (EditText) parentHolder.findViewById(R.id.et2);

                //Bundle b = new Bundle();
                //b=getIntent().getExtras();
                String ad, password;

                ad = String.valueOf(admin.getText());
                password = String.valueOf(pw.getText());

                if (ad.equals("bhavesh") && password.equals("abc123")) {
                    Intent i = new Intent(getActivity().getBaseContext(), Count.class);
                    startActivity(i);
                    admin.setText("");
                    pw.setText("");
                    admin.requestFocus();
                    //showToast("Admin Login Successful");
                } else {
                    showToast("Invalid Admin");
                    admin.setText("");
                    pw.setText("");
                }
            }
        });

        return parentHolder;
    }

    public boolean showToast(String message) {
        Toast.makeText(getActivity().getBaseContext(), message, Toast.LENGTH_LONG).show();
        return true;
    }
}