package ma.ensias.appels_tels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import ma.ensias.appels_tels.Utils.ExporterWeb;
import ma.ensias.appels_tels.adapter.MyAdapter;
import ma.ensias.appels_tels.basedonnes.AppelHistory;
import ma.ensias.appels_tels.basedonnes.DataSource;
import ma.ensias.appels_tels.models.AppelModel;

public class AppelActivity extends AppCompatActivity {

    public ArrayList<AppelModel> mDataList= new ArrayList<AppelModel>();
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    DataSource dbGest;
    SwipeRefreshLayout refresh ;
    Button btnsortduree , btnsortdate;
    Button btnEntrantAppell,btnSortantAppell;
    Button btnCurrentMois;
    Button btnexport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appel);
        btnEntrantAppell = findViewById(R.id.btnappellentrant);
        btnSortantAppell = findViewById(R.id.btnappelsortant);
        btnCurrentMois = findViewById(R.id.btnappelledumoiscourant);
        refresh = findViewById(R.id.swiperefresh);
        dbGest = new DataSource(getApplicationContext());
        btnsortduree = findViewById(R.id.btntriduree);
        btnsortdate  = findViewById(R.id.btntridate);
        btnexport = findViewById(R.id.btnexporter);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
      //  recyclerView.setNestedScrollingEnabled(false);

        AppelHistory appellDetails= new AppelHistory(getApplicationContext(),dbGest);
        appellDetails.getAppelsInfo();
        mDataList = dbGest.getData();
        btnsortduree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList = dbGest.getDataSortByDuree();
                myAdapter = new MyAdapter(AppelActivity.this, mDataList,dbGest);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
            }
        });
        btnsortdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mDataList = dbGest.getData();

                myAdapter = new MyAdapter(AppelActivity.this, mDataList,dbGest);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
            }
        });
        btnEntrantAppell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList = dbGest.getAppelByType("INCOMING");
                myAdapter = new MyAdapter(AppelActivity.this, mDataList,dbGest);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
            }
        });
        btnSortantAppell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList = dbGest.getAppelByType("OUTGOING");
                myAdapter = new MyAdapter(AppelActivity.this, mDataList,dbGest);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
            }
        });
        btnCurrentMois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList = dbGest.getAppelByMounth();
                myAdapter = new MyAdapter(AppelActivity.this, mDataList,dbGest);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
            }
        });
        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList = dbGest.getData();
                myAdapter = new MyAdapter(AppelActivity.this, mDataList,dbGest);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
                refresh.setRefreshing(false);
            }
        });
        myAdapter = new MyAdapter(AppelActivity.this, mDataList,dbGest);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagert);
        TextInputEditText searchcontact = (TextInputEditText) findViewById(R.id.search);
        searchcontact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (AppelActivity.this).myAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnexport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (AppelModel appelmodel : mDataList) {
                    new ExporterWeb(AppelActivity.this, appelmodel).execute();
                }


            }
        });
    }












}