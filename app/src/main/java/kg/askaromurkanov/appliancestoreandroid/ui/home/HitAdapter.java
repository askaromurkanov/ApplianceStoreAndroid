package kg.askaromurkanov.appliancestoreandroid.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.util.ArrayList;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.R;
import kg.askaromurkanov.appliancestoreandroid.data.App;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.ItemProductBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.productCard.ProductCardFragment;


public class HitAdapter extends RecyclerView.Adapter<HitAdapter.ViewHolder> {
    private List<Product> products = new ArrayList<>();
    private ProductDao productDao;
    private HomeFragment homeFragment;
    private AppDatabase appDatabase;

    public void setList(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding itemProductBinding = ItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        ViewHolder viewHolder = new ViewHolder(itemProductBinding);

        appDatabase = Room.databaseBuilder(parent.getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        productDao = appDatabase.productDao();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        String fullName = product.getName()+" "+product.getFactory()+" "+product.getModel();
        holder.binding.productName.setText(fullName);
        holder.binding.productImage.setImageResource(product.getImage());
        double priceWithDiscount = product.getPrice()-(product.getPrice()*product.getDiscount())/100;
        String price = "$"+priceWithDiscount;
        holder.binding.productPrice.setText(price);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment productCardFragment = new ProductCardFragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("product", product);
//                productCardFragment.setArguments(bundle);

                Bundle args = new Bundle();
                args.putInt("productId", product.getId());

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ProductCardFragment();

                myFragment.setArguments(args);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, myFragment)
                        .commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding binding;

        public ViewHolder(@NonNull ItemProductBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

    }

    public void filterdList(ArrayList<Product> filteredList){
        products = filteredList;
        notifyDataSetChanged();
    }
}
