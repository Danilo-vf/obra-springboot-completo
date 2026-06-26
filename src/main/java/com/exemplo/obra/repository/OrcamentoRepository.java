package com.exemplo.obra.repository;

import com.exemplo.obra.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

    List<Orcamento> findByNomeCliente(String nomeCliente);

    Optional<Orcamento> findByNumeroOrcamento(String numeroOrcamento);
}
