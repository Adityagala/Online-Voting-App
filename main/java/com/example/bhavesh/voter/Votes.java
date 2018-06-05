package com.example.bhavesh.voter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class Votes extends Fragment {
    View parentHolder;
    private static final int READ_BLOCK_SIZE = 1000;
    String[] entryArray = new String[1000];

    public Votes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentHolder=inflater.inflate(R.layout.fragment_votes, container, false);

        TextView aap=(TextView)parentHolder.findViewById(R.id.tv1);
        TextView ss=(TextView)parentHolder.findViewById(R.id.tv2);
        TextView con=(TextView)parentHolder.findViewById(R.id.tv3);
        TextView bjp=(TextView)parentHolder.findViewById(R.id.tv4);
        Button bt1 = (Button) parentHolder.findViewById(R.id.bt7);

        String s="";

        try
        {
            FileInputStream fIn =getActivity().openFileInput("vote.txt");
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
            Log.v("Tag","Hello "+s);
            if(s!=null)
            {
                entryArray=s.split(",");
            }
            else
            {
                Toast.makeText(getActivity().getBaseContext(),"File not loaded successfully!",Toast.LENGTH_LONG).show();
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        Log.v("Tag","Hiiiii "+entryArray[0]);
        Log.v("Tag","Hiiiii "+entryArray[1]);
        Log.v("Tag","Hiiiii "+entryArray[2]);
        Log.v("Tag","Hiiiii "+entryArray[3]);
        aap.setText("AAP "+entryArray[0]);
        ss.setText("Shivsena "+entryArray[1]);
        con.setText("Congress "+entryArray[2]);
        bjp.setText("BJP "+entryArray[3]);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Are you sure you want to reset?")
                        .setCancelable(false)
                        .setTitle("Reset")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    FileOutputStream out = getActivity().openFileOutput("vote.txt", MODE_PRIVATE);
                                    OutputStreamWriter fout = new OutputStreamWriter(out);
                                    fout.write("0,0,0,0");
                                    fout.close();

                                    out = getActivity().openFileOutput("login.txt", MODE_PRIVATE);
                                    fout = new OutputStreamWriter(out);
                                    fout.write("");
                                    fout.close();

                                    Intent i=new Intent(getActivity().getBaseContext(),Count.class);
                                    getActivity().finish();
                                    startActivity(i);
                                    Toast.makeText(getActivity().getBaseContext(),"Stats have been reset",Toast.LENGTH_SHORT).show();
                                } catch(IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog ad=alert.create();
                ad.setTitle("Logout");
                ad.show();
            }
        });
        return parentHolder;
    }
}
