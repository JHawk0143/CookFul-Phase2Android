package algonquin.cst2335.findmyrecipe.recyclerviews.favoriterecycler;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.findmyrecipe.R;
import algonquin.cst2335.findmyrecipe.fragments.FavoriteFragment;

public class FavRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView1;
    private Context context;

    public FavRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        imageView = itemView.findViewById(R.id.favrow_thumbnail);
        textView1 = itemView.findViewById(R.id.favrow_thing1);
        itemView.setOnClickListener(this);
    }

    public void bindData(FavRecyclerRowData rowData) {
        textView1.setText(rowData.getName());
    }

    @Override
    public void onClick(View v) {
        // Handle click event
        // Navigate to the FavouriteFragment
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFragment, new FavoriteFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
