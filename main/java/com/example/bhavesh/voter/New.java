package com.example.bhavesh.voter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class New extends Fragment {

    private static final int READ_BLOCK_SIZE = 1000;
    String[] entryArray = new String[1000];
    View parentHolder;

    public New() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        parentHolder=inflater.inflate(R.layout.fragment_new, container, false);

        final EditText aadharEditText=(EditText) parentHolder.findViewById(R.id.et1);
        final EditText passwordEditText=(EditText) parentHolder.findViewById(R.id.et2);

        Button btn=(Button) parentHolder.findViewById(R.id.bt1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aadharNo = aadharEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (aadharNo.length() != 12) {
                    Toast.makeText(getActivity(), "Invalid Aadhar No", Toast.LENGTH_SHORT).show();
                } else {
                    String s = "";
                    boolean filePresent = fileExist("login.txt");
                    boolean success = true;

                    if (filePresent) {
                        try {
                            FileInputStream fIn = getActivity().openFileInput("login.txt");
                            InputStreamReader isr = new InputStreamReader(fIn);
                            char[] inputBuffer = new char[READ_BLOCK_SIZE];
                            int charRead;
                            while ((charRead = isr.read(inputBuffer)) > 0) {
                                //---convert the chars to a String---
                                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                                s += readString;
                                inputBuffer = new char[READ_BLOCK_SIZE];
                            }
                            Log.v("TAG", "s is " + s);
                            if (s != null) {
                                entryArray = s.split(",");
                            } else {
                                Toast.makeText(getActivity().getBaseContext(), "File not loaded successfully!", Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }

                        if (aadharNo.equals("") || password.equals("")) {
                            Toast.makeText(getActivity().getBaseContext(), "Enter Aadhar No. and Password",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < entryArray.length; i = i + 3) {
                                if (aadharNo.equals(entryArray[i])) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Aadhar No exists. Please register another Aadhar No", Toast.LENGTH_SHORT).show();
                                    success = false;
                                    break;
                                }
                            }

                            if (!success) {
                                aadharEditText.setText("");
                                passwordEditText.setText("");
                            } else {
                                String new_login = aadharNo + "," + password + ",0,";
                                try {
                                    FileOutputStream out = getActivity().openFileOutput("login.txt", MODE_APPEND);
                                    OutputStreamWriter fout = new OutputStreamWriter(out);
                                    fout.append(new_login);
                                    fout.close();
                                    Bundle b = new Bundle();
                                    b.putString("aadhar", aadharNo);
                                    b.putString("password", password);
                                    Intent i = new Intent(getActivity().getBaseContext(), Activity3.class);
                                    i.putExtras(b);
                                    startActivity(i);
                                    aadharEditText.setText("");
                                    passwordEditText.setText("");
                                    aadharEditText.requestFocus();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {

                        String new_login = aadharNo + "," + password + ",0,";

                        try {
                            FileOutputStream out = getActivity().openFileOutput("login.txt", MODE_PRIVATE);
                            OutputStreamWriter fout = new OutputStreamWriter(out);
                            fout.write(new_login);
                            fout.close();
                            Bundle b = new Bundle();
                            b.putString("aadhar", aadharNo);
                            b.putString("password", password);
                            Intent i = new Intent(getActivity().getBaseContext(), Activity3.class);
                            i.putExtras(b);
                            startActivity(i);
                            aadharEditText.setText("");
                            passwordEditText.setText("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        return parentHolder;
    }

    public boolean fileExist(String fname){
        File file = getActivity().getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}