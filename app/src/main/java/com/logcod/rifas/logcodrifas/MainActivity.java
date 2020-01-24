package com.logcod.rifas.logcodrifas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.logcod.rifas.negocio.Rifa;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private EditText nomeView, enderecoView, contatoView, viewEmail, valorPagarView;
    private Button btSalvar, btCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarFirebase();
        gerarEvento();

    }

    public Button gerarEvento() {
        btSalvar = (Button) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()) {
                    nomeView = (EditText) findViewById(R.id.viewNome);
                    enderecoView = (EditText) findViewById(R.id.viewEndereco);
                    contatoView = (EditText) findViewById(R.id.viewTelefone);
                    viewEmail = (EditText) findViewById(R.id.viewEmail);
                    valorPagarView = (EditText) findViewById(R.id.viewValorPagar);
                    Rifa rifa = new Rifa();
                    rifa.setContato(contatoView.getText().toString());
                    rifa.setEndereco(enderecoView.getText().toString());
                    rifa.setValorPagar(Double.valueOf(valorPagarView.getText().toString()));
                    rifa.setEmail(viewEmail.getText().toString());
                    rifa.setNome(nomeView.getText().toString());
                    rifa.setChave(UUID.randomUUID().toString());
                    rifa.setPago(false);

                    reference.child("endereco").child(rifa.getChave()).setValue(rifa);
                    Toast.makeText(MainActivity.this,"Sucessofull",Toast.LENGTH_LONG).show();

                    limparCampos();
                   // Intent intent = new Intent(MainActivity.this, ListaViewCompradores.class);
                   // startActivity(intent);

                } else {

                }

            }
        });
        return btSalvar;
    }

    ;

    public void limparCampos() {
        nomeView.setText("");
        nomeView.setText("");
        enderecoView.setText("");
        contatoView.setText("");
        viewEmail.setText("");
        valorPagarView.setText("");

    }

    public void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }
}
