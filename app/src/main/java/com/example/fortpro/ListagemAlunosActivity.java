package com.example.fortpro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fortpro.adapter.AlunoAdapter;
import com.example.fortpro.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class ListagemAlunosActivity extends AppCompatActivity {

    private RecyclerView rvAlunos;
    private AlunoAdapter adapter;
    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_alunos);

        rvAlunos = findViewById(R.id.rvAlunos);
        alunos = new ArrayList<>();

        alunos.add(new Aluno(1, "João", "12345678", "Rua A", "Apt 1", "Centro", "Cidade A", "UF A"));
        alunos.add(new Aluno(2, "Maria", "87654321", "Rua B", "Casa 2", "Bairro B", "Cidade B", "UF B"));

        adapter = new AlunoAdapter(alunos, this);
        rvAlunos.setLayoutManager(new LinearLayoutManager(this));
        rvAlunos.setAdapter(adapter);
    }
}
