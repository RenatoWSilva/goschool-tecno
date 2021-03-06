package br.com.goschool.goschool_mobile.activity.motorista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.goschool.goschool_mobile.Adapters.MotoristaAdapter;
import br.com.goschool.goschool_mobile.Aplication.IMostoristaREST;
import br.com.goschool.goschool_mobile.Modulos.Motorista;
import br.com.goschool.goschool_mobile.R;
import br.com.goschool.goschool_mobile.activity.MenuLateralActivity;
import br.com.goschool.goschool_mobile.activity.estudante.EstudanteActivity;
import br.com.goschool.goschool_mobile.activity.maps.MapsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotoristaActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MotoristaActivity.this, MotoristaAddActivity.class);
                startActivity(intent);
            }
        });

    }




    @Override
    protected void onStart() {
        super.onStart();
        final ListView lista = (ListView) findViewById(R.id.lvMotorista);
        IMostoristaREST iMostoristaREST = IMostoristaREST.retrofit.create(IMostoristaREST.class);
        dialog = new ProgressDialog(MotoristaActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();
        final Call<List<Motorista>> call = iMostoristaREST.getMotorista();
        call.enqueue(new Callback<List<Motorista>>() {
            @Override
            public void onResponse(Call<List<Motorista>> call, Response<List<Motorista>> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                final List<Motorista> listaMotoristas = response.body();
                if (listaMotoristas != null) {
                    MotoristaAdapter adapter = new MotoristaAdapter(getBaseContext(), listaMotoristas);
                    lista.setAdapter(adapter);
                    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(MotoristaActivity.this, MotoristaEditActivity.class);
                            intent.putExtra("ID", listaMotoristas.get(i).getId());
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Motorista>> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "Problema de acesso", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id == R.id.cadastro_motorista) {
            Intent intent = new Intent(this, MotoristaActivity.class);
            startActivity(intent);

        } else if (id == R.id.cadastro_estudante) {
            Intent intent = new Intent(this, EstudanteActivity.class);
            startActivity(intent);

        } else if (id == R.id.localizacao) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);

        } else if (id == R.id.menu) {
           Intent intent = new Intent(this, MenuLateralActivity.class);
           startActivity(intent);

       }

        return super.onOptionsItemSelected(item);
    }



}
