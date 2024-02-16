package algonquin.cst2335.findmyrecipe.recyclerviews.searchrecycler;

import android.content.Context;
import android.graphics.BitmapFactory;
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
import algonquin.cst2335.findmyrecipe.databinding.SearchRowBinding;
import algonquin.cst2335.findmyrecipe.fragments.SearchFragmentDialog;


public class SearchRecycler extends RecyclerView.Adapter<SearchRecycler.RowHolder>  {

    protected final Context context;
    private final SearchRecyclerRowModel rowModel;
    public static class RowHolder extends RecyclerView.ViewHolder  {

        SearchRecyclerRowData rowData;

        public RowHolder(@NonNull View itemView, Context context)  {
            super(itemView);



        }

    }

    public SearchRecycler(Context context, SearchRecyclerRowModel row_model)  {
        this.context = context;
        this.rowModel = row_model;
    }

    @NonNull
    @Override
    public SearchRecycler.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchRowBinding binding = SearchRowBinding.inflate( LayoutInflater.from( parent.getContext() ), parent, false );
        return new SearchRecycler.RowHolder( binding.getRoot(), context );
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.rowData = this.rowModel.getRow(position);

        ImageView img = holder.itemView.findViewById(R.id.searchrow_thumbnail);
        TextView name = holder.itemView.findViewById(R.id.searchrow_name);
        TextView used = holder.itemView.findViewById((R.id.searchrow_used));
        TextView unused = holder.itemView.findViewById((R.id.searchrow_unused));
        TextView missing = holder.itemView.findViewById((R.id.searchrow_missing));

        if(holder.rowData.getImage() == null)  {
            //check to see if the image happens to already exist
            if( ApiBitmapHolder.getImages().getValue().containsKey( holder.rowData.getImageKey() ) )  {
                img.setImageBitmap( ApiBitmapHolder.getImage( holder.rowData.getImageKey() ) );
            }
            else  {
                img.setImageBitmap( BitmapFactory.decodeResource(context.getResources(), R.drawable.test_apple));
            }

        }
        else  {
            img.setImageBitmap( holder.rowData.getImage() );
        }

        name.setText( holder.rowData.getName() );

        holder.rowData.setPosition(position);

        int check_for_complex = holder.rowData.getMissing() + holder.rowData.getUsed() + holder.rowData.getUnused();
        if(check_for_complex > 0)  {
            used.setVisibility( View.VISIBLE );
            unused.setVisibility( View.VISIBLE );
            missing.setVisibility( View.VISIBLE );
            holder.itemView.findViewById(R.id.searchrow_missing_title).setVisibility( View.VISIBLE );
            holder.itemView.findViewById(R.id.searchrow_unused_title).setVisibility( View.VISIBLE );
            holder.itemView.findViewById(R.id.searchrow_used_title).setVisibility( View.VISIBLE );
            used.setText( String.valueOf(holder.rowData.getUsed()) );
            unused.setText( String.valueOf(holder.rowData.getUnused()) );
            missing.setText( String.valueOf(holder.rowData.getMissing()) );
        }
        else  {
            used.setVisibility( View.INVISIBLE );
            unused.setVisibility( View.INVISIBLE );
            missing.setVisibility( View.INVISIBLE );
            holder.itemView.findViewById(R.id.searchrow_missing_title).setVisibility( View.INVISIBLE );
            holder.itemView.findViewById(R.id.searchrow_unused_title).setVisibility( View.INVISIBLE );
            holder.itemView.findViewById(R.id.searchrow_used_title).setVisibility( View.INVISIBLE );
        }

        holder.itemView.setOnClickListener( v -> {

            final FragmentActivity activity = (FragmentActivity) context;

            SearchFragmentDialog dialog = new SearchFragmentDialog();
            FragmentManager frag = ((FragmentActivity) context).getSupportFragmentManager();
            Bundle args = new Bundle();
            //int position = getAdapterPosition();

            args.putString("name", holder.rowData.getName());
            args.putInt("apid", holder.rowData.getApid());

            //args.put
            dialog.setArguments(args);
            dialog.setAdapter(this);
            dialog.show(frag, "search_dialog");

        });


    }

    @Override
    public int getItemCount() {
        return this.rowModel.getSize();
    }


}
