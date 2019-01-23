package me.ghiasi.soft;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder>
        implements Filterable  {
    private Context context;
    private List<Service> serviceList;
    private List<Service> servicListFiltered;
    private ServiceAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView service_name;
        public ImageView service_image;

        public MyViewHolder(View view) {
            super(view);
            service_name = (TextView) view.findViewById(R.id.service_name);
            service_image = (ImageView) view.findViewById(R.id.service_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onServiceSelected(servicListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    public ServiceAdapter(Context context, List<Service> serviceList, ServiceAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.serviceList = serviceList;
        this.servicListFiltered = serviceList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Service service = servicListFiltered.get(position);
        holder.service_name.setText(service.getTitle());

        Glide.with(context)
                .load(service.getPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.service_image);
    }

    @Override
    public int getItemCount() {
        return servicListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    servicListFiltered = serviceList;
                } else {
                    List<Service> filteredList = new ArrayList<>();
                    for (Service row : serviceList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    servicListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = servicListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                servicListFiltered = (ArrayList<Service>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ServiceAdapterListener {
        void onServiceSelected(Service service);
    }

}
