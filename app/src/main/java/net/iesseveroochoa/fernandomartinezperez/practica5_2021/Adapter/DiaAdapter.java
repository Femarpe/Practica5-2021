package net.iesseveroochoa.fernandomartinezperez.practica5_2021.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import net.iesseveroochoa.fernandomartinezperez.practica5_2021.R;
import net.iesseveroochoa.fernandomartinezperez.practica5_2021.model.DiaDiario;

import java.util.List;

public class DiaAdapter extends RecyclerView.Adapter<DiaAdapter.DiaViewHolder> {
    private List<DiaDiario> listaDias;
    private OnItemClickBorrarListener listenerBorrar;
    private OnItemClickEditarListener listenerEditar;

    public void setListaDias(List<DiaDiario> dias) {
        listaDias = dias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dia, parent, false);
        return new DiaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaViewHolder holder, int position) {
        if (listaDias != null) {
            final DiaDiario dia = listaDias.get(position);

            holder.tvResumen.setText(dia.getId() + "-" + dia.getResumen());
            holder.tvTecnico.setText(dia.getId() + "-" + dia.getFechaFormatoLocal());


        }
    }

    @Override
    public int getItemCount() {
        if (listaDias != null)
            return listaDias.size();
        else return 0;
    }

    public class DiaViewHolder extends RecyclerView.ViewHolder {

        private TextView tvResumen;
        private TextView tvTecnico;
        private ImageView ivEstado;
        private ImageView ivEditar;
        private ImageView ivBorrar;
        private ConstraintLayout clItem;

        private DiaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResumen = itemView.findViewById(R.id.tvResumen);
            tvTecnico = itemView.findViewById(R.id.tvTecnico);
            ivEstado = itemView.findViewById(R.id.ivEstado);


            ivEditar = itemView.findViewById(R.id.ivEditar);
            ivEditar.setOnClickListener(v -> listenerEditar.onItemClickEditar(
                    listaDias.get(DiaViewHolder.this.getAbsoluteAdapterPosition())));


            ivBorrar = itemView.findViewById(R.id.ivBorrar);
            ivBorrar.setOnClickListener(v -> listenerBorrar.onItemClickBorrar(
                    listaDias.get(DiaViewHolder.this.getAbsoluteAdapterPosition())));

            clItem = itemView.findViewById(R.id.clItem);

        }
    }

    public interface OnItemClickBorrarListener {
        void onItemClickBorrar(DiaDiario dia);

    }

    public interface OnItemClickEditarListener {
        void onItemClickEditar(DiaDiario dia);
    }

    public void setOnItemClickBorrarListener(OnItemClickBorrarListener listener) {
        this.listenerBorrar = listener;

    }

    public void setOnItemClickEditarListener(OnItemClickEditarListener listener) {
        this.listenerEditar = listener;

    }

}
