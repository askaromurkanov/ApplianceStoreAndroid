package kg.askaromurkanov.appliancestoreandroid.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.util.ArrayList;
import java.util.List;

import kg.askaromurkanov.appliancestoreandroid.data.App;
import kg.askaromurkanov.appliancestoreandroid.data.Dao.ProductDao;
import kg.askaromurkanov.appliancestoreandroid.data.models.Product;
import kg.askaromurkanov.appliancestoreandroid.data.room.AppDatabase;
import kg.askaromurkanov.appliancestoreandroid.databinding.ItemProductBinding;


public class HitAdapter extends RecyclerView.Adapter<HitAdapter.ViewHolder> {
    private List<Product> products = new ArrayList<>();
    private ProductDao productDao;
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

        appDatabase = App.getAppDatabase(parent.getContext());
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
        String price = "$"+String.valueOf(priceWithDiscount);
        holder.binding.productPrice.setText(price);
        holder.binding.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(holder.binding.getRoot().getContext()).create();
                alertDialog.setTitle("Вы хотите добавить "+product.getName() + " " + product.getFactory()+" в корзину");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "принять",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "отклонить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
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
}
