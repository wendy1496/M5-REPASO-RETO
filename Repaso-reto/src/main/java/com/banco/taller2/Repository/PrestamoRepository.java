package com.banco.taller2.Repository;

import com.banco.taller2.Model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query("SELECT p FROM Prestamo p WHERE p.cliente.id = :idCliente ORDER BY p.fechaCreacion DESC LIMIT 3")
    List<Prestamo> findTrxPrestamos(String idCliente);

    List<Prestamo> findByClienteId(String idCliente);

}
