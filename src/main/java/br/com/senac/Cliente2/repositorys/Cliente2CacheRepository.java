package br.com.senac.Cliente2.repositorys;

import br.com.senac.Cliente2.entitys.Cliente2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cliente2CacheRepository {
    @Autowired
    private Cliente2Repository cliente2Repository;

    @Cacheable("clientes")
    public List<Cliente2> carregarTodosClientes(){
        return cliente2Repository.findAll();

    }
}
