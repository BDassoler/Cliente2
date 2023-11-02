package br.com.senac.Cliente2.repositorys;

import br.com.senac.Cliente2.entitys.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query("Select a from endereco a where a.cliente2.id =:cliente2Id")
    List<Endereco> carregarEnderecosByCliente2Id(@Param("cliente2Id") Long cliente2Id);
}
