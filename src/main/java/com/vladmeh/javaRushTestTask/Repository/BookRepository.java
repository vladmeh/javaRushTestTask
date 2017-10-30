package com.vladmeh.javaRushTestTask.Repository;


import com.vladmeh.javaRushTestTask.Entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Query("SELECT t FROM Book t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.autor) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Book> findBySearchTerm(@Param("searchTerm") String searchTerm);

    @Query("SELECT t FROM Book t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.autor) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Book> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageRequest);
}
