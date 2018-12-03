package br.com.goschool.goschool_mobile.activity.motorista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.goschool.goschool_mobile.Aplication.IMostoristaREST;
import br.com.goschool.goschool_mobile.Modulos.Motorista;
import br.com.goschool.goschool_mobile.R;
import br.com.goschool.goschool_mobile.activity.MenuLateralActivity;
import br.com.goschool.goschool_mobile.activity.estudante.EstudanteActivity;
import br.com.goschool.goschool_mobile.activity.maps.MapsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotoristaAddActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista_add);

        final EditText nome = (EditText) findViewById(R.id.edNome);
        final EditText cnh = (EditText) findViewById(R.id.edcnh);
        final EditText cpf = (EditText) findViewById(R.id.edcpf);
        final EditText rg = (EditText) findViewById(R.id.edrgmot);
        final EditText nasc = (EditText) findViewById(R.id.ednascmot);
        final EditText sexo = (EditText) findViewById(R.id.edsexomot);
        Button adicionar = (Button) findViewById(R.id.btnAddMotorista);
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(MotoristaAddActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();
                Motorista motorista = new Motorista();
                motorista.setMotororistaNome(nome.getText().toString());
                motorista.setMotororistaCNH(cnh.getText().toString());
                motorista.setMotororistaCPF(Integer.parseInt(cpf.getText().toString()));
                motorista.setMotoristaSexo(sexo.getText().toString());
                motorista.setMotoristaNasc(nasc.getText().toString());
                motorista.setMotoristaRg(Integer.parseInt(rg.getText().toString()));
                IMostoristaREST iMostoristaREST = IMostoristaREST.retrofit.create(IMostoristaREST.class);
                final Call<Void> call = iMostoristaREST.insereMotorista(motorista);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Motorista inserido com sucesso", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                    }
                });
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
