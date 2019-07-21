package com.helycopternicht.epocket.repositories;

import com.helycopternicht.epocket.models.Currency;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByName(@NonNull String name);
}
