package a9.ybond.mobiletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//16.methods and class of ViewHolder are added
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    // 17.
    private List<Item> items;
    private Context context;
    // 32. in order to pass onItemListner to delete
    private OnItemListener onItemListener;

    // 19. recycler_row.xml content is reused from Android Chat
    // 20. Building a constructor for our RecycleAdapter


    // 32.
    public RecycleAdapter(Context context, List<Item> items, OnItemListener onItemListener)
    {
        this.items = items;
        this.context = context;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
     // RecycleAdapter. before ViewHolder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Delete RecycleAdapter???

        // 18.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);

        // we are going to return a new holder holding the row
        // 35. adding onItemListener parameter
        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position)
    {
        // 24. Use our holder that is passed in, and get textItem
        holder.txtItem.setText(items.get(position).getDescription());

    }

    @Override
    public int getItemCount()
    {
        // 23. We are going to need the size of the item
        return items.size();
    }

    // 21. Building a constructor for ViewHolder
    // 33. adding implements View.OnClickListener
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 22. we need to a reference to the recycler row
        TextView txtItem;
        OnItemListener onItemListener;
        // 35. This ViewHolder constructor also needs to take in
        // OnItemListener and store it inside the constructor
        public ViewHolder(View itemView, OnItemListener onItemListener)

        {
            super(itemView);
            // @53:27
            this.onItemListener = onItemListener;
            txtItem = itemView.findViewById(R.id.txtItemView);
            // 36. the first parameter has to be set too
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view)
        {
            // 34. getting the index of item clicked by the user
            onItemListener.onItemClicked(getAdapterPosition());
        }
    }








}
