package edu.msudenver.roadrunners.inventory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.msudenver.roadrunners.R;
import edu.msudenver.roadrunners.activities.InventoryDetailActivity;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.ItemViewHolder> {

    private List<InventoryItem> itemsList;

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private int id;
        private TextView title;
        private TextView shelf;
        private TextView qty;

        ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), title.getText().toString(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(itemView.getContext(), InventoryDetailActivity.class);
                    intent.putExtra("itemId", id);
                    itemView.getContext().startActivity(intent);
                }
            });

            title = itemView.findViewById(R.id.txtItemTitle);
            shelf = itemView.findViewById(R.id.txtItemShelf);
            qty = itemView.findViewById(R.id.txtQty);
        }
    }

    public InventoryItemAdapter() {
        this.itemsList = new ArrayList<>();
    }

    public void setItemsList(List<InventoryItem> items) {
        this.itemsList = items;
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
        InventoryItem item = itemsList.get(i);
        System.out.println("Got item: " + item);
        itemViewHolder.id = item.getId();
        itemViewHolder.title.setText(item.getName());
        itemViewHolder.shelf.setText(item.getShelf());
        itemViewHolder.qty.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

}
