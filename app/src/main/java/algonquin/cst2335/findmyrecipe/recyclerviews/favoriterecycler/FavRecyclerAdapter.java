package algonquin.cst2335.findmyrecipe.recyclerviews.favoriterecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.findmyrecipe.R;

public class FavRecyclerAdapter extends RecyclerView.Adapter<FavRecyclerViewHolder> {

    private List<FavRecyclerRowData> rowDataList;

    public FavRecyclerAdapter(List<FavRecyclerRowData> rowDataList) {
        this.rowDataList = rowDataList;
    }

    @NonNull
    @Override
    public FavRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_fav_row, parent, false);
        return new FavRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavRecyclerViewHolder holder, int position) {
        FavRecyclerRowData rowData = rowDataList.get(position);
        // Bind the data to the ViewHolder
        holder.bindData(rowData);
    }

    @Override
    public int getItemCount() {
        return rowDataList.size();
    }
}
