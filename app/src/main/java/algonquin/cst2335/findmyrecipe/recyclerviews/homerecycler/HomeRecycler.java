package algonquin.cst2335.findmyrecipe.recyclerviews.homerecycler;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.findmyrecipe.R;
import algonquin.cst2335.findmyrecipe.api.ApiBitmapHolder;
import algonquin.cst2335.findmyrecipe.databinding.RecyclerHomeRowBinding;
import algonquin.cst2335.findmyrecipe.fragments.SearchFragmentDialog;

/**
 * Main recycler class for the home fragment
 */
public class HomeRecycler extends RecyclerView.Adapter<HomeRecycler.RowHolder> {

    private final Context context;
    private final HomeRecyclerRowController rowModel;
    private OnItemClickListener listener; // Click listener member variable

    /**
     *
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * Constructor
     * @param context app context
     * @param rowModel reference to the model being used for the recycler
     * @param listener recycler listener
     */
    public HomeRecycler(Context context, HomeRecyclerRowController rowModel, OnItemClickListener listener) {
        this.context = context;
        this.rowModel = rowModel;
        this.listener = listener;
    }

    /**
     * internal class to hold row data for the recycler
     */
    public static class RowHolder extends RecyclerView.ViewHolder {
        HomeRecyclerRowData rowData;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return inflated view
     */
    @NonNull
    @Override
    public HomeRecycler.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerHomeRowBinding binding = RecyclerHomeRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RowHolder(binding.getRoot());
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HomeRecycler.RowHolder holder, int position) {
        holder.rowData = this.rowModel.getRow(position);

        //References to the row widgets
        ImageView img = holder.itemView.findViewById(R.id.homerow_thumbnail);
        TextView title = holder.itemView.findViewById(R.id.homerow_title);

        img.setImageBitmap(ApiBitmapHolder.getImage( holder.rowData.image_key ));
        title.setText( holder.rowData.getTitle() );

        // Set the click listener for the row item1
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
            //Toast.makeText(context, "Row clicked", Toast.LENGTH_LONG).show();

            final FragmentActivity activity = (FragmentActivity) context;

            SearchFragmentDialog dialog = new SearchFragmentDialog();
            FragmentManager frag = ((FragmentActivity) context).getSupportFragmentManager();
            Bundle args = new Bundle();
            //int position = getAdapterPosition();

            args.putString("name", holder.rowData.getTitle());
            args.putInt("apid", holder.rowData.getApid());

            //args.put
            dialog.setArguments(args);
            dialog.setAdapter(this);
            dialog.show(frag, "search_dialog");


        });

    }

    /**
     * How many rows in the recycler?
     * @return row count
     */
    @Override
    public int getItemCount() {
        return this.rowModel.getSize();
    }



}
