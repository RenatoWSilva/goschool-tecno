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

public class MotoristaEditActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista_edit);

        final EditText nome = (EditText) findViewById(R.id.edNome);
        final EditText cnh = (EditText) findViewById(R.id.edcnh);
        final EditText cpf = (EditText) findViewById(R.id.edcpf);
        final EditText rg = (EditText) findViewById(R.id.edrgmot);
        final EditText nasc = (EditText) findViewById(R.id.ednascmot);
        final EditText sexo = (EditText) findViewById(R.id.edsexomot);

        Intent intent = getIntent();
        final String id = Integer.toString(intent.getIntExtra("ID", 0));

        final IMostoristaREST iMostoristaREST = IMostoristaREST.retrofit.create(IMostoristaREST.class);
        final Call<Motorista> call = iMostoristaREST.getMotoristaPorId(id);
        dialog = new ProgressDialog(MotoristaEditActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();
        call.enqueue(new Callback<Motorista>() {
            @Override
            public void onResponse(Call<Motorista> call, Response<Motorista> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Motorista motorista = response.body();
                nome.setText(motorista.getMotororistaNome());
                cnh.setText(motorista.getMotororistaCNH());
                cpf.setText(String.valueOf(motorista.getMotororistaCPF()));
                nasc.setText(motorista.getMotoristaNasc());
                sexo.setText(motorista.getMotoristaSexo());
                rg.setText(String.valueOf(motorista.getMotoristaRg()));
            }

            @Override
            public void onFailure(Call<Motorista> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();

            }
        });

        Button alterar = (Button) findViewById(R.id.btnEditMotorista);
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(MotoristaEditActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();                Motorista motorista = new Motorista();
                motorista.setId(Integer.parseInt(id));
                motorista.setMotororistaNome(nome.getText().toString());
                motorista.setMotororistaCNH(cnh.getText().toString());
                motorista.setMotororistaCPF(Integer.parseInt(cpf.getText().toString()));
                motorista.setMotoristaNasc(nasc.getText().toString());
                motorista.setMotoristaSexo(sexo.getText().toString());
                motorista.setMotoristaRg(Integer.parseInt(rg.getText().toString()));
                Call<Void> call = iMostoristaREST.alteraMotorista(id, motorista);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Motorista alterado com sucesso", Toast.LENGTH_SHORT).show();
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
        Button remover = (Button) findViewById(R.id.btnDeleteMotorista);
        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(MotoristaEditActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();
                Call<Void> call = iMostoristaREST.removeMotorista(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Motorista removido com sucesso", Toast.LENGTH_SHORT).show();
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
