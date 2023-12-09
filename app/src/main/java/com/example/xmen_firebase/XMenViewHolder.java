package com.example.xmen_firebase;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xmen_firebase.databinding.XMenBinding;
import com.squareup.picasso.Picasso;


public class XMenViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    private final XMenBinding ui;
    public static final int MENU_EDIT = 1;
    public static final int MENU_DELETE = 2;

    private XMenAdapter adapter;


    public XMenViewHolder(@NonNull XMenBinding ui , XMenAdapter adapter) {
        super(ui.getRoot());
        this.ui = ui;
        this.adapter = adapter;
        itemView.setOnClickListener(this::onClick);
        itemView.setOnCreateContextMenuListener(this);
    }

    private void onClick(View v) {
        int filteredPosition = adapter.getOriginalPosition(getAdapterPosition());
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(filteredPosition);
        }
        onEyeIconClick(filteredPosition);
    }

    public void setXMen(XMen xmen) {
        ui.nom.setText(xmen.getNom());
        // Load image using Picasso
        Picasso.get().load(xmen.getIdImage()).into(ui.image);
    }

    private XMenAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(XMenAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void onEyeIconClick(int position) {
        // Handle the eye icon click here, e.g., launch ViewActivity
        Intent intent = new Intent(itemView.getContext(), ViewActivity.class);
        intent.putExtra(ViewActivity.EXTRA_POSITION, position);
        itemView.getContext().startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // position de l'élément concerné*
        int position = getAdapterPosition();
        // titre du menu = nom du X-Men
        menu.setHeaderTitle(ui.nom.getText());
        // utiliser l'ordre pour stocker la position destinée à l'activité
        menu.add(0, MENU_EDIT, position, "Edit");
        menu.add(0, MENU_DELETE, position, "Delete");
    }
}


