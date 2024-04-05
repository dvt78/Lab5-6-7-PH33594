package code.name.monkey.lab6lab7.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import code.name.monkey.lab6lab7.R;
import code.name.monkey.lab6lab7.model.Fruit;

public class FruitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Fruit> fruitList;

    public FruitAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, parent, false);
        return new FruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FruitViewHolder) {
            Fruit fruit = fruitList.get(position);
            FruitViewHolder viewHolder = (FruitViewHolder) holder;
            viewHolder.bind(fruit);
        }
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    public void updateData(List<Fruit> newFruitList) {
        fruitList.addAll(newFruitList);
        notifyDataSetChanged();
    }

    public class FruitViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView quantityTextView;
        private TextView priceTextView;
        private ImageView image;

        public FruitViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            image = itemView.findViewById(R.id.image);

        }

        public void bind(Fruit fruit) {
            nameTextView.setText(fruit.getName());
            String[] companyNames = nameTextView.getContext().getResources().getStringArray(R.array.company_names);
            quantityTextView.setText(companyNames[fruit.getIdCompany() +1]);
            priceTextView.setText(String.valueOf(fruit.getPrice()));
            Glide.with(image)
                    .load("http://192.168.28.139:3000/uploads/" + fruit.getImages().get(0))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(image);
        }
    }
}
