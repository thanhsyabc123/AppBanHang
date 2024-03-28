package com.example.doanmobile.hoadonnguoiban;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.Customer_Model;
import com.example.doanmobile.R;
import com.example.doanmobile.hoadon.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.List;

public class shophienthiAdapter extends RecyclerView.Adapter<shophienthiAdapter.ViewHolder> {

    private Context context;

    private List<Order> orderList;

    public shophienthiAdapter(Context context, List<Order> hoadonkhdetailList) {
        this.context = context;
        this.orderList = hoadonkhdetailList;
    }

    @NonNull
    @Override
    public shophienthiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(order.getOrderDate());
        holder.ngaynguoimuaorder.setText(formattedDate);
        holder.hinhthucnguoimuaorder.setText(order.getHtThanhToan());
        holder.diachinguoimuaorder.setText(order.getDiaChi());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int userID = order.getUserID();
        CollectionReference shopCollectionRef1 = db.collection("KhachHang");
        Query shopQuery1 = shopCollectionRef1.whereEqualTo("userID", userID);
        shopQuery1.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Customer_Model customerModel = document.toObject(Customer_Model.class);
                    holder. tennguoimuaorder.setText(customerModel.getTenDayDu());
                }
            } else {
                // Xử lý trường hợp không thành công
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tennguoimuaorder, ngaynguoimuaorder, hinhthucnguoimuaorder, diachinguoimuaorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tennguoimuaorder = itemView.findViewById(R.id.tennguoimuaorder);
            ngaynguoimuaorder = itemView.findViewById(R.id.ngaynguoimuaorder);
            hinhthucnguoimuaorder = itemView.findViewById(R.id.hinhthucnguoimuaorder);
            diachinguoimuaorder = itemView.findViewById(R.id.diachinguoimuaorder);
        }
    }
}
