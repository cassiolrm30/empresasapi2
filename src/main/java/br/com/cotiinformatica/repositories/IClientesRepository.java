package br.com.cotiinformatica.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import br.com.cotiinformatica.entities.Cliente;

public interface IClientesRepository extends CrudRepository<Cliente, Integer>
{
	@Query("select c from Cliente c where c.nome = :param1")
	Cliente findByNome(@Param("param1") String nome) throws Exception;
}