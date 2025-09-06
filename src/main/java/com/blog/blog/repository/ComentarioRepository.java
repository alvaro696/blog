package com.blog.blog.repository;

import com.blog.blog.entity.Comentario;
import com.blog.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByBlog(Blog blog);

    @Query("SELECT c FROM Comentario c WHERE c.blog.id = :blogId ORDER BY c.fechaComentario DESC")
    List<Comentario> findByBlogIdOrderByFechaDesc(@Param("blogId") Long blogId);

    List<Comentario> findByEmailComentarista(String email);

    List<Comentario> findByPuntuacion(Integer puntuacion);

    @Query("SELECT c FROM Comentario c WHERE c.puntuacion BETWEEN :min AND :max")
    List<Comentario> findByPuntuacionBetween(@Param("min") Integer puntuacionMin,
            @Param("max") Integer puntuacionMax);

    @Query("SELECT MIN(c.puntuacion), MAX(c.puntuacion), AVG(c.puntuacion), COUNT(c) " +
            "FROM Comentario c WHERE c.blog.id = :blogId")
    Object[] findPuntuacionStatsByBlogId(@Param("blogId") Long blogId);

    @Query("SELECT COUNT(c) FROM Comentario c WHERE c.blog.id = :blogId")
    Long countByBlogId(@Param("blogId") Long blogId);

    List<Comentario> findByPaisComentarista(String pais);

    @Query("SELECT c FROM Comentario c WHERE LOWER(c.contenido) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Comentario> findByContenidoContaining(@Param("termino") String termino);
}
