package com.shivam.menu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class nextActivity extends AppCompatActivity {

    Button showdatabtn, updatedatabtn, deletedatabtn;
    databaseHelper fundb;
    EditText id,ename,eemail,epass,emob;
    String no,name,email,pass,mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        showdatabtn=(Button) findViewById(R.id.showbtn);
        updatedatabtn=(Button) findViewById(R.id.updatebtn);
        deletedatabtn=(Button) findViewById(R.id.deletebtn);
        fundb=new databaseHelper(this);

        showdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdata();
            }
        });

        updatedatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedata();
            }
        });

        deletedatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedata();
            }
        });
    }

    void deletedata()
    {
        AlertDialog.Builder takeid=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=this.getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.deletealert,null);

        takeid.setView(view)
                .setTitle("Input")
                .setMessage("Enter Your Unique ID")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        id=view.findViewById(R.id.deleteid);
                        no=id.getText().toString();
                        Integer res=fundb.deleteData(no);

                        if (res>0)
                        {
                            Toast.makeText(getApplicationContext(),"Data Deleted Suceesfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Data Is Not Deleted",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        takeid.show();
    }

    void updatedata()
    {
        AlertDialog.Builder takeid=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=this.getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.alert,null);

        takeid.setView(view)
                .setTitle("Input")
                .setMessage("Enter Your Data")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        id=view.findViewById(R.id.id_fun);
                        ename=view.findViewById(R.id.name_fun);
                        eemail=view.findViewById(R.id.email_fun);
                        epass=view.findViewById(R.id.pass_fun);
                        emob=view.findViewById(R.id.mob_fun);
                        no=id.getText().toString();
                        name=ename.getText().toString();
                        email=eemail.getText().toString();
                        pass=epass.getText().toString();
                        mob=emob.getText().toString();

                        fundb.updateData(no,name,email,pass,mob);
                    }
                });

        takeid.show();
    }

    void showdata()
    {
        Cursor res=fundb.getAllData();

        if (res.getCount()==0)
        {
            Toast.makeText(getApplicationContext(),"Table Is Empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            StringBuffer stringBuffer=new StringBuffer();
            while (res.moveToNext())
            {
                stringBuffer.append("ID : "+res.getString(0)+"\n");
                stringBuffer.append("NAME : "+res.getString(1)+"\n");
                stringBuffer.append("EMAIL : "+res.getString(2)+"\n");
                stringBuffer.append("MOB. NO. : "+res.getString(4)+"\n\n");
            }

            showmsg("Data",stringBuffer.toString());
        }
    }

    void showmsg(String title, String msg)
    {
        AlertDialog.Builder build =new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(title);
        build.setMessage(msg);
        build.show();
    }
}
//hello