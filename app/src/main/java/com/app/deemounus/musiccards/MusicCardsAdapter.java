package com.app.deemounus.musiccards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MusicCardsAdapter extends RecyclerView.Adapter<MusicCardsAdapter.MusicCardsViewHolder> {
    static List<MusicCardsData> cardsData;
    static boolean ismTwoPane;

    public MusicCardsAdapter(List<MusicCardsData> cardsData, boolean ismTwoPane) {
        this.cardsData = cardsData;
        this.ismTwoPane = ismTwoPane;
    }

    @Override
    public int getItemCount() {
        return cardsData.size();
    }

    @Override
    public void onBindViewHolder(MusicCardsViewHolder contactViewHolder, int i) {
        try {
            contactViewHolder.bindView(i);
        } catch(Exception e) {e.printStackTrace();}
        }


        @Override
        public MusicCardsViewHolder onCreateViewHolder (ViewGroup viewGroup,int i){
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_view, viewGroup, false);

            return new MusicCardsViewHolder(itemView);
        }


        public static class MusicCardsViewHolder extends RecyclerView.ViewHolder {

            protected ImageView vCardPic;
            private String imgURL;
            private String musicURL;
            String LOG_TAG = getClass().getSimpleName();

            public static void startDetailActivity(Context ctx, String imgUrl, String musicUrl){
                Intent intent = new Intent(ctx, DetailActivity.class);
                intent.putExtra("imgUrl", imgUrl);
                intent.putExtra("musicUrl", musicUrl);
                ctx.startActivity(intent);
            }

            public MusicCardsViewHolder(View v) {
                super(v);
                vCardPic = (ImageView) v.findViewById(R.id.cardPic);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.v(LOG_TAG, "Item is clicked: " + getAdapterPosition());
                        if(!ismTwoPane) {
                            startDetailActivity(v.getContext(), imgURL, musicURL);
//                            Log.v("Music URL for Activity", musicURL);
                        }
//                        else {
                            // Add handler for Tablet UI
//                            Bundle mBundle = new Bundle();
//                            mBundle.putString("imgUrl", imgURL);
//                            mBundle.putString("musicUrl", musicURL);
//                            DetailActivityFragment fragment = new DetailActivityFragment();
//                            fragment.setArguments(mBundle);
//                            ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragmentDetail, fragment)
//                                    .commit();
//                        }
                    }
                });
            }

            public void bindView(int i){
                Context context;
                context = vCardPic.getContext();
                MusicCardsData cd = cardsData.get(i);
                imgURL = cd.cardPicture;
                musicURL = cd.cardMusic;
//                Log.v("Image from Adapter", imgURL);
//                Log.v("Music from Adapter", musicURL);
                Picasso.with(context)
                        .load(imgURL)
                        .resize(400,400)
                        .error(R.drawable.image_placeholder)
                        .centerCrop()
                        .into(vCardPic);
            }
        }
    }

