package kg.askaromurkanov.appliancestoreandroid.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import kg.askaromurkanov.appliancestoreandroid.databinding.ItemProductHorizontalBinding;


public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    private List<Product> products = new ArrayList<>();
    private AppDatabase appDatabase;
    private ProductDao productDao;

    public void setList(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductHorizontalBinding itemProductHorizontalBinding = ItemProductHorizontalBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        appDatabase = App.getAppDatabase(parent.getContext());
        productDao = appDatabase.productDao();

        ViewHolder viewHolder = new ViewHolder(itemProductHorizontalBinding);
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



        String discount = "-"+product.getDiscount()+"%";
        holder.binding.productDicount.setText(discount);
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
        private ItemProductHorizontalBinding binding;

        public ViewHolder(@NonNull ItemProductHorizontalBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

    }
}
