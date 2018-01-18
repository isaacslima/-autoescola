package br.com.re98.autoescolamais.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Perfil implements Serializable{


    private String codProcesso;
    private String validadeProcesso;
    private String nome;
    private String rg;
    private String cpf;
    private String dataAprovacaoExameMedico;
    private String dataAprovacaoExameTeorico;
    private String dataAprovacaoMoto;
    private String dataAprovacaoCarro;

    public String getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(String codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getValidadeProcesso() {
        return validadeProcesso;
    }

    public void setValidadeProcesso(String validadeProcesso) {
        this.validadeProcesso = validadeProcesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataAprovacaoExameMedico() {
        return dataAprovacaoExameMedico;
    }

    public void setDataAprovacaoExameMedico(String dataAprovacaoExameMedico) {
        this.dataAprovacaoExameMedico = dataAprovacaoExameMedico;
    }

    public String getDataAprovacaoExameTeorico() {
        return dataAprovacaoExameTeorico;
    }

    public void setDataAprovacaoExameTeorico(String dataAprovacaoExameTeorico) {
        this.dataAprovacaoExameTeorico = dataAprovacaoExameTeorico;
    }

    public String getDataAprovacaoMoto() {
        return dataAprovacaoMoto;
    }

    public void setDataAprovacaoMoto(String dataAprovacaoMoto) {
        this.dataAprovacaoMoto = dataAprovacaoMoto;
    }

    public String getDataAprovacaoCarro() {
        return dataAprovacaoCarro;
    }

    public void setDataAprovacaoCarro(String dataAprovacaoCarro) {
        this.dataAprovacaoCarro = dataAprovacaoCarro;
    }

    @Override
    public String toString() {

        return getCodProcesso() + " - " +getNome();
    }
}
