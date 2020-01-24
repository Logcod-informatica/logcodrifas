package com.logcod.rifas.logcodrifas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.logcod.rifas.negocio.Rifa;

import java.util.ArrayList;

public class ListaViewCompradores extends AppCompatActivity {

    private ArrayList<Rifa> lista = new ArrayList<>();
    private ArrayAdapter<Rifa> listaAdpter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private EditText textBusca;
    private ListView listaView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_view_compradores);
        inicializarFirebase();
        eventoBusca();


    }

    private void eventoBusca() {
        textBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             String palavra = textBusca.getText().toString().trim();
             pesquisar(palavra);
            }
        });
    }

    void pesquisar(String palavra){
        Query query;
        if (palavra.equals("")){
            query = reference.child("endereco")
                            .orderByChild("nome");
        }else{
            query = reference.child("endereco")
                             .orderByChild("nome").startAt(palavra).endAt(palavra+"\uf8ff");
        }
        lista.clear();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot obj: dataSnapshot.getChildren()){
                    Rifa r = obj.getValue(Rifa.class);
                    lista.add(r);

                }
                listaAdpter = new ArrayAdapter<>(ListaViewCompradores.this,android.R.layout.activity_list_item,lista);
                listaView.setAdapter(listaAdpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        pesquisar("");
    }

    void inicializarFirebase(){
        FirebaseApp.initializeApp(ListaViewCompradores.this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }


}
