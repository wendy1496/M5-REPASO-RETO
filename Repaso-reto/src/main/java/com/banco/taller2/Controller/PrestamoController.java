package com.banco.taller2.Controller;

import com.banco.taller2.DTO.PrestamoDTO;
import com.banco.taller2.Model.Prestamo;
import com.banco.taller2.Service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {
    private final PrestamoService prestamoService;

    @Autowired
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Prestamo> solicitarPrestamo(@RequestBody @Valid PrestamoDTO solicitudPrestamoDTO) {
        Prestamo prestamo = prestamoService.solicitarPrestamo(
                solicitudPrestamoDTO.getClienteId(),
                solicitudPrestamoDTO.getMonto(),
                solicitudPrestamoDTO.getInteres(),
                solicitudPrestamoDTO.getDuracionMeses()
        );
        return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
    }

    @PutMapping("/aprobar/{prestamoId}")
    public ResponseEntity<Prestamo> aprobarPrestamo(
            @PathVariable Long prestamoId,
            @RequestParam String nuevoEstado) {

        try {
            Prestamo prestamo = prestamoService.aprobarPrestamo(prestamoId, nuevoEstado);
            return new ResponseEntity<>(prestamo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{prestamoId}")
    public ResponseEntity<Prestamo> consultarPrestamoPorId(@PathVariable Long prestamoId) {
        try {
            Prestamo prestamo = prestamoService.consultarPrestamoPorId(prestamoId);
            return new ResponseEntity<>(prestamo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/historial/{clienteId}")
    public ResponseEntity<List<Prestamo>> obtenerHistorialPrestamos(@PathVariable String clienteId) {
        List<Prestamo> prestamos = prestamoService.consultarHistorialUltimosPrestamos(clienteId);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @PostMapping("/simulacion")
    public ResponseEntity<BigDecimal> simularCuota(@RequestBody @Valid Prestamo prestamo) {

        BigDecimal cuota = prestamoService.simularCuota(
                prestamo.getMonto(),
                prestamo.getInteres(),
                prestamo.getDuracionMeses()
        );

        return new ResponseEntity<>(cuota, HttpStatus.OK);
    }

    @GetMapping("/cliente/{clienteId}/historial")
    public ResponseEntity<List<Prestamo>> consultarHistorialPorCliente(@PathVariable String clienteId) {
        List<Prestamo> historial = prestamoService.consultarHistorialPorCliente(clienteId);
        if (historial.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }
}
