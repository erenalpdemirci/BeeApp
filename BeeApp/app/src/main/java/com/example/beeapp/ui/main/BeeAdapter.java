package com.example.beeapp.ui.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beeapp.data.model.Bee;
import com.example.beeapp.databinding.ItemBeeBinding;

public class BeeAdapter extends ListAdapter<Bee, BeeAdapter.BeeViewHolder> {
    private static final String TAG = "BeeAdapter";
    private final OnBeeClickListener listener;

    public interface OnBeeClickListener {
        void onBeeClick(Bee bee);
    }

    public BeeAdapter(OnBeeClickListener listener) {
        super(new BeeDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public BeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            ItemBeeBinding binding = ItemBeeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
            return new BeeViewHolder(binding);
        } catch (Exception e) {
            Log.e(TAG, "onCreateViewHolder hatası: ", e);
            throw e;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BeeViewHolder holder, int position) {
        try {
            Bee bee = getItem(position);
            Log.d(TAG, "Arı bağlanıyor: " + bee.getName() + ", ID: " + bee.getId());
            holder.bind(bee);
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder hatası: ", e);
        }
    }

    class BeeViewHolder extends RecyclerView.ViewHolder {
        private final ItemBeeBinding binding;

        public BeeViewHolder(@NonNull ItemBeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                try {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Bee bee = getItem(position);
                        Log.d(TAG, "Arıya tıklandı: " + bee.getName() + ", ID: " + bee.getId());
                        listener.onBeeClick(bee);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Tıklama hatası: ", e);
                }
            });
        }

        public void bind(Bee bee) {
            try {
                binding.nameTextView.setText(bee.getName());
                binding.typeTextView.setText(bee.getType());
                binding.jobTextView.setText(bee.getJob());
            } catch (Exception e) {
                Log.e(TAG, "bind hatası: ", e);
            }
        }
    }

    static class BeeDiffCallback extends DiffUtil.ItemCallback<Bee> {
        @Override
        public boolean areItemsTheSame(@NonNull Bee oldItem, @NonNull Bee newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Bee oldItem, @NonNull Bee newItem) {
            return oldItem.equals(newItem);
        }
    }
}