package com.example.sonu.jaquar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.provider.SyncStateContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonu.jaquar.Adapters.NewlyProductAdapter;
import com.example.sonu.jaquar.Adapters.RecyclerAdapter;
import com.example.sonu.jaquar.Constants.CheckInternetCoonnection;
import com.example.sonu.jaquar.Constants.SearchConstants;
import com.example.sonu.jaquar.Models.CategoriesModel;
import com.example.sonu.jaquar.Models.GetValue;
import com.example.sonu.jaquar.Receivers.InternetReceiver;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowByCategoty extends AppCompatActivity  {
    private static final String TAG ="ShowByCategory" ;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
  ProgressDialog progressDialog;
    Toolbar toolbar;
    private AlertDialog.Builder mbuilder;
    private InternetReceiver internetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_by_categoty);
        Log.d(TAG, "onCreate: ");
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        internetReceiver = new InternetReceiver();
        registerReceiver(internetReceiver, intentFilter);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        toolbar =findViewById(R.id.toolbarCategory);
        toolbar.setTitle("Shop By Category");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobarCOlor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(ShowByCategoty.this,
                DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(ShowByCategoty.this,
                DividerItemDecoration.VERTICAL));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog =new ProgressDialog(ShowByCategoty.this);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ArrayList<CategoriesModel> arrayList =new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("categoreis");
        FirebaseRecyclerAdapter<CategoriesModel, RecyclerAdapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<CategoriesModel, RecyclerAdapter>(
                        CategoriesModel.class,
                        R.layout.categotylayout,
                        RecyclerAdapter.class,
                        databaseReference

                ) {
                    @Override
                    protected void populateViewHolder(RecyclerAdapter viewHolder, CategoriesModel model, int position) {
                        viewHolder.SetDetails(getApplicationContext(), model.getName(), model.getImage());
                        progressDialog.dismiss();
                    }

                    @Override
                    public RecyclerAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
                        RecyclerAdapter viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new NewlyProductAdapter.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView textView = view.findViewById(R.id.firebasetext);
                               String s  =textView.getText().toString();
                                SearchConstants.CATEGOTY_NAME=s;
                                Intent intent  =new Intent(ShowByCategoty.this,SubCategory.class);
                                intent.putExtra("title",s);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(),"item  at"+s,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        return  viewHolder;
                    }

                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        if (internetReceiver != null) {
            unregisterReceiver(internetReceiver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchandlogout,menu);

        MenuItem menuItem =menu.findItem(R.id.BothSearch);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("onqueryTextSubmit",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("categoreis");
                Query query =databaseReference.orderByChild("name").startAt(newText).endAt(newText+"\uf8ff");
                FirebaseRecyclerAdapter<CategoriesModel, RecyclerAdapter> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<CategoriesModel, RecyclerAdapter>(
                                CategoriesModel.class,
                                R.layout.categotylayout,
                                RecyclerAdapter.class,
                                query

                        ) {
                            @Override
                            protected void populateViewHolder(RecyclerAdapter viewHolder, CategoriesModel model, int position) {
                                if(model.getName().isEmpty())
                                {
                                    Log.d("MOdelValue","yes");
                                }
                                viewHolder.SetDetails(getApplicationContext(), model.getName(), model.getImage());
                            }
                            @Override
                            public RecyclerAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
                                RecyclerAdapter viewHolder = super.onCreateViewHolder(parent, viewType);
                                viewHolder.setOnClickListener(new NewlyProductAdapter.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        TextView textView = view.findViewById(R.id.firebasetext);
                                        String s  =textView.getText().toString();
                                        SearchConstants.CATEGOTY_NAME=s;
                                        Intent intent  =new Intent(ShowByCategoty.this,SubCategory.class);
                                        intent.putExtra("title",s);
                                        startActivity(intent);
                                        //Toast.makeText(getApplicationContext(),"item  at"+s,Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {

                                    }
                                });
                                return  viewHolder;
                            }

                        };
                recyclerView.setAdapter(firebaseRecyclerAdapter);


                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.BothLogout) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(ShowByCategoty.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            Log.d("logout", "yes");
        }
        return super.onOptionsItemSelected(item);
    }

}
