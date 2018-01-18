package br.com.re98.autoescolamais.modelo;


public class Exame {

    private String numero;
    private String id;
    private String tipo;
    private String dataexames;
    private String resultado;
    private String categoria;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataexames() {
        return dataexames;
    }

    public void setDataexames(String dataexames) {
        this.dataexames = dataexames;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return getId() + " - " + getCategoria() + getDataexames();
    }
}
