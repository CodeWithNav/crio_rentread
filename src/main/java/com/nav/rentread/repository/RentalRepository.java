package com.nav.rentread.repository;

import com.nav.rentread.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Collection<Rental> findByUserIdAndReturnDateIsNull(Long userId);


    Optional<Rental> findByUserIdAndBookIdAndReturnDateIsNull(Long userId, Long bookId);
}
