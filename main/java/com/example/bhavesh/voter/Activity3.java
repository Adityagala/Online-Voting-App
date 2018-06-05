package com.example.bhavesh.voter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Activity3 extends AppCompatActivity {

    private static final int READ_BLOCK_SIZE = 1000;
    String[] entryArray = new String[1000];
    Bundle b = new Bundle();
    String[] entryArrayLogin = new String[1000];
    boolean voted=false;
    RelativeLayout aap,ss,con,bjp;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Voter");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int colour=getResources().getColor(R.color.windowBackground);
        toolbar.getNavigationIcon().setColorFilter(colour, PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitleTextColor(colour);

        aap=(RelativeLayout) findViewById(R.id.rl1);
        ss=(RelativeLayout)findViewById(R.id.rl2);
        con=(RelativeLayout) findViewById(R.id.rl3);
        bjp=(RelativeLayout)findViewById(R.id.rl4);
        final TextView check=(TextView)findViewById(R.id.check);
        final TextView checkvote=(TextView)findViewById(R.id.checkvote);
        Button bt1= (Button)findViewById(R.id.bt4);

        String s="";

        b=getIntent().getExtras();

        boolean filePresent=fileExist("vote.txt");

        if(filePresent) {

            try {
                FileInputStream fIn = openFileInput("vote.txt");
                InputStreamReader isr = new InputStreamReader(fIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    //---convert the chars to a String---
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readString;
                    inputBuffer = new char[READ_BLOCK_SIZE];
                }
                if (s != null) {
                    entryArray = s.split(",");
                } else {
                    Toast.makeText(getBaseContext(), "File not loaded successfully!", Toast.LENGTH_LONG).show();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            s = "";

            try {
                FileInputStream fIn = openFileInput("login.txt");
                InputStreamReader isr = new InputStreamReader(fIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    //---convert the chars to a String---
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readString;
                    inputBuffer = new char[READ_BLOCK_SIZE];
                }
                if (s != null) {
                    entryArrayLogin = s.split(",");
                } else {
                    Toast.makeText(getBaseContext(), "File not loaded successfully!", Toast.LENGTH_LONG).show();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        else {
            try {
                FileOutputStream out = openFileOutput("vote.txt", MODE_PRIVATE);
                OutputStreamWriter fout = new OutputStreamWriter(out);
                fout.write("0,0,0,0");
                fout.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

            try {
                FileInputStream fIn = openFileInput("vote.txt");
                InputStreamReader isr = new InputStreamReader(fIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    //---convert the chars to a String---
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readString;
                    inputBuffer = new char[READ_BLOCK_SIZE];
                }
                if (s != null) {
                    entryArray = s.split(",");
                } else {
                    Toast.makeText(getBaseContext(), "File not loaded successfully!", Toast.LENGTH_LONG).show();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            s = "";

            try {
                FileInputStream fIn = openFileInput("login.txt");
                InputStreamReader isr = new InputStreamReader(fIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    //---convert the chars to a String---
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readString;
                    inputBuffer = new char[READ_BLOCK_SIZE];
                }
                if (s != null) {
                    entryArrayLogin = s.split(",");
                } else {
                    Toast.makeText(getBaseContext(), "File not loaded successfully!", Toast.LENGTH_LONG).show();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }


        aap.setOnClickListener(new View.OnClickListener() {
                                   String aadhar = b.getString("aadhar");
                                   String password = b.getString("password");
                                   public void onClick(View v) {
                                       if(!voted) {
                                           AlertDialog.Builder alert = new AlertDialog.Builder(Activity3.this);
                                           alert.setMessage("Are you sure you want to vote for AAP?")
                                                   .setCancelable(false)
                                                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                       @Override
                                                       public void onClick(DialogInterface dialog, int which) {
                                                           int vote_count = Integer.parseInt(entryArray[0]);
                                                           vote_count++;
                                                           entryArray[0] = String.valueOf(vote_count);

                                                           for (int i = 0; i < entryArrayLogin.length; i = i + 3) {
                                                               if (entryArrayLogin[i].equals(aadhar) && entryArrayLogin[i + 1].equals(password)) {
                                                                   entryArrayLogin[i + 2] = String.valueOf(1);
                                                                   break;
                                                               }
                                                           }

                                                           String new_login = "";
                                                           String new_vote = "";

                                                           for (int i = 0; i < entryArrayLogin.length; i++) {
                                                               new_login += entryArrayLogin[i] + ',';
                                                           }

                                                           for (int i = 0; i < entryArray.length; i++) {
                                                               new_vote += entryArray[i] + ',';
                                                           }

                                                           try {
                                                               FileOutputStream out = openFileOutput("login.txt", MODE_PRIVATE);
                                                               OutputStreamWriter fout = new OutputStreamWriter(out);
                                                               fout.write(new_login);
                                                               fout.close();
                                                           } catch (IOException e) {
                                                               e.printStackTrace();
                                                           }

                                                           try {
                                                               FileOutputStream out = openFileOutput("vote.txt", MODE_PRIVATE);
                                                               OutputStreamWriter fout = new OutputStreamWriter(out);
                                                               fout.write(new_vote);
                                                               fout.close();
                                                           } catch (IOException e) {
                                                               e.printStackTrace();
                                                           }

                                                           check.setText(new_login);
                                                           checkvote.setText(new_vote);
                                                           voted=true;
                                                       }
                                                   })
                                                   .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                       @Override
                                                       public void onClick(DialogInterface dialog, int which) {
                                                           dialog.cancel();
                                                       }
                                                   });

                                           AlertDialog ad=alert.create();
                                           ad.setTitle("Confirm Vote");
                                           ad.show();
                                       }
                                       else {
                                           Toast.makeText(getBaseContext(),"You are only allowed to vote once",Toast.LENGTH_SHORT).show();
                                       }

                                   }
                               }

        );


        ss.setOnClickListener(new View.OnClickListener() {
            String aadhar = b.getString("aadhar");
            String password = b.getString("password");

            public void onClick(View v) {
                if(!voted) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Activity3.this);
                    alert.setMessage("Are you sure you want to vote for Shiv Sena?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int vote_count = Integer.parseInt(entryArray[1]);
                                    vote_count++;
                                    entryArray[1] = String.valueOf(vote_count);

                                    for (int i = 0; i < entryArrayLogin.length; i = i + 3) {
                                        if (entryArrayLogin[i].equals(aadhar) && entryArrayLogin[i + 1].equals(password)) {
                                            entryArrayLogin[i + 2] = String.valueOf(1);
                                            break;
                                        }
                                    }

                                    String new_login = "";
                                    String new_vote = "";

                                    for (int i = 0; i < entryArrayLogin.length; i++) {
                                        new_login += entryArrayLogin[i] + ',';
                                    }

                                    for (int i = 0; i < entryArray.length; i++) {
                                        new_vote += entryArray[i] + ',';
                                    }

                                    try {
                                        FileOutputStream out = openFileOutput("login.txt", MODE_PRIVATE);
                                        OutputStreamWriter fout = new OutputStreamWriter(out);
                                        fout.write(new_login);
                                        fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        FileOutputStream out = openFileOutput("vote.txt", MODE_PRIVATE);
                                        OutputStreamWriter fout = new OutputStreamWriter(out);
                                        fout.write(new_vote);
                                        fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    check.setText(new_login);
                                    checkvote.setText(new_vote);
                                    voted=true;
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog ad=alert.create();
                    ad.setTitle("Confirm Vote");
                    ad.show();
                }
                else {
                    Toast.makeText(getBaseContext(),"You are only allowed to vote once",Toast.LENGTH_SHORT).show();
                }

            }
        });


        con.setOnClickListener(new View.OnClickListener() {

            String aadhar = b.getString("aadhar");
            String password = b.getString("password");

            public void onClick(View v) {
                if (!voted) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Activity3.this);
                    alert.setMessage("Are you sure you want to vote for Congress?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int vote_count = Integer.parseInt(entryArray[2]);
                                    vote_count++;
                                    entryArray[2] = String.valueOf(vote_count);

                                    for (int i = 0; i < entryArrayLogin.length; i = i + 3) {
                                        if (entryArrayLogin[i].equals(aadhar) && entryArrayLogin[i + 1].equals(password)) {
                                            entryArrayLogin[i + 2] = String.valueOf(1);
                                            break;
                                        }
                                    }

                                    String new_login = "";
                                    String new_vote = "";

                                    for (int i = 0; i < entryArrayLogin.length; i++) {
                                        new_login += entryArrayLogin[i] + ',';
                                    }

                                    for (int i = 0; i < entryArray.length; i++) {
                                        new_vote += entryArray[i] + ',';
                                    }

                                    try {
                                        FileOutputStream out = openFileOutput("login.txt", MODE_PRIVATE);
                                        OutputStreamWriter fout = new OutputStreamWriter(out);
                                        fout.write(new_login);
                                        fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        FileOutputStream out = openFileOutput("vote.txt", MODE_PRIVATE);
                                        OutputStreamWriter fout = new OutputStreamWriter(out);
                                        fout.write(new_vote);
                                        fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    check.setText(new_login);
                                    checkvote.setText(new_vote);
                                    voted = true;
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog ad = alert.create();
                    ad.setTitle("Confirm Vote");
                    ad.show();
                } else {
                    Toast.makeText(getBaseContext(), "You are only allowed to vote once", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bjp.setOnClickListener(new View.OnClickListener() {

            String aadhar = b.getString("aadhar");
            String password = b.getString("password");

            public void onClick(View v) {
                if(!voted) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Activity3.this);
                    alert.setMessage("Are you sure you want to vote for BJP?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int vote_count = Integer.parseInt(entryArray[3]);
                                    vote_count++;
                                    entryArray[3] = String.valueOf(vote_count);

                                    for (int i = 0; i < entryArrayLogin.length; i = i + 3) {
                                        if (entryArrayLogin[i].equals(aadhar) && entryArrayLogin[i + 1].equals(password)) {
                                            entryArrayLogin[i + 2] = String.valueOf(1);
                                            break;
                                        }
                                    }

                                    String new_login = "";
                                    String new_vote = "";

                                    for (int i = 0; i < entryArrayLogin.length; i++) {
                                        new_login += entryArrayLogin[i] + ',';
                                    }

                                    for (int i = 0; i < entryArray.length; i++) {
                                        new_vote += entryArray[i] + ',';
                                    }

                                    try {
                                        FileOutputStream out = openFileOutput("login.txt", MODE_PRIVATE);
                                        OutputStreamWriter fout = new OutputStreamWriter(out);
                                        fout.write(new_login);
                                        fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        FileOutputStream out = openFileOutput("vote.txt", MODE_PRIVATE);
                                        OutputStreamWriter fout = new OutputStreamWriter(out);
                                        fout.write(new_vote);
                                        fout.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    check.setText(new_login);
                                    checkvote.setText(new_vote);
                                    voted=true;
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog ad=alert.create();
                    ad.setTitle("Confirm Vote");
                    ad.show();
                }
                else {
                    Toast.makeText(getBaseContext(),"You are only allowed to vote once",Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Activity2.class);
                startActivity(i);
            }
        });*/
    }

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==android.R.id.home)
        {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity3.this);
        alert.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
        //super.onBackPressed();
    }
}