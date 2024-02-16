package algonquin.cst2335.findmyrecipe.recyclerviews.ingredientmgr;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.findmyrecipe.R;
import algonquin.cst2335.findmyrecipe.database.AppDatabase;
import algonquin.cst2335.findmyrecipe.database.DbHolder;
import algonquin.cst2335.findmyrecipe.database.models.PantryModel;
import algonquin.cst2335.findmyrecipe.databinding.RecyclerPantryRowBinding;

public class IngredientRecycler extends RecyclerView.Adapter<IngredientRecycler.RowHolder> {

    private final Context context;

    private final IngredientRecyclerRowModel rowModel;

    private AppDatabase db_ref;

    public static class RowHolder extends RecyclerView.ViewHolder  {

        IngredientRecyclerRowData rowData;

        public RowHolder(@NonNull View itemView)  { super(itemView); }

    }

    public IngredientRecycler(Context context, IngredientRecyclerRowModel row_model, AppDatabase db_ref)  {
        this.context = context;
        this.rowModel = row_model;
        this.db_ref = db_ref;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerPantryRowBinding binding = RecyclerPantryRowBinding.inflate( LayoutInflater.from( parent.getContext() ), parent, false );
        return new RowHolder( binding.getRoot() );
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.rowData = this.rowModel.getRow(position);

        TextView name_view = holder.itemView.findViewById(R.id.pantryrow_name);
        CheckBox check = holder.itemView.findViewById(R.id.pantryrow_check);
        ImageButton delete = holder.itemView.findViewById(R.id.pantryrow_delete);



        //img_view.setImageBitmap( BitmapFactory.decodeResource(context.getResources(), R.drawable.test_apple));

        name_view.setText( holder.rowData.getName() );
        check.setChecked( holder.rowData.isChecked() );

        check.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            holder.rowData.setChecked(isChecked);

            PantryModel row = new PantryModel();
            row.selected = isChecked;
            row.name = holder.rowData.name;
            row.ingredient_id = holder.rowData.ingredient_id;

            Executor get_uoms = Executors.newSingleThreadExecutor();
            get_uoms.execute(() -> {
                DbHolder.getDb().PantryDao().updateIngredient(row);
            });


        });

        delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to delete this ingredient?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Executor delete = Executors.newSingleThreadExecutor();
                            delete.execute(() -> {
                                PantryModel row = new PantryModel();
                                row.selected = holder.rowData.isChecked();
                                row.name = holder.rowData.name;
                                row.ingredient_id = holder.rowData.ingredient_id;
                                DbHolder.getDb().PantryDao().deleteIngredient(row);

                                rowModel.removeRow(holder.getAdapterPosition());

                                Activity activity = (Activity) context;
                                activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        notifyDataSetChanged();
                                    }
                                });



                            });

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            builder.create().show();
        });

        /*
        holder.itemView.setOnClickListener(v ->  {
            Toast.makeText(context, "row deleted", Toast.LENGTH_LONG).show();

            Executor get_uoms = Executors.newSingleThreadExecutor();
            get_uoms.execute(() -> {
                //                                                       ¯\_(ツ)_/¯
                //PantryModel to_delete = DbHolder.getDb().PantryDao().getIngredientById( holder.rowData.getIngredientId() );
                //DbHolder.getDb().PantryDao().deleteIngredient(to_delete);
            });
            this.rowModel.removeRow(position);
            this.notifyDataSetChanged();

        });

         */

    }

    @Override
    public int getItemCount() {
        return this.rowModel.getSize();
    }

}
