package com.romulo.os.repositories;

import com.romulo.os.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query("SELECT obj FROM Cliente obj WHERE obj.cpf =:cpf")
    Optional<Cliente> findByCPF(@Param("cpf") String cpf);
}
