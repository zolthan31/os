package com.romulo.os.repositories;

import com.romulo.os.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    @Query("SELECT obj FROM Tecnico obj WHERE obj.cpf =:cpf")
    Optional<Tecnico> findByCPF(@Param("cpf") String cpf);
}
