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

import java.util.List;

/**
 * Created by deemounus on 7/17/16.
 */
public class MusicCardsAdapter extends RecyclerView.Adapter<MusicCardsAdapter.MusicCardsViewHolder> {
    private List<MusicCardsData> cardsData;
    private Context context;

    public MusicCardsAdapter(List<MusicCardsData> cardsData){
     this.cardsData = cardsData;
    }

    @Override
    public int getItemCount(){
        return cardsData.size();
    }

    @Override
    public void onBindViewHolder(MusicCardsViewHolder contactViewHolder, int i) {
        MusicCardsData cd = cardsData.get(i);

        context = contactViewHolder.vCardPic.getContext();
        Picasso.with(context).load(cd.cardPicture).into(contactViewHolder.vCardPic);
        Log.v("Adapter data: ", cd.cardPicture);

    }

    @Override
    public MusicCardsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view, viewGroup, false);

        return new MusicCardsViewHolder(itemView);
    }

    public static class MusicCardsViewHolder extends RecyclerView.ViewHolder {

        protected ImageView vCardPic;

        public MusicCardsViewHolder (View v) {
            super(v);
//            vCardPic =  (TextView) v.findViewById(R.id.cardPic);
            vCardPic = (ImageView) v.findViewById(R.id.cardPic);
            v.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.v("Adapter", "Item is clicked: " + getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("adapterPosition", getAdapterPosition());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
