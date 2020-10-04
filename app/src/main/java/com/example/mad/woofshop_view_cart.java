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

import com.example.mad.models.Cart;
import com.example.mad.viewholders.CartViewHolder;
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

public class woofshop_view_cart extends AppCompatActivity {

    Button calculatebill;
    TextView title;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String userID;
    float allTotal = 0, totalPtice=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofshop_view_cart);

        //initialize
        calculatebill = findViewById(R.id.calcutalebill);

        title = findViewById(R.id.titleCart);
        recyclerView = findViewById(R.id.cart_itemview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartRef.child("User").child(userID).child("ProductItem"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {

                cartViewHolder.txtCartItmName.setText("Product Name: "+cart.getItemName());
                cartViewHolder.txtCartItmPrice.setText("Price: Rs."+cart.getPrice());
                cartViewHolder.txtCartItmQty.setText("Quantity: "+cart.getQuantity());

                //calculate total price
                totalPtice = cart.getPrice() * cart.getQuantity();
                allTotal = allTotal + totalPtice;

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit Item",
                                        "Remove Item"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(woofshop_view_cart.this);
                        builder.setTitle("Cart Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //user click on the edit button
                                if(i == 0)
                                {
                                    Intent intent = new Intent(woofshop_view_cart.this, woofshop_update_cartitem.class);
                                    intent.putExtra("itmID", cart.getItemID());
                                    intent.putExtra("itmQty", String.valueOf(cart.getQuantity()));
                                    startActivity(intent);
                                }
                                //user click on the remove button
                                if(i == 1)
                                {
                                    //update quantity of item stock
                                    updateProductItemStock(cart.getItemID(), cart.getQuantity());

                                    cartRef.child("User").child(userID).child("ProductItem").child(cart.getItemID())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(), "item removed Successfully", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(woofshop_view_cart.this, woofshop_show_products.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }
                            }
                        });

                        //alert dialog finish - show dialog
                        builder.show();
                    }
                });

            }


            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_itemlayout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    @Override
    protected void onResume() {
        super.onResume();

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofShop);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottomNaviBar_woofCorner:
                        startActivity(new Intent(getApplicationContext(), woofcorner_show_ads.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofCare:
                        startActivity(new Intent(getApplicationContext(), woofcare_show_clinics.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofShop:
                        startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.bottomNaviBar_woofProfile:
                        startActivity(new Intent(getApplicationContext(), woofprofile_menu.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });
        //bottom navigation bar ends

        //if cart is empty
        DatabaseReference cartRefChk = FirebaseDatabase.getInstance().getReference().child("CartList").child("User").child(userID).child("ProductItem");

        cartRefChk.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    calculatebill.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(woofshop_view_cart.this, woofshop_viewBill.class);
                            intent.putExtra("totalPrice", String.valueOf(allTotal));
                            startActivity(intent);
                        }
                    });
                }
                else
                {
//                    calculatebill.setVisibility(View.INVISIBLE);
                        title.setText("Add items to cart.🐶🛒");
                        calculatebill.setText("Bask To WoofShop");
                        calculatebill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(woofshop_view_cart.this, woofshop_show_products.class);
                        startActivity(intent);

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateProductItemStock(final String itemID, final int qty)
    {
        final DatabaseReference dbUpdateQty = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        dbUpdateQty.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    int stock = Integer.parseInt(snapshot.child(itemID).child("qty").getValue().toString());
                    int qtyNew = stock + qty;
                    dbUpdateQty.child(itemID).child("qty").setValue(qtyNew);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}