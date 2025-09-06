package com.blog.blog.repository;

import com.blog.blog.entity.Blog;
import com.blog.blog.entity.Autor;
import com.blog.blog.entity.Periodicidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByAutor(Autor autor);

    @Query("SELECT b FROM Blog b WHERE b.autor.id = :autorId")
    List<Blog> findByAutorId(@Param("autorId") Long autorId);

    List<Blog> findByPeriodicidad(Periodicidad periodicidad);

    List<Blog> findByPermiteComentarios(Boolean permiteComentarios);

    @Query("SELECT b FROM Blog b WHERE LOWER(b.tema) LIKE LOWER(CONCAT('%', :tema, '%'))")
    List<Blog> findByTemaContaining(@Param("tema") String tema);

    @Query("SELECT b FROM Blog b WHERE LOWER(b.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Blog> findByTituloContaining(@Param("titulo") String titulo);

    @Query("SELECT b FROM Blog b LEFT JOIN FETCH b.comentarios WHERE b.id = :id")
    Optional<Blog> findByIdWithComentarios(@Param("id") Long id);

    @Query("SELECT b FROM Blog b LEFT JOIN FETCH b.historial WHERE b.id = :id")
    Optional<Blog> findByIdWithHistorial(@Param("id") Long id);

    @Query("SELECT DISTINCT b FROM Blog b " +
            "LEFT JOIN FETCH b.autor " +
            "LEFT JOIN FETCH b.comentarios " +
            "WHERE b.id = :id")
    Optional<Blog> findByIdComplete(@Param("id") Long id);

    @Query("SELECT DISTINCT b FROM Blog b LEFT JOIN FETCH b.autor")
    List<Blog> findAllWithAutor();
}
