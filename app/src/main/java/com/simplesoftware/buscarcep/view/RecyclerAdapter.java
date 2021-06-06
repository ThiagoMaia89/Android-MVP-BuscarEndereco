package com.simplesoftware.buscarcep.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simplesoftware.buscarcep.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<String> listaEndereco;
    private Context context;

    public RecyclerAdapter(ArrayList<String> listaEndereco){
        this.listaEndereco = listaEndereco;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String endereco = listaEndereco.get(position);
        holder.rv_itemText.setText(endereco);
        switch (position){
            case 0 : holder.rv_image.setImageResource(R.drawable.icon_cep);
            break;
            case 1 : holder.rv_image.setImageResource(R.drawable.icon_street);
            break;
            case 2: holder.rv_image.setImageResource(R.drawable.icon_bairro);
            break;
            case 3: holder.rv_image.setImageResource(R.drawable.icon_city);
            break;
            case 4: holder.rv_image.setImageResource(R.drawable.icon_estado);
            break;
            case 5: holder.rv_image.setImageResource(R.drawable.icon_ibge);
            break;
            case 6: holder.rv_image.setImageResource(R.drawable.icon_phone);
            break;
        }


    }

    @Override
    public int getItemCount() {
        return listaEndereco.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView rv_image;
        private TextView rv_itemText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rv_image = itemView.findViewById(R.id.rv_image);
            rv_itemText = itemView.findViewById(R.id.rv_itemText);
        }
    }

}
