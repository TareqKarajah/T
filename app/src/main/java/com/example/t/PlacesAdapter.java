package com.example.t;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(JapanPlace place);
    }

    private ArrayList<JapanPlace> placesList;
    private ArrayList<JapanPlace> placesListFull;
    private OnItemClickListener listener;

    public PlacesAdapter(ArrayList<JapanPlace> placesList, OnItemClickListener listener) {
        this.placesList = placesList;
        this.listener = listener;
    }

    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<JapanPlace> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(placesListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (JapanPlace place : placesListFull) {
                    if (place.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(place);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            placesList.clear();
            placesList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JapanPlace place = placesList.get(position);
        holder.name.setText(place.getName());
        holder.description.setText(place.getDescription());
        holder.imageView.setImageResource(place.getImageResourceId());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(place));
    }
    @Override
    public int getItemCount() {
        return placesList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.placeName);
            description = itemView.findViewById(R.id.placeDescription);
            imageView = itemView.findViewById(R.id.placeImage);
        }
    }
}
