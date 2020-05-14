package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.User.Profile;
import com.example.myapplication.User.SearchPage;
import com.example.myapplication.signin.login.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdminSearch extends AppCompatActivity {
    EditText searchbarr;
    RecyclerView recyclerVieww;
    DatabaseReference referencee;
    ArrayList<String> BookNameLists;
    ArrayList<String> AuthorNameLists;
    ArrayList<String> PicLists;
    ArrayList<String> PublisherLists;
    ArrayList<String> LinkLists;
    ArrayList<String> DescriptionLists;
    ArrayList<String>UidList;
    View header;
    NavigationView navbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mtoggle;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsearch);
        searchbarr = (EditText) findViewById(R.id.searchbar);
        referencee = FirebaseDatabase.getInstance().getReference();
        referencee.keepSynced(true);
        recyclerVieww = (RecyclerView) findViewById(R.id.rv);
        recyclerVieww.setHasFixedSize(true);
        recyclerVieww.setLayoutManager(new LinearLayoutManager(this));
        recyclerVieww.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        BookNameLists = new ArrayList<>();
        PublisherLists = new ArrayList<>();
        AuthorNameLists = new ArrayList<>();
        LinkLists = new ArrayList<>();
        PicLists = new ArrayList<>();
        DescriptionLists=new ArrayList<>();
        UidList=new ArrayList<>();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlay);
        navbar = (NavigationView) findViewById(R.id.drawer);
        navbar.bringToFront();
        header = navbar.getHeaderView(0);
        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mtoggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mtoggle.syncState();
        fAuth= FirebaseAuth.getInstance();
        searchbarr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                }
                else{
                    BookNameLists.clear();
                    AuthorNameLists.clear();
                    PicLists.clear();
                    PublisherLists.clear();
                    DescriptionLists.clear();
                    UidList.clear();
                }
            }

            private void setAdapter(final String searchedString) {
                referencee.child("BookDB").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        BookNameLists.clear();
                        AuthorNameLists.clear();
                        PicLists.clear();
                        PublisherLists.clear();
                        DescriptionLists.clear();
                        UidList.clear();

                        int counter=0;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String uid = snapshot.getKey();
                            String desc = snapshot.child("Desc").getValue(String.class);
                            String bookname = snapshot.child("bookname").getValue(String.class);
                            String author = snapshot.child("author").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            String publisher = snapshot.child("Publisher").getValue(String.class);
                            String link=snapshot.child("link").getValue(String.class);
                            String decscription=snapshot.child("Desc").getValue(String.class);

                            try {

                                if ((bookname.toLowerCase().contains(searchedString.toLowerCase())) || (author.toLowerCase().contains(searchedString.toLowerCase()))) {
                                    UidList.add(uid);
                                    BookNameLists.add(bookname);
                                    AuthorNameLists.add(author);
                                    PublisherLists.add(publisher);
                                    PicLists.add(image);
                                    DescriptionLists.add(decscription);
                                    LinkLists.add(link);
                                    counter++;
                                }
                                if(BookNameLists.isEmpty() && AuthorNameLists.isEmpty()){
                                    Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception e){
                            }
                            if(counter==15){
                                break;
                            }
                            AdminSearchAdapter adminSearchAdapter = new AdminSearchAdapter(AdminSearch.this, BookNameLists, AuthorNameLists, PicLists, PublisherLists,DescriptionLists,LinkLists,UidList);
                            recyclerVieww.setAdapter(adminSearchAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawers();

                if(id== R.id.home) {

                    Intent search = new Intent(getApplicationContext(), AdminHome.class);
                    startActivity(search);


                }

                else if(id== R.id.EditBooks) {
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if(id== R.id.addbooksadmin) {
                    Intent sendtopro=new Intent(getApplicationContext(), Addbooks.class);
                    startActivity(sendtopro);
                }
                else if(id== R.id.tempdb) {

                    Intent adbk = new Intent(getApplicationContext(), TempBooksLayout.class);
                    startActivity(adbk);
                }
                else if(id== R.id.Logoutbtn) {
                    fAuth.signOut();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
