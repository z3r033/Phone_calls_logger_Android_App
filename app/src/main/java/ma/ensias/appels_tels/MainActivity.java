package ma.ensias.appels_tels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 18;
    Boolean autorisation = false;
    Button btnappell,btnstats,btnstatsjour,btnstatssemaine;
   CardView idstatsmois,idstatssemaine,idstatsjour,idappel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckUserPermsions();
      Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Appel historique");

        btnappell = findViewById(R.id.btnappelle);
        btnappell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AppelActivity.class));
            }
        });
        idappel = findViewById(R.id.idappelhis);
        idappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AppelActivity.class));
            }
        });
        idstatsmois = findViewById(R.id.idstatsmois);
        idstatsmois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StatsActivity.class));
            }
        });

        idstatssemaine = findViewById(R.id.idstastsemaine);
        idstatssemaine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StatsWeek.class));
            }
        });

        idstatsjour = findViewById(R.id.idstastsjour);
        idstatsjour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StatsDayActivity.class));
            }
        });


        btnstats = findViewById(R.id.btnstast);
        btnstats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StatsActivity.class));
            }
        });
        btnstatsjour = findViewById(R.id.btnstastjour);
        btnstatsjour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StatsDayActivity.class));
            }
        });
        btnstatssemaine = findViewById(R.id.btnstastsemaine);
        btnstatssemaine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StatsWeek.class));
            }
        });


    }
    void CheckUserPermsions(){

        String[] autorisations={Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_CALL_LOG,  Manifest.permission.WRITE_CONTACTS
        ,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET};
        if ( Build.VERSION.SDK_INT >= 23){

            for(String autorisation :autorisations) {
                if (ActivityCompat.checkSelfPermission(this, autorisation) !=
                        PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                                    android.Manifest.permission.READ_CALL_LOG,
                                    Manifest.permission.READ_CONTACTS,
                                    Manifest.permission.CALL_PHONE,
                                    Manifest.permission.WRITE_CALL_LOG  ,
                            Manifest.permission.WRITE_CONTACTS ,
                                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                            REQUEST_CODE_ASK_PERMISSIONS
                           );
                    return;
                }
            }
        }
        autorisation=true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);

                    startActivity(i);
                    autorisation=true;
                } else {
                    Toast.makeText( this,  "Vous n'avez pas autorisé l'application à atteindre vos contacts" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}