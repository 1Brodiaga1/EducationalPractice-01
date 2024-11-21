package com.example.sciencelab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sciencelab.Experiment;

import java.util.List;

public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ExperimentViewHolder> {

    private List<Experiment> experimentList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Experiment experiment);
    }

    public ExperimentAdapter(Context context, List<Experiment> experimentList, OnItemClickListener listener) {
        this.context = context;
        this.experimentList = experimentList;
        this.listener = listener;
    }

    @Override
    public ExperimentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_experiment, parent, false);
        return new ExperimentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExperimentViewHolder holder, int position) {
        Experiment experiment = experimentList.get(position);
        holder.titleTextView.setText(experiment.getTitle());
        holder.descriptionTextView.setText(experiment.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(experiment));
    }

    @Override
    public int getItemCount() {
        return experimentList.size();
    }

    public static class ExperimentViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, descriptionTextView;

        public ExperimentViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            descriptionTextView = view.findViewById(R.id.descriptionTextView);
        }
    }
}
