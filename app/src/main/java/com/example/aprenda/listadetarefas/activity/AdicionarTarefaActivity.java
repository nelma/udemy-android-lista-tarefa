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
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);


        //recuperar caso seja edicao
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //configurar caixa de texto
        if(tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

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

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if(tarefaAtual != null) {

                    if(!editTarefa.getText().toString().isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setId(tarefaAtual.getId());
                        tarefa.setNomeTarefa(editTarefa.getText().toString());


                        //atualizar dado no bco
                        if(tarefaDAO.atualizar(tarefa)) {
                            //fechar a Activity
                            finish();
                            Toast.makeText(
                                    AdicionarTarefaActivity.this,
                                    "Sucesso ao editar tarefa", Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            Toast.makeText(
                                    AdicionarTarefaActivity.this,
                                    "Erro ao editar tarefa", Toast.LENGTH_SHORT
                            ).show();
                        }
                    }


                } else {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(editTarefa.getText().toString());

                    if(!editTarefa.getText().toString().isEmpty()) {

                        //Executa acao ao clicar em salvar
                        if(tarefaDAO.salvar(tarefa)) {
                            //fechar a Activity
                            finish();
                            Toast.makeText(
                                    AdicionarTarefaActivity.this,
                                    "Sucesso ao salvar tarefa", Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            Toast.makeText(
                                    AdicionarTarefaActivity.this,
                                    "Erro ao salvar tarefa", Toast.LENGTH_SHORT
                            ).show();
                        }

                    } else {
                        Toast.makeText(
                                AdicionarTarefaActivity.this,
                                "Texto tarefa obrigatório", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
