package com.example.myapplication.User;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class Bkhomeholder extends RecyclerView.ViewHolder {
    public TextView title,name,email;
    public ImageView bookimg,profilepic;
    public CardView cardView;


    public Bkhomeholder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.bkdettitle);
        name = itemView.findViewById(R.id.unamedisplay);
        email = itemView.findViewById(R.id.emaildisplay);
        profilepic = itemView.findViewById(R.id.profileimg);
        bookimg = itemView.findViewById(R.id.bkdetimg);
        cardView=itemView.findViewById(R.id.bookcardview);


    }
}





