package app.com.blogapi.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.List;
import app.com.blogapi.entidades.Comments;
import app.com.blogapi.R;

public class AdaptadorComentario extends RecyclerView.Adapter<AdaptadorComentario.ViewHolder> {

    public List<Comments> dataComments;

    public AdaptadorComentario(List<Comments> dataComments) {
        this.dataComments = dataComments;
    }

    @NonNull
    @Override
    public AdaptadorComentario.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorComentario.ViewHolder holder, int position) {

        Comments comments = dataComments.get(position);
        Date date = new Date(comments.getCreatedAt());

        holder.author.setText(comments.getUserName()+" ("+comments.getUserEmail()+")");
        holder.date.setText(String.valueOf(date));
        holder.comment.setText(comments.getBody());
    }

    @Override
    public int getItemCount() {
        return dataComments.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView author, date, comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        author = itemView.findViewById(R.id.commentAuthor);
        date = itemView.findViewById(R.id.commentDate);
        comment = itemView.findViewById(R.id.commentBody);

        }
    }



}
