package nerdstore.catalogo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nerdstore.catalogo.domain.Categoria;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, String> {
	Categoria findByCodigo(Integer codigo);
	Categoria findByNome(String nome);
}
