package smb.pja.smbproject.first.list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.db.DataBaseHandler;

class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private List<Item> items;

    public MyListAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        return new MyListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Item select = items.get(i);

        viewHolder.setIdItem(select.getId());
        viewHolder.name.setText(select.getProductName());
        viewHolder.price.setText(select.getPrice().toString());
        viewHolder.amount.setText(select.getAmount().toString());
        viewHolder.state.setChecked(select.isBought());
        }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnLongClickListener {

        private DataBaseHandler db;

        private Integer idItem;
        private TextView name;
        private TextView price;
        private TextView amount;
        private CheckBox state;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            db = new DataBaseHandler(itemView.getContext());
            itemView.setOnLongClickListener(this);
            name = itemView.findViewById(R.id.list_row_name_textView);
            price = itemView.findViewById(R.id.list_row_price_textView);
            amount = itemView.findViewById(R.id.list_row_amount_textView);
            state = itemView.findViewById(R.id.list_row_state_checkBox);
            setOnClickListener();
        }


        public void setIdItem(Integer idItem) {
            this.idItem = idItem;
        }

        @Override
        public boolean onLongClick(View v) {
            Item item = new Item(idItem, name.getText().toString(),
                    Float.valueOf(price.getText().toString()),
                    Integer.valueOf(amount.getText().toString()),
                    state.isChecked());
            Intent intent = new Intent(v.getContext(), AddElementActivity.class);
            intent.putExtra("old", item);
            v.getContext().startActivity(intent);
            return true;
        }

        private void setOnClickListener() {
            state.setOnClickListener(v -> {
                db.updateCheckbox(idItem, state.isChecked());
            });
        }

    }
}
