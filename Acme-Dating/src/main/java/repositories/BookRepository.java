package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
