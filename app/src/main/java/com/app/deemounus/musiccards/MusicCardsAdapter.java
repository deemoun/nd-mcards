package com.app.deemounus.musiccards;

import android.content.Context;
import android.content.Intent;
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

    public MusicCardsAdapter(List<MusicCardsData> cardsData) {
        this.cardsData = cardsData;
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
                        Log.v("Adapter", "Item is clicked: " + getAdapterPosition());
                        startDetailActivity(v.getContext(), imgURL, musicURL);
                        Log.v("Music URL for Activity", musicURL);
                    }
                });
            }

            public void bindView(int i){
                Context context;
                context = vCardPic.getContext();
                MusicCardsData cd = cardsData.get(i);
                imgURL = cd.cardPicture;
                musicURL = cd.cardMusic;
                Log.v("MusicCardsAdapter", imgURL);
                Log.v("Music from Adapter", musicURL);
                Picasso.with(context).load(imgURL).into(vCardPic);
            }
        }
    }

