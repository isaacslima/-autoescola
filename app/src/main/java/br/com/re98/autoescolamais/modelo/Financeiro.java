package br.com.re98.autoescolamais.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by isaac on 1/7/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Financeiro implements Serializable{

    private String numero;
    private String id;
    private String descricao;
    private String valor;
    private String vencimento;
    private String pagto;
    private String restante;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getPagto() {
        return pagto;
    }

    public void setPagto(String pagto) {
        this.pagto = pagto;
    }

    public String getRestante() {
        return restante;
    }

    public void setRestante(String restante) {
        this.restante = restante;
    }

    @Override
    public String toString() {
        return getId() + " - " + getDescricao() + " - " + getVencimento();
    }
}
