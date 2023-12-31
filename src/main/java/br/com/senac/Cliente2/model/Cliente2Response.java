package br.com.senac.Cliente2.model;

import java.time.LocalDate;
import java.util.List;

public class Cliente2Response {
    private Long id;
    private String nome;
    private String sobreNome;
    private LocalDate dataNascimento;
    private String documento;
    private String email;
    private String telefone;
    private List<EnderecoResponse> enderecos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<EnderecoResponse> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoResponse> enderecos) {
        this.enderecos = enderecos;
    }
}
