package com.example.myapplication.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminSearchAdapter extends RecyclerView.Adapter<AdminSearchAdapter.EditViewHolder> {
    Context c;
    ArrayList<String> BookNameLists;
    ArrayList<String> AuthorNameLists;
    ArrayList<String> PicLists;
    ArrayList<String> CategoryList;
    LinearLayout booklayoutt;
    ArrayList<String>IsbnList;
//    FirebaseDatabase db;

    DatabaseReference referencee;
//    public  String key;

    class EditViewHolder extends RecyclerView.ViewHolder{
        ImageView bookimages;
        TextView booknamess, authornamess;


        public EditViewHolder(@NonNull View itemView) {
            super(itemView);
            bookimages =  itemView.findViewById(R.id.Bookimg);
            booknamess =  itemView.findViewById(R.id.BookName);
            authornamess =  itemView.findViewById(R.id.BookAuthor);
            booklayoutt=itemView.findViewById(R.id.LinLayout);
            referencee = FirebaseDatabase.getInstance().getReference();
            referencee.keepSynced(true);

        }
    }

    public AdminSearchAdapter(Context c1, ArrayList<String> bookNameLists, ArrayList<String> authorNameLists, ArrayList<String> picLists,ArrayList<String> isbnList,ArrayList<String> categoryList) {
        c = c1;
        BookNameLists = bookNameLists;
        AuthorNameLists = authorNameLists;
        PicLists = picLists;
        IsbnList=isbnList;
        CategoryList=categoryList;
    }
    @NonNull
    @Override
    public AdminSearchAdapter.EditViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.activity_search_layout,parent,false);
        return new AdminSearchAdapter.EditViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final EditViewHolder holder, final int position) {
        holder.booknamess.setText(BookNameLists.get(position));
        holder.authornamess.setText(AuthorNameLists.get(position));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(c, AdminBookdetails.class);
                i.putExtra("booknames",BookNameLists.get(position));
                i.putExtra("Image",PicLists.get(position));
                i.putExtra("author_name",AuthorNameLists.get(position));
                i.putExtra("isbn",IsbnList.get(position));
                i.putExtra("category",CategoryList.get(position));


                c.startActivity(i);
            }
        });
        Glide.with(c).asBitmap().load(PicLists.get(position)).placeholder(R.mipmap.ic_launcher_round).into(holder.bookimages);
    }
    @Override
    public int getItemCount() {
        return BookNameLists.size();
    }
}
