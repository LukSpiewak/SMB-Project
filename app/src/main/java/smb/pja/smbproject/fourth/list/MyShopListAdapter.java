package smb.pja.smbproject.fourth.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import smb.pja.smbproject.R;
import smb.pja.smbproject.fourth.Shop;

class MyShopListAdapter extends RecyclerView.Adapter<MyShopListAdapter.ViewHolder> {

    List<Shop> shopList;

    MyShopListAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public MyShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shop_row, viewGroup, false);

        return new MyShopListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Shop shop = shopList.get(i);

        viewHolder.idItem = shop.getId();
        viewHolder.name.setText(shop.getName());
        viewHolder.description.setText(shop.getDescription());
        viewHolder.localization.setText(shop.getLat() + ", " + shop.getLon());
        viewHolder.radius.setText(String.valueOf(shop.getRadius()));
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Integer idItem;
        private TextView name;
        private TextView description;
        private TextView localization;
        private TextView radius;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shop_row_name_textView);
            description = itemView.findViewById(R.id.shop_row_description_textView);
            localization = itemView.findViewById(R.id.shop_row_location_textView);
            radius = itemView.findViewById(R.id.shop_row_radius_textView);
        }
    }
}
