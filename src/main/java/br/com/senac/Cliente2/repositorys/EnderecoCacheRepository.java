package br.com.senac.Cliente2.repositorys;

import br.com.senac.Cliente2.entitys.Endereco;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnderecoCacheRepository {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Cacheable("enderecos")
    public List<Endereco> carregarTodosEnderecos(){
        return enderecoRepository.findAll();
    }
}
