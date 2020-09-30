package com.example.mad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.models.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class woofadmin_orders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_orders);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.showAdminOrdersView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(dbRef, Order.class).build();

        FirebaseRecyclerAdapter<Order,AdminViewHolder> adapter = new FirebaseRecyclerAdapter<Order, AdminViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminViewHolder adminViewHolder, final int i, @NonNull final Order order) {

                String status = order.getStatus();
                adminViewHolder.receiver.setText("Customer(Received): "+order.getName());
                adminViewHolder.address.setText("Address : "+order.getAddress());
                adminViewHolder.phone.setText("Phone: "+order.getPhone());
                adminViewHolder.price.setText("Total Amount: "+order.getTotalAmount());
                adminViewHolder.postal.setText("Postal Code: "+order.getPostalCode());
                adminViewHolder.date.setText("Date: "+order.getDateOrdered());
                adminViewHolder.status.setText("Status: "+status);

                adminViewHolder.viewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(woofadmin_orders.this, woofadmin_ordersViewItems.class);
                        intent.putExtra("uID", order.getCurrentUserID());
                        startActivity(intent);
                    }
                });


                //update order status
                adminViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (order.getStatus().equals("Pending")) {

                            final String ordID = getRef(i).getKey();

                            CharSequence options[] = new CharSequence[]
                                    {
                                            "Confirm set Dilivered Order",
                                            "Cancel"
                                    };
                            AlertDialog.Builder builder = new AlertDialog.Builder(woofadmin_orders.this);
                            builder.setTitle("Order Options");

                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //admin click on the confirm
                                    if (i == 0) {
                                        final DatabaseReference dbUpdateRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(ordID);

                                        dbUpdateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                dbUpdateRef.child("status").setValue("Dilivered");

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                }
                            });

                            //alert dialog finish - show dialog
                            builder.show();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ordrer_layout,parent,false);

                return new AdminViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_admin_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_adminOrders);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.bottomNaviBar_adminOrganizations:
                        startActivity(new Intent(getApplicationContext(), woofadmin_organization_view.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminItems:
                        startActivity(new Intent(getApplicationContext(), woofadmin_addItem.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminOrders:
                        startActivity(new Intent(getApplicationContext(), woofadmin_orders.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminProfile:
                        startActivity(new Intent(getApplicationContext(), woofadmin_account.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });
        //bottom navigation bar ends
    }

    //Admin product View Holder class

    public static class AdminViewHolder extends RecyclerView.ViewHolder
    {
        TextView receiver, phone, address,postal, status,price, date,userCurrent;
        Button viewAllBtn;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            receiver = itemView.findViewById(R.id.Orders_receiverName);
            phone = itemView.findViewById(R.id.Orders_phone);
            postal = itemView.findViewById(R.id.Orders_postal);
            price = itemView.findViewById(R.id.Orders_price);
            address = itemView.findViewById(R.id.Orders_address);
            date = itemView.findViewById(R.id.Orders_date);
            status = itemView.findViewById(R.id.Order_status);
            viewAllBtn = itemView.findViewById(R.id.adminOdershowAll);

        }

    }

}