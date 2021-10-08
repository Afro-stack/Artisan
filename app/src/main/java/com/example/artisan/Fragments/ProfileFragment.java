package com.example.artisan.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.artisan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView image_profile;
    private ImageView settings;
    private TextView pieces;
    private TextView admirers;
    private TextView admiring;
    private TextView fullname;
    private TextView username;
    private TextView bio;

    private ImageView pictures;
    private FirebaseUser fUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        image_profile = view.findViewById(R.id.image_profile);
        settings = view.findViewById(R.id.settings);
        admirers = view.findViewById(R.id.admirers);
        admiring = view.findViewById(R.id.admiring);
        pieces = view.findViewById(R.id.pieces);
        fullname = view.findViewById(R.id.fullname);
        username = view.findViewById(R.id.username);
        bio = view.findViewById(R.id.bio);
        pictures = view.findViewById(R.id.recycler_view_pictures);

        return view;
    }
}