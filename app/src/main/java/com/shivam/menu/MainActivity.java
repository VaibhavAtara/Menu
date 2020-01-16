package com.shivam.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this, button);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //Toast.makeText(MainActivity.this,"You Clicked : " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        switch(menuItem.getItemId())
                        {
                            case R.id.item1:
                                signup(view);
                                break;
                            case R.id.item2:
                                login(view);
                                break;

                        }
                        return true;
                    }
                    void signup(View view1)
                    {
                        Intent intent = new Intent(getApplicationContext(), signup.class);
                        startActivity(intent);
                    }
                    void login(View view1)
                    {
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                    }
                });
                popup.show();
            }
        });
    }
}
