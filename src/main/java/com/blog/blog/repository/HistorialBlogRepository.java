package com.blog.blog.repository;

import com.blog.blog.entity.HistorialBlog;
import com.blog.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistorialBlogRepository extends JpaRepository<HistorialBlog, Long> {

    List<HistorialBlog> findByBlogOrderByVersionDesc(Blog blog);

    @Query("SELECT h FROM HistorialBlog h WHERE h.blog.id = :blogId ORDER BY h.version DESC")
    List<HistorialBlog> findByBlogIdOrderByVersionDesc(@Param("blogId") Long blogId);

    Optional<HistorialBlog> findByBlogAndVersion(Blog blog, Integer version);

    @Query("SELECT h FROM HistorialBlog h WHERE h.blog.id = :blogId ORDER BY h.version DESC LIMIT 1")
    Optional<HistorialBlog> findLatestByBlogId(@Param("blogId") Long blogId);

    @Query("SELECT COALESCE(MAX(h.version), 0) + 1 FROM HistorialBlog h WHERE h.blog.id = :blogId")
    Integer getNextVersionForBlog(@Param("blogId") Long blogId);

    @Query("SELECT COUNT(h) FROM HistorialBlog h WHERE h.blog.id = :blogId")
    Long countVersionsByBlogId(@Param("blogId") Long blogId);

    @Query("SELECT h FROM HistorialBlog h WHERE LOWER(h.tipoCambio) LIKE LOWER(CONCAT('%', :tipo, '%'))")
    List<HistorialBlog> findByTipoCambioContaining(@Param("tipo") String tipoCambio);

    @Query("DELETE FROM HistorialBlog h WHERE h.blog.id = :blogId")
    void deleteByBlogId(@Param("blogId") Long blogId);
}
