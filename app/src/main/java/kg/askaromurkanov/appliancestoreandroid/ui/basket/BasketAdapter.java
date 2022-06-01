package kg.askaromurkanov.appliancestoreandroid.ui.basket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.Dao.BasketDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.UserDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.ItemProductBasketBinding;
import kg.askaromurkanov.appliancestoreandroid.databinding.ItemProductHorizontalBinding;
import kg.askaromurkanov.appliancestoreandroid.ui.home.DiscountAdapter;
import kg.askaromurkanov.appliancestoreandroid.ui.home.HomeFragment;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private List<Product> products = new ArrayList<>();
    private AppDatabase appDatabase;

    private BasketDao basketDao;
    private ProductDao productDao;
    private UserDao userDao;

    public void setList(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBasketBinding itemProductBasketBinding = ItemProductBasketBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        appDatabase = Room.databaseBuilder(parent.getContext().getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        productDao = appDatabase.productDao();
        basketDao = appDatabase.basketDao();


        ViewHolder viewHolder = new ViewHolder(itemProductBasketBinding);
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

        if(product.getDiscount()>0) {
            String discount = "-" + product.getDiscount() + "%";
            holder.binding.productDiscount.setText(discount);
        }
        else {
            holder.binding.productDiscount.setVisibility(View.GONE);
        }

        holder.binding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basketDao.deleteByProductId(product.getId());
                products.remove(products.indexOf(product));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBasketBinding binding;

        public ViewHolder(@NonNull ItemProductBasketBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
