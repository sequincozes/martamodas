package com.devbe.martamodas.home.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.devbe.martamodas.R;
import com.devbe.martamodas.home.HomeContracts;
import com.devbe.martamodas.home.entity.ClientCard;
import com.devbe.martamodas.util.YLog;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdapterClients extends RecyclerView.Adapter<AdapterClients.MyViewHolder> {

    private MainActivity activity;
    private Context mContext;
    private List<ClientCard> itens;
    private AdapterClients adapter;
    private String username;

    public AdapterClients(MainActivity activity, Context mContext,
                          List<ClientCard> items, String username) {
        this.activity = activity;
        this.mContext = mContext;
        this.itens = items;
        this.username = username;
        this.adapter = this;
    }

    public AdapterClients(AdapterClients adapter, HomeContracts.Router router) {
        this.activity = adapter.activity;
        this.mContext = adapter.mContext;
        this.itens = adapter.getRegisterItems();
        this.username = adapter.username;
        this.adapter = adapter;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<ClientCard> getRegisterItems() {
        return itens;
    }

    public void setItens(List<ClientCard> itens) {
        this.itens = itens;
    }

    public void addItem(ClientCard icon) {
        itens.add(icon);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        YLog.d("AdapterClientRegister", "onBindViewHolder", "Entrou no metodo...");

        holder.cardView.setOnClickListener(null);
        final ClientCard itemDetail = itens.get(position);
//        holder.icon.setImageDrawable(itemDetail.getIcon());
        holder.company.setText(itemDetail.getCompany());
        holder.name.setText(itemDetail.getName());
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_client_register, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return getRegisterItems().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView company;
        public TextView name;
        public CardView cardView;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textViewName);
            company = view.findViewById(R.id.textViewCompany);
            icon = view.findViewById(R.id.photo);
            cardView = view.findViewById(R.id.cardViewClient);
        }
    }

    /*---------- Faz busca na lista ------- */
    public ArrayList<ClientCard> gridSearch(String text) {
        ArrayList<ClientCard> result = new ArrayList<>();
        for (int i = 0; i < itens.size(); i++) {
            if (unAccent(itens.get(i).getName().toLowerCase()).contains(unAccent(text.toLowerCase()))
            || unAccent(itens.get(i).getCompany().toLowerCase()).contains(unAccent(text.toLowerCase()))) {
                result.add(itens.get(i));
            }
        }
        setItens(result);
        return result;
    }

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
