package com.shivam.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    databaseHelper logindb;

    boolean access=false;
    private final String CHANNEL_ID="personal_notification";
    private final int NOTIFICATION_ID=001;

    EditText editTextemail;
    EditText editTextpass;
    Button loginbtn;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logindb=new databaseHelper(this);

        editTextemail=(EditText)findViewById(R.id.editTextemail1);
        editTextpass=(EditText)findViewById(R.id.editTextpass1);
        loginbtn=(Button) findViewById(R.id.loginbutton);
        builder = new AlertDialog.Builder(this);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res=logindb.getAllData();
                int flag=0;

                String email=editTextemail.getText().toString();
                String pass=editTextpass.getText().toString();

                if(email.equals(""))
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
                else
                {
                    if (res.getCount()==0)
                    {
                        Toast.makeText(getApplicationContext(),"Username Is Not Found",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        while (res.moveToNext())
                        {
                            if (res.getString(2).equals(email) && res.getString(3).equals(pass))
                            {
                                access=true;
                                flag=1;
                            }
                        }
                    }

                    if (flag==0)
                    {
                        Toast.makeText(getApplicationContext(),"Username and Password Is Incorrect",Toast.LENGTH_LONG).show();
                    }
                }

                if (access==true)
                {
                    displayNotification(view);
                    Intent intent = new Intent(getApplicationContext(), nextActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    void displayNotification(View v)
    {
        NotificationCompat.Builder notbuilder=new NotificationCompat.Builder(this,CHANNEL_ID);
        notbuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notbuilder.setContentTitle("Login Notification");
        notbuilder.setContentText("Login Successfull. Thank You.");
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
