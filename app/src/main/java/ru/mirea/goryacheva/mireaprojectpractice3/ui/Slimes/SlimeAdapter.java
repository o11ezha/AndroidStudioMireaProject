package ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.goryacheva.mireaprojectpractice3.R;

public class SlimeAdapter extends RecyclerView.Adapter<SlimeAdapter.ViewHolder> {

    public List<Slime> slimes;

    public SlimeAdapter(List<Slime> slimes) { this.slimes = slimes; }

    @NonNull
    @Override
    public SlimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slime_item, parent, false);
        return new SlimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlimeAdapter.ViewHolder holder, int position) {
        Slime slime = slimes.get(position);
        holder.addName.setText(slime.name);
        holder.addType.setText(slime.type);
        holder.addDiet.setText(slime.diet);
        holder.addFavmeal.setText(slime.favmeal);
        holder.addFavtoy.setText(slime.favtoy);
        holder.addPlort.setText(slime.plort);
    }

    @Override
    public int getItemCount() { return slimes.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView addName;
        public TextView addType;
        public TextView addDiet;
        public TextView addFavmeal;
        public TextView addFavtoy;
        public TextView addPlort;

        public ViewHolder(View itemView) {
            super(itemView);
            addName = itemView.findViewById(R.id.textnameSlime);
            addType = itemView.findViewById(R.id.texttypeSlime);
            addDiet = itemView.findViewById(R.id.textdietSlime);
            addFavmeal = itemView.findViewById(R.id.textfavmealSlime);
            addFavtoy = itemView.findViewById(R.id.textfavtoySlime);
            addPlort = itemView.findViewById(R.id.textplortSlime);
        }
    }
}