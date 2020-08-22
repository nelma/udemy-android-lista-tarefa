package com.example.aprenda.listadetarefas.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import com.example.aprenda.listadetarefas.R;
import com.example.aprenda.listadetarefas.adapter.TarefaAdapter;
import com.example.aprenda.listadetarefas.helpers.DbHelper;
import com.example.aprenda.listadetarefas.helpers.RecyclerItemClickListener;
import com.example.aprenda.listadetarefas.helpers.TarefaDAO;
import com.example.aprenda.listadetarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefa = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurar recycler
        recyclerView = findViewById(R.id.recyclerView);

        //definindo os itens
        ContentValues cv = new ContentValues();
        cv.put("nome", "Teste");

        DbHelper db = new DbHelper(getApplicationContext());
        db.getWritableDatabase().insert(DbHelper.TABELA_TAREFAS, null, cv );


        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.i("clique", "onItemClick");
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Log.i("clique", "onLongItemClik");
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaTarefas() {

        //Tarefa estaticas
//        Tarefa t1 = new Tarefa();
//        t1.setNomeTarefa("Ir ao mercado");
//        listaTarefa.add(t1);
//
//        Tarefa t2 = new Tarefa();
//        t2.setNomeTarefa("Ir a feira");
//        listaTarefa.add(t2);


        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefa = tarefaDAO.listar();


        //configurar adapter
        tarefaAdapter = new TarefaAdapter(listaTarefa);


        //Configurar Recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);
    }

    @Override
    protected void onStart() {

        //Add no onStart para recarregar lista qdo voltar da tela de cadastro de tarefa
        carregarListaTarefas();

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
