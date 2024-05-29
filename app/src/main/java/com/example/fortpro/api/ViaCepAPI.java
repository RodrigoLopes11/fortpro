package com.example.fortpro.api;

import com.example.fortpro.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepAPI {
    @GET("{cep}/json/")
    Call<Endereco> buscarEndereco(@Path("cep") String cep);
}
