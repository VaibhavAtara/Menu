package com.shivam.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    databaseHelper signupdb;
    boolean access=false;
    private final String CHANNEL_ID="personal_notification";
    private final int NOTIFICATION_ID=001;

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPass;
    EditText editTextMob;
    Button buttonSignUp;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupdb=new databaseHelper(this);
        buttonSignUp=(Button)findViewById(R.id.button);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPass=(EditText)findViewById(R.id.editTextPass);
        editTextMob=(EditText)findViewById(R.id.editTextMob);
        builder = new AlertDialog.Builder(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTextName.getText().toString();
                String email=editTextEmail.getText().toString();
                String mob=editTextMob.getText().toString();
                String pass=editTextPass.getText().toString();


                //Toast.makeText(getApplicationContext(),"Name is "+name,Toast.LENGTH_LONG).show();
                if(name.equals(""))
                {
                    String str="Name field is empty. Please fill it.";
                    alertdialogfunction(str);
                    access=false;
                }
                else if(email.equals(""))
                {
                    String str="Email field is empty. Please fill it.";
                    alertdialogfunction(str);
                    access=false;
                }
                else if(!email.contains("@") || !email.contains("."))
                {
                    String str="Invalid Email Id.";
                    alertdialogfunction(str);
                    access=false;
                }
                else if(pass.equals(""))
                {
                    String str="Password field is empty. Please fill it.";
                    alertdialogfunction(str);
                    access=false;
                }
                else if (pass.length()<=8)
                {
                    String str="Password length should be greater than 8.";
                    alertdialogfunction(str);
                    access=false;
                }
                else if(mob.equals(""))
                {
                    String str="Mobile No. field is empty. Please fill it.";
                    alertdialogfunction(str);
                    access=false;
                }
                else if (mob.length()!=10)
                {
                    String str="Invalid Mobile Number.";
                    alertdialogfunction(str);
                    access=false;
                }
                else
                {
                    access=true;
                    boolean isinserted=signupdb.insertdata(name,email,pass,mob);

                    if (isinserted==true)
                    {
                        Toast.makeText(getApplicationContext(),"Data is Inserted",Toast.LENGTH_LONG).show();
                    }
                }

                if(access==true)
                {
                    displayNotification(v);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

        });
    }
    void displayNotification(View v)
    {
        NotificationCompat.Builder notbuilder=new NotificationCompat.Builder(this,CHANNEL_ID);
        notbuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notbuilder.setContentTitle("Sign Up Notification");
        notbuilder.setContentText("Account created Successfully. Thank You.");
        notbuilder.setAutoCancel(false);
        notbuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, notbuilder.build());
    }

    void alertdialogfunction(String str)
    {
        builder.setMessage(str)
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle("Alert...!");
        alertDialog.show();
    }
}
