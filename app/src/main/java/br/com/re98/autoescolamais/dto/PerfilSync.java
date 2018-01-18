package br.com.re98.autoescolamais.dto;

import java.util.List;

import br.com.re98.autoescolamais.modelo.Perfil;



public class PerfilSync {

    private List<Perfil> perfil;
    private String momentoDaUltimaModificacao;

    public List<Perfil> getPerfil() {
        return perfil;
    }


    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
