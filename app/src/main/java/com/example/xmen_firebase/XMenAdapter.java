package com.example.xmen_firebase;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xmen_firebase.databinding.XMenBinding;

import java.util.ArrayList;

public class XMenAdapter extends  RecyclerView.Adapter<XMenViewHolder>{

    public ArrayList<XMen> liste;
    public ArrayList<XMen> filteredData;

    public XMenAdapter(ArrayList<XMen> liste) {
        this.liste = liste;
        this.filteredData = new ArrayList<>(liste);
    }

    public int getOriginalPosition(int filteredPosition) {
        if (filteredPosition >= 0 && filteredPosition < filteredData.size()) {
            XMen filteredXMen = filteredData.get(filteredPosition);
            return liste.indexOf(filteredXMen);
        } else {
            return -1; // Return -1 for invalid positions
        }
    }



    // Method to filter data based on search input and spinner selection
    public void filter(String query, String selectedFilter) {
        filteredData.clear();

        if (TextUtils.isEmpty(query)) {
            filteredData.addAll(liste);
        } else {
            query = query.toLowerCase().trim();
            for (XMen item : liste) {
                // Add your filtering logic based on the selected filter
                String filterValue = getFilterValue(item, selectedFilter);
                if (filterValue != null && filterValue.toLowerCase().contains(query)) {
                    filteredData.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }

    private String getFilterValue(XMen xMen, String selectedFilter) {
        switch (selectedFilter) {
            case "nom":
                return xMen.getNom();
            case "pouvoirs":
                return xMen.getPouvoirs();
            case "description":
                return xMen.getDescription();
            case "alias":
                return xMen.getAlias();
            default:
                return null;
        }
    }

    @NonNull
    @Override
    public XMenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        XMenBinding ui = XMenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new XMenViewHolder(ui,this);
    }

    @Override
    public void onBindViewHolder(@NonNull XMenViewHolder holder, int position) {
        holder.setXMen(filteredData.get(position)); // Use filteredData instead of liste
        holder.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return filteredData.size(); // Use filteredData instead of liste
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}


