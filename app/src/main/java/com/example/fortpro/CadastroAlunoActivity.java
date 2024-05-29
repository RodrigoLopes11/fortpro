package com.example.fortpro;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fortpro.api.ViaCepAPI;
import com.example.fortpro.model.Aluno;
import com.example.fortpro.model.Endereco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroAlunoActivity extends AppCompatActivity {

    private EditText etRA, etNome, etCEP, etLogradouro, etComplemento, etBairro, etCidade, etUF;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        etRA = findViewById(R.id.etRA);
        etNome = findViewById(R.id.etNome);
        etCEP = findViewById(R.id.etCEP);
        etLogradouro = findViewById(R.id.etLogradouro);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etCidade = findViewById(R.id.etCidade);
        etUF = findViewById(R.id.etUF);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        etCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 8) {
                    buscarEndereco(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnCadastrar.setOnClickListener(v -> cadastrarAluno());
    }

    private void buscarEndereco(String cep) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ViaCepAPI api = retrofit.create(ViaCepAPI.class);
        Call<Endereco> call = api.buscarEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    if (endereco != null) {
                        etLogradouro.setText(endereco.getLogradouro());
                        etComplemento.setText(endereco.getComplemento());
                        etBairro.setText(endereco.getBairro());
                        etCidade.setText(endereco.getLocalidade());
                        etUF.setText(endereco.getUf());
                    }
                } else {
                    Toast.makeText(CadastroAlunoActivity.this, "Erro ao buscar o endereço", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(CadastroAlunoActivity.this, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cadastrarAluno() {
        int ra = Integer.parseInt(etRA.getText().toString());
        String nome = etNome.getText().toString();
        String cep = etCEP.getText().toString();
        String logradouro = etLogradouro.getText().toString();
        String complemento = etComplemento.getText().toString();
        String bairro = etBairro.getText().toString();
        String cidade = etCidade.getText().toString();
        String uf = etUF.getText().toString();

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);

        Toast.makeText(this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
