package br.com.senac.Cliente2.repositorys;


import br.com.senac.Cliente2.entitys.Cliente2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface Cliente2Repository extends JpaRepository<Cliente2, Long> {
}

