package com.example.aprenda.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aprenda.listadetarefas.R;
import com.example.aprenda.listadetarefas.helpers.TarefaDAO;
import com.example.aprenda.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar:

                Tarefa tarefa = new Tarefa();
                tarefa.setNomeTarefa(editTarefa.getText().toString());

                //Executa acao ao clicar em salvar
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                tarefaDAO.salvar(tarefa);

//                Toast.makeText(
//                        AdicionarTarefaActivity.this,
//                        "Botão salvar pressionado", Toast.LENGTH_SHORT
//                ).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
