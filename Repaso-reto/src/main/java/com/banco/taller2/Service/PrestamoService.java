package com.banco.taller2.Service;

import com.banco.taller2.Model.Cliente;
import com.banco.taller2.Model.Prestamo;
import com.banco.taller2.Repository.ClienteRepository;
import com.banco.taller2.Repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, ClienteRepository clienteRepository) {
        this.prestamoRepository = prestamoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Prestamo solicitarPrestamo(String clienteId, BigDecimal monto, BigDecimal interes, int duracionMeses) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el cliente"));

        Prestamo prestamo = new Prestamo();
        prestamo.setMonto(monto);
        prestamo.setInteres(interes);
        prestamo.setDuracionMeses(duracionMeses);
        prestamo.setEstado("Pendiente");
        prestamo.setFechaCreacion(LocalDateTime.now());
        prestamo.setFechaActualizacion(LocalDateTime.now());
        prestamo.setCliente(cliente);

        return prestamoRepository.save(prestamo);
    }

    public Prestamo aprobarPrestamo(Long prestamoId, String nuevoEstado) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("El prestamo no existe, intente de nuevo"));

        if (!"Pendiente".equals(prestamo.getEstado())) {
            throw new IllegalArgumentException("El préstamo en proceso.");
        }

        if (!"Aprobado".equalsIgnoreCase(nuevoEstado) && !"Rechazado".equalsIgnoreCase(nuevoEstado)) {
            throw new IllegalArgumentException("Ingrese solamente 'Aprobado' o 'Rechazado'");
        }

        prestamo.setEstado(nuevoEstado);
        prestamo.setFechaActualizacion(LocalDateTime.now());

        return prestamoRepository.save(prestamo);
    }

    public Prestamo consultarPrestamoPorId(Long prestamoId) {
        return prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no existe, intente de nuevo."));
    }

    public List<Prestamo> consultarHistorialUltimosPrestamos(String clienteId) {
        return prestamoRepository.findTrxPrestamos(clienteId);
    }

    public BigDecimal simularCuota(BigDecimal monto, BigDecimal tasaInteresAnual, int duracionMeses) {
        BigDecimal tasaMensual = tasaInteresAnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        BigDecimal factor = BigDecimal.ONE.add(tasaMensual).pow(duracionMeses);

        BigDecimal cuota = monto.multiply(tasaMensual).multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        return cuota;
    }

    public List<Prestamo> consultarHistorialPorCliente(String clienteId) {
        return prestamoRepository.findByClienteId(clienteId);
    }


}
