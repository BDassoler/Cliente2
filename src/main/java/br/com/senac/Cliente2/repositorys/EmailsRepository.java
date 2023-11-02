package br.com.senac.Cliente2.repositorys;

import br.com.senac.Cliente2.entitys.Emails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailsRepository extends JpaRepository<Emails, Long> {
}
