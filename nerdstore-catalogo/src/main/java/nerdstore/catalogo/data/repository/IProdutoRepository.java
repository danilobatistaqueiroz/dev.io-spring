package nerdstore.catalogo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nerdstore.catalogo.domain.Produto;

@Repository
public interface IProdutoRepository extends MongoRepository<Produto, String> {
  
}
