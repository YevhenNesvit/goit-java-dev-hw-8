package spring.boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot.model.dao.ProductDao;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao, UUID> {

}
