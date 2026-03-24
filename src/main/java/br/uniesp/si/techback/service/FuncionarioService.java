package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public List<FuncionarioDTO> listar() {
        log.info("Listando todos os funcionários");
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        log.debug("Total de funcionários encontrados: {}", funcionarios.size());

        return funcionarios.stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public FuncionarioDTO buscarPorId(Long id) {
        try {
            log.info("Buscando funcionário com ID: {}", id);

            Funcionario funcionario = funcionarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

            log.debug("Funcionário encontrado: {}", funcionario);
            return converterParaDTO(funcionario);

        } catch (Exception e) {
            log.error("Erro ao buscar funcionário com ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public FuncionarioDTO salvar(FuncionarioDTO funcionarioDTO) {
        try {
            log.info("Salvando novo funcionário: {}", funcionarioDTO.getNome());

            Funcionario funcionario = converterParaEntidade(funcionarioDTO);
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

            log.info("Funcionário salvo com sucesso. ID: {}", funcionarioSalvo.getId());
            return converterParaDTO(funcionarioSalvo);

        } catch (Exception e) {
            log.error("Erro ao salvar funcionário: {}", e.getMessage(), e);
            throw e;
        }
    }

    public FuncionarioDTO atualizar(Long id, FuncionarioDTO funcionarioDTO) {
        try {
            log.info("Atualizando funcionário com ID {}: {}", id, funcionarioDTO);

            Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

            funcionarioExistente.setNome(funcionarioDTO.getNome());
            funcionarioExistente.setCargo(funcionarioDTO.getCargo());

            Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionarioExistente);

            log.debug("Funcionário ID {} atualizado com sucesso", id);
            return converterParaDTO(funcionarioAtualizado);

        } catch (Exception e) {
            log.error("Erro ao atualizar funcionário ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public void excluir(Long id) {
        try {
            log.info("Excluindo funcionário com ID: {}", id);

            Funcionario funcionario = funcionarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

            funcionarioRepository.delete(funcionario);

            log.debug("Funcionário com ID {} excluído com sucesso", id);

        } catch (Exception e) {
            log.error("Erro ao excluir funcionário com ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    private FuncionarioDTO converterParaDTO(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setCargo(funcionario.getCargo());
        return dto;
    }

    private Funcionario converterParaEntidade(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioDTO.getId());
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setCargo(funcionarioDTO.getCargo());
        return funcionario;
    }
}