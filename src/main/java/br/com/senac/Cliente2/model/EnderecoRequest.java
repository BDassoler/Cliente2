package br.com.senac.Cliente2.model;

public class EnderecoRequest {
    private Long id;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String nomeResponsavel;
    private Long cliente2Id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public Long getCliente2Id() {
        return cliente2Id;
    }

    public void setCliente2Id(Long cliente2Id) {
        this.cliente2Id = cliente2Id;
    }
}
