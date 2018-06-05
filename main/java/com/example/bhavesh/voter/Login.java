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
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private static final int READ_BLOCK_SIZE = 1000;
    String[] entryArray = new String[1000];
    Bundle b = new Bundle();
    View parentHolder;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentHolder=inflater.inflate(R.layout.fragment_login, container, false);
        //super.onCreate(savedInstanceState);
        Button ok = (Button) parentHolder.findViewById(R.id.bt1);

        final TextView test=(TextView)parentHolder.findViewById(R.id.test);
        final EditText aadharEditText=(EditText)parentHolder.findViewById(R.id.et1);
        final EditText passwordEditText=(EditText)parentHolder.findViewById(R.id.et2);

        String s="";

        try
        {
            FileInputStream fIn =getActivity().openFileInput("login.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;
            while ((charRead = isr.read(inputBuffer))>0)
            {
//---convert the chars to a String---
                String readString =String.copyValueOf(inputBuffer, 0,charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            if(s!=null)
            {
                entryArray=s.split(",");
            }
            else
            {
                Toast.makeText(getActivity().getBaseContext(),"File not loaded successfully!",Toast.LENGTH_LONG).show();
            }

            test.setText(s);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                String aadharNo=aadharEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                if(aadharNo.equals("") || password.equals(""))
                {
                    Toast.makeText(getActivity().getBaseContext(), "Enter Aadhar No and Password",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    for(int i=0;i<entryArray.length;i=i+3)
                    {
                        //Log.v("TAG","adhar is"+entryArray[i+1]+" and login is "+entryArray[i]);
                        if(i+1<entryArray.length && password.equals(entryArray[i+1]) && aadharNo.equals( entryArray[i]))
                        {
                            if(entryArray[i+2].equals("0"))
                            {
                                b.putString("aadhar", aadharNo);
                                b.putString("password", password);
                                Intent in=new Intent(getActivity().getBaseContext(),Activity3.class);
                                in.putExtras(b);
                                startActivity(in);
                                aadharEditText.setText("");
                                passwordEditText.setText("");
                                break;
                            }else
                            {
                                Toast.makeText(getActivity().getBaseContext(), "You have already VOTED",
                                        Toast.LENGTH_LONG).show();
                                aadharEditText.setText("");
                                passwordEditText.setText("");
                                aadharEditText.requestFocus();
                                break;
                            }
                        }
                        else
                        {
                            if((i==entryArray.length-3))
                            {
                                Toast.makeText(getActivity().getBaseContext(), "You are not a valid User",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }});

        return parentHolder;
    }
}