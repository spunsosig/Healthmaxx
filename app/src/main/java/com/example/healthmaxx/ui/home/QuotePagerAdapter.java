package com.example.healthmaxx.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthmaxx.Models.Quote;
import com.example.healthmaxx.R;

import java.util.List;

public class QuotePagerAdapter extends RecyclerView.Adapter<QuotePagerAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textQuote;
        private TextView textAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textQuote = itemView.findViewById(R.id.quoteTextView);
            textAuthor = itemView.findViewById(R.id.authorTextView);
        }

        public void bind(Quote quote) {
            textQuote.setText(quote.getQuote());
            textAuthor.setText(quote.getAuthor());
        }
    }

    private List<Quote> quotes;

    public QuotePagerAdapter(List<Quote> quotes) {
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote, parent, false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quote quote = quotes.get(position);
        holder.bind(quote);
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

}
