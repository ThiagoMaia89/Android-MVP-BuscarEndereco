package com.simplesoftware.buscarcep.presenter;

import android.content.Context;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simplesoftware.buscarcep.model.Endereco;

import java.util.ArrayList;

public interface MainContract {

    interface MvpView{

    }

    interface Presenter {

        void handleBtnBuscarOnClick(EditText et_cep, RecyclerView rv_lista_endereco, GridLayoutManager layoutManager, Context context);

        void handleBtnCopiarEnderecoOnClick(EditText et_cep, Context context);

    }

}
