package com.simplesoftware.buscarcep.util;

import com.simplesoftware.buscarcep.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceConfig {

    @GET("{cep}/json/")
    Call<Endereco> buscarEndereco(@Path("cep") String cep);
}
