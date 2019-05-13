package edu.msudenver.roadrunners.inventory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.activities.InventoryDetailActivity;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.ItemViewHolder>
        implements Filterable {

    private List<InventoryItem> itemsList;
    private List<InventoryItem> itemsListFiltered;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                System.out.println("Performing filtering");
                String sequence = constraint.toString();
                List<InventoryItem> items = new ArrayList<>();

                if (sequence.isEmpty()) {
                    itemsListFiltered = itemsList;

                } else {
                    for (InventoryItem item : itemsList) {
                        if (item.getName().toLowerCase().contains(sequence.toLowerCase())) {
                            items.add(item);
                        }
                    }
                    itemsListFiltered = items;
                }

                FilterResults results = new FilterResults();
                results.values = itemsListFiltered;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsListFiltered = (ArrayList<InventoryItem>) results.values;

                notifyDataSetChanged();
            }
        };
    }

    /*
     * Created for each list item, instantiates references to view controls for use by onBindViewHolder method
     * */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private int id;
        private TextView title;
        private TextView shelf;
        private TextView box;
        private TextView qty;
        private ImageView thumbnail;

        ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), InventoryDetailActivity.class);
                    intent.putExtra("itemId", id);
                    itemView.getContext().startActivity(intent);
                }
            });

            title = itemView.findViewById(R.id.txtItemTitle);
            shelf = itemView.findViewById(R.id.txtItemShelf);
            box = itemView.findViewById(R.id.txtItemBox);
            qty = itemView.findViewById(R.id.txtQty);
            thumbnail = itemView.findViewById(R.id.imgThumb);
        }
    }

    public InventoryItemAdapter() {
        this.itemsList = new ArrayList<>();
        this.itemsListFiltered = itemsList;
    }

    public void setItemsList(List<InventoryItem> items) {
        this.itemsList = items;
        this.itemsListFiltered = itemsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_inventory_list_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        InventoryItem item = itemsListFiltered.get(i);
        itemViewHolder.id = item.getId();
        itemViewHolder.title.setText(item.getName());
        itemViewHolder.shelf.setText(item.getShelf());
        itemViewHolder.box.setText(item.getBox());
        itemViewHolder.qty.setText(String.valueOf(item.getQuantity()));
        Bitmap image = item.getThumbnailBitmap();
        if (image != null)
            itemViewHolder.thumbnail.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return itemsListFiltered.size();
    }

}
