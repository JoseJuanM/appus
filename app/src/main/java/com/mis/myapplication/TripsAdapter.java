package com.mis.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.mis.myapplication.entity.Trip;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {
    private List<Trip> tripList;
    Context context;
    public TripsAdapter(List<Trip> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Trip trip=tripList.get(i);
        holder.title.setText(trip.getTitle());


        if(!trip.getImageLink().isEmpty())
        Picasso.get().load(trip.getImageLink()).placeholder(R.drawable.ic_placeholder).into(holder.image);
        holder.description.setText(trip.getPrice()+"€"+"\nSalida : "+trip.getStartDate()+"\nLlegada : "+trip.getEndDate());

        if(trip.isSelected()){
            holder.imageSelected.setImageResource(R.drawable.ic_baseline_star_24);
        }
        else{
            holder.imageSelected.setImageResource(R.drawable.ic_star);
        }

        holder.imageSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trip.setSelected(!trip.isSelected());
                FirebaseDatabase.getInstance().getReference("Trips").child(trip.getId()).setValue(trip);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,TripDetailActivity.class);
                intent.putExtra("Trip", trip);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title, description;
        private ImageView image;
        private ImageButton imageSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desciption);
            image = itemView.findViewById(R.id.image);
            imageSelected = itemView.findViewById(R.id.imgButton);
        }
    }

}
