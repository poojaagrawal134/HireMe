package com.example.hireme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hireme.Fragments.DesiredJob;
import com.example.hireme.Fragments.HomeFragment;
import com.example.hireme.Fragments.ProfileFragment;
import com.example.hireme.R;
import com.example.hireme.Fragments.SkillFragment;
import com.example.hireme.Fragments.WorkFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class MenuActivity extends AppCompatActivity {

    TapBarMenu tapBarMenu;
    ImageView home , work , profile, dashboard;
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tapBarMenu = findViewById(R.id.tapBarMenu);
        home = findViewById(R.id.Skill_frag);
        work = findViewById(R.id.work);
        profile = findViewById(R.id.pp);
        dashboard = findViewById(R.id.dashboard);

        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuButtonClick();
            }
        });

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();

        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SkillFragment()).commit();
            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WorkFragment()).commit();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DesiredJob()).commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.log:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.mic:
                Toast.makeText(getApplicationContext(), "Mic Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notification:
                Toast.makeText(getApplicationContext(), "Notification Clicked", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void onMenuButtonClick(){
        tapBarMenu.toggle();
    }
}