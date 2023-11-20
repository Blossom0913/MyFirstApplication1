package com.jnu.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> addItemLauncher;

    ActivityResultLauncher<Intent> updateItemLauncher;
    ArrayList<ShopItem> shopItems = new ArrayList<>();

    ShopItemAdapter shopItemAdapter = new ShopItemAdapter(shopItems);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView mainRecyclerView = findViewById(R.id.recyclerview_main);

        registerForContextMenu(mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));




        shopItems.add(new ShopItem("青椒", 1.5, R.drawable.qingjiao));
        shopItems.add(new ShopItem("萝卜", 2.5, R.drawable.luobo));
        shopItems.add(new ShopItem("白菜", 3.5, R.drawable.baicai));


        mainRecyclerView.setAdapter(shopItemAdapter);
        addItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name"); // 获取返回的数据
                        String priceText = data.getStringExtra("price"); // 获取返回的数据

                        double price= Double.parseDouble(priceText);
                        shopItems.add(new ShopItem(name, price, R.drawable.baicai));
                        shopItemAdapter.notifyItemInserted(shopItems.size());

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );
        updateItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int position = data.getIntExtra("position",0);
                        String name = data.getStringExtra("name"); // 获取返回的数据
                        String priceText = data.getStringExtra("price"); // 获取返回的数据

                        double price= Double.parseDouble(priceText);
                        ShopItem shopItem = shopItems.get(position);
                        shopItem.setPrice(price);
                        shopItem.setName(name);
                        shopItemAdapter.notifyItemChanged(position);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(MainActivity.this, ShopItemDetailsActivity.class);
                addItemLauncher.launch(intent);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shopItems.remove(item.getOrder());
                        shopItemAdapter.notifyItemRemoved(item.getOrder());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;
            case 2:
                Intent intentUpdate = new Intent(MainActivity.this, ShopItemDetailsActivity.class);
                ShopItem shopItem= shopItems.get(item.getOrder());
                intentUpdate.putExtra("name",shopItem.getName());
                intentUpdate.putExtra("price",shopItem.getPrice());
                intentUpdate.putExtra("position",item.getOrder());
                updateItemLauncher.launch(intentUpdate);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {

        private ArrayList<ShopItem> shopItemArrayList;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewName;
            private final TextView textViewPrice;
            private final ImageView imageViewItem;

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
            }

            public ViewHolder(View shopItemView) {
                super(shopItemView);

                textViewName = shopItemView.findViewById(R.id.textview_item_name);
                textViewPrice = shopItemView.findViewById(R.id.textview_item_price);
                imageViewItem = shopItemView.findViewById(R.id.imageview_item);
                shopItemView.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewName() {
                return textViewName;
            }

            public TextView getTextViewPrice() {
                return textViewPrice;
            }

            public ImageView getImageViewItem() {
                return imageViewItem;
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param shopItems String[] containing the data to populate views to be used
         *                  by RecyclerView.
         */
        public ShopItemAdapter(ArrayList<ShopItem> shopItems) {
            shopItemArrayList = shopItems;
        }

        // Create new views (invoked by the layout manager)

        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.show_item_row, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewName().setText(shopItemArrayList.get(position).getName());
            viewHolder.getTextViewPrice().setText(shopItemArrayList.get(position).getPrice() + "");
            viewHolder.getImageViewItem().setImageResource(shopItemArrayList.get(position).getImageResourceId());
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return shopItemArrayList.size();
        }
    }
}
