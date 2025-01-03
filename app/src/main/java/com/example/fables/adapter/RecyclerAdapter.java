package com.example.fables.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fables.R;
import com.example.fables.data.model.Fable;
import com.example.fables.data.model.RecyclerFable;
import com.example.fables.ui.FableDetailsActivity;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private final Context context;
    private final List<Fable> fables;

    public RecyclerAdapter(Context context, List<Fable>  fables){
        this.context = context;
        this.fables = fables;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.fable.setText(fables.get(position).getTitle());
        holder.author.setText(fables.get(position).getAuthor());
        holder.number.setText(String.valueOf(position + 1));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FableDetailsActivity.class);
            intent.putExtra("fable_title", fables.get(position).getTitle());
            intent.putExtra("fable_content", fables.get(position).getContent());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return fables.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView fable;
        private final TextView author;
        private final TextView number;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            fable = itemView.findViewById(R.id.fable_title);
            author = itemView.findViewById(R.id.fable_author);
            number = itemView.findViewById(R.id.fable_number);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            String currentText = fable.getText().toString();
            String authorText = author.getText().toString();

        }
    }
}
