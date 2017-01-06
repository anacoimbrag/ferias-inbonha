package br.inhonhas.feriapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.inhonhas.feriapp.Event;
import br.inhonhas.feriapp.R;

/**
 * Created by Ana Coimbra on 05/01/2017.
 */

public class OutAdapter extends RecyclerView.Adapter<OutAdapter.ViewHolder> {

    List<Event> events;

    public OutAdapter(List<Event> events) {
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.partial_out_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);
        if(event != null) {
            holder.nameTextView.setText(event.getName());
            holder.locationTextView.setText(event.getAddress());
            holder.dateTextView.setText(event.getWhen());
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView dateTextView;
        TextView locationTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
            locationTextView = (TextView) itemView.findViewById(R.id.place);
        }
    }
}
