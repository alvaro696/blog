package com.blog.blog.repository;

import com.blog.blog.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT a FROM Autor a WHERE a.paisResidencia = :pais")
    java.util.List<Autor> findByPaisResidencia(@Param("pais") String pais);

    @Query("SELECT a FROM Autor a WHERE " +
           "LOWER(a.nombres) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(a.apellidoPaterno) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(a.apellidoMaterno) LIKE LOWER(CONCAT('%', :termino, '%'))")
    java.util.List<Autor> findByNombreContaining(@Param("termino") String termino);
}
