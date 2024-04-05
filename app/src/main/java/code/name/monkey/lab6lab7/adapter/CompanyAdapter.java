package code.name.monkey.lab6lab7.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.name.monkey.lab6lab7.R;
import code.name.monkey.lab6lab7.model.Company;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private List<Company> companyList;
    private OnDeleteButtonClickListener deleteButtonClickListener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }

    public CompanyAdapter(List<Company> companyList, OnDeleteButtonClickListener listener) {
        this.companyList = companyList;
        this.deleteButtonClickListener = listener;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companyList.get(position);
        holder.textCompanyName.setText(company.getName());
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.onDeleteButtonClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        TextView textCompanyName;
        Button btnDelete;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            textCompanyName = itemView.findViewById(R.id.textCompanyName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public Company getItem(int position) {
        if (position < 0 || position >= companyList.size()) {
            return null;
        }
        return companyList.get(position);
    }
}
