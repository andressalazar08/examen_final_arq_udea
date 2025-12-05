package examenfinal.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import examenfinal.demo.model.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
}
