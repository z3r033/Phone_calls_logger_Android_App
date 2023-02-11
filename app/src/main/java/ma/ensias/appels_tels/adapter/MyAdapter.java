package ma.ensias.appels_tels.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import ma.ensias.appels_tels.AppelActivity;
import ma.ensias.appels_tels.R;
import ma.ensias.appels_tels.basedonnes.AppelHistory;
import ma.ensias.appels_tels.basedonnes.DataSource;
import ma.ensias.appels_tels.models.AppelModel;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {


    ArrayList<AppelModel> mDataList;
    LayoutInflater layoutInflater;
    Context context;
    private ValueFilter valueFilter;
    private ArrayList<AppelModel> mStringFilterList;
    DataSource dbGest;

    public MyAdapter (Context context, ArrayList<AppelModel> data,DataSource db) {

        layoutInflater=LayoutInflater.from(context);
        this.mDataList=data;
        this.mStringFilterList=data;
        this.context=context;
        this.dbGest=db;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=layoutInflater.inflate(R.layout.listitem,parent,false);

        MyViewHolder holder= new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final AppelModel appelmodel=mDataList.get(position);
        holder.setData (appelmodel,position);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
           //
                alertDialog.setTitle("dialog de suppression");
                alertDialog.setIcon(R.drawable.icred_baseline_delete_forever_24);
                alertDialog.setMessage("tu est sure que vous voulez supprimer cette appelle ? ");
                alertDialog.setPositiveButton("Anuller", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AppelHistory appellDetails= new AppelHistory(context,dbGest);
                        appellDetails.deleteAppelleNumber(appelmodel.getIdlog(),appelmodel.getId());
                        notifyDataSetChanged();

                    }
                });

                AlertDialog dialog = alertDialog.create();
               // dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomtxt,numerotxt,datetxt,timetxt,durationtxt,locationtxt;
        ImageView imagetype,imageuser;
        Button btndelete;
        CardView rootCardView;



        public MyViewHolder(View itemView) {
            super(itemView);



            nomtxt=(TextView)itemView.findViewById(R.id.nomtxt);
            numerotxt=(TextView)itemView.findViewById(R.id.numerotxt);
            datetxt=(TextView)itemView.findViewById(R.id.datetxt);
            timetxt=(TextView)itemView.findViewById(R.id.timetxt);
            durationtxt=(TextView)itemView.findViewById(R.id.dureetxt);
            locationtxt=(TextView)itemView.findViewById(R.id.txtLocation);

            imagetype=(ImageView) itemView.findViewById(R.id.imagetype);
            imageuser=(ImageView) itemView.findViewById(R.id.nomimage);
            rootCardView=(CardView) itemView.findViewById(R.id.rootCardView);
            btndelete = (Button) itemView.findViewById(R.id.btndelete);




        }


        public void setData(AppelModel appelemodel, int position) {

            if(appelemodel.getNom()==null)

            {
                appelemodel.setNom("Unknown");

            }


            this.nomtxt.setText(appelemodel.getNom());
            this.numerotxt.setText(appelemodel.getNumero());
            this.datetxt.setText(appelemodel.getDate());
            this.timetxt.setText(appelemodel.getTime());
            this.locationtxt.setText(appelemodel.getLocation());

            int seconde=appelemodel.getDuration();

            int secondes=seconde%60;
            int minute=seconde/60;
            int heure=minute/60;

            if(heure!=0)
            {
                this.durationtxt.setText(heure+"h "+" "+minute+"m "+" "+secondes+"s");
            }
            else if(minute!=0)
            {
                this.durationtxt.setText(minute+"m "+" "+secondes+"s");
            }else{
                this.durationtxt.setText(secondes+"s");
            }




            if(appelemodel.getType().equals("MISSED"))

            {
                this.imagetype.setImageResource(R.drawable.ic_baseline_phone_missed_24);

            }

            else if(appelemodel.getType().equals("OUTGOING"))

            {

                this.imagetype.setImageResource(R.drawable.ic_baseline_call_made_24);


            }

            else if (appelemodel.getType().equals("INCOMING"))
            {
                this.imagetype.setImageResource(R.drawable.ic_baseline_phone_callback_24);


            }

            else

            {
                this.imagetype.setImageResource(R.drawable.ic_baseline_not_interested_24);


            }


        }
    }



    @Override
    public Filter getFilter() {
        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<AppelModel> filterList = new ArrayList<AppelModel>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getNumero().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        AppelModel appel = new AppelModel();
                        appel.setNom(mStringFilterList.get(i).getNom());
                        appel.setId(mStringFilterList.get(i).getId());
                        appel.setIdlog(mStringFilterList.get(i).getIdlog());
                        appel.setNumero(mStringFilterList.get(i).getNumero());
                        appel.setDuration(mStringFilterList.get(i).getDuration());
                        appel.setType(mStringFilterList.get(i).getType());
                        appel.setDate(mStringFilterList.get(i).getDate());
                        appel.setTime(mStringFilterList.get(i).getTime());
                        appel.setTemps_appelle(mStringFilterList.get(i).getTemps_appelle());
                        appel.setLocation(mStringFilterList.get(i).getLocation());
                        filterList.add(appel);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }


        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mDataList = (ArrayList<AppelModel>) results.values;
            notifyDataSetChanged();
        }

    }



}