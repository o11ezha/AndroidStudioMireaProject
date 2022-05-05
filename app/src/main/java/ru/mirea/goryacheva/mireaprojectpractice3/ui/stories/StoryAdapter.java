package ru.mirea.goryacheva.mireaprojectpractice3.ui.stories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.goryacheva.mireaprojectpractice3.R;

public class StoryAdapter extends  RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    public List<Story> stories;

    public StoryAdapter(List<Story> stories) { this.stories = stories; }

    @NonNull
    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.ViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.addName.setText(story.name);
        holder.addDecs.setText(story.description);
    }

    @Override
    public int getItemCount() { return stories.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView addName;
        public TextView addDecs;

        public ViewHolder(View itemView) {
            super(itemView);
            addName = itemView.findViewById(R.id.textnameStory);
            addDecs = itemView.findViewById(R.id.textStory);
        }
    }
}