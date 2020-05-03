package com.example.myapplication.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.signin.login.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Bookdetailslayouthome extends SearchPage {

    TextView title, author, pub,description,link;
    ImageView image;
    View header;
    NavigationView navbar1;
    DrawerLayout drawerLayout1;
    ActionBarDrawerToggle mtoggle1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetailslayout);
        image = (ImageView) findViewById(R.id.img);
        title = (TextView) findViewById(R.id.bkname);
        author = (TextView) findViewById(R.id.aname);
        pub = (TextView) findViewById(R.id.pname);
//        description = (TextView) findViewById(R.id.bkdescription);
        link = (TextView) findViewById(R.id.bklink);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        navbar1 = (NavigationView) findViewById(R.id.drawer);
        navbar1.bringToFront();
        drawerLayout1 = (DrawerLayout) findViewById(R.id.drawerlay);
        header = navbar1.getHeaderView(0);
        mtoggle1 = new ActionBarDrawerToggle(this, drawerLayout1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout1.addDrawerListener(mtoggle1);
        mtoggle1.syncState();
        String key = getIntent().getStringExtra("key");


//retriving search on click  data from firebase
        { String Bname = getIntent().getStringExtra("booknames");
            title.setText("Book Name:"+Bname);

            String Author = getIntent().getStringExtra("author_name");
            author.setText("Author:"+Author);

            String publisher = getIntent().getStringExtra("publisher_name");
            pub.setText("Publisher:"+publisher);

            final String Link = getIntent().getStringExtra("link");
            link.setText("Click here to buy");
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent link=new Intent(Intent.ACTION_VIEW, Uri.parse(Link));
                    startActivity(link);
                }
            });

            String imgs = getIntent().getStringExtra("Image");
            Picasso.get().load(imgs).into(image);
        }

        //retriving onclick data from homepage

        {
               if(key!=null) {
                   databaseReference.child("TopbooksDB").child(key).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           String mtitle = dataSnapshot.child("bookname").getValue().toString();
                           title.setText(mtitle);
                           String mauthor = dataSnapshot.child("author").getValue().toString();
                           author.setText("by " + mauthor);
                           String mimgs = dataSnapshot.child("image").getValue().toString();
                           Picasso.get().load(mimgs).into(image);


                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
               }

            navbar1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    drawerLayout1.closeDrawers();

                    if(id== R.id.home) {

                        Intent search = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(search);
                    }
                    else if(id== R.id.browse) {
                        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
//                    Intent h = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(h);
                    }
                    else if(id== R.id.search) {

                        Intent search = new Intent(getApplicationContext(), SearchPage.class);
                        startActivity(search);
                    }
                    else if(id== R.id.Profile) {

                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);


                    }




                    else if(id== R.id.Logoutbtn) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.putExtra("finish", true); // if you are checking for this in your other Activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }

                    drawerLayout1.closeDrawer(GravityCompat.START);
                    return true;

                }

            });


    }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mtoggle1.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout1.isDrawerOpen(GravityCompat.START)) {
            drawerLayout1.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
