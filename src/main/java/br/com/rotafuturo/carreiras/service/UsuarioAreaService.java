package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafuturo.carreiras.dto.AreaDTO;
import br.com.rotafuturo.carreiras.dto.AreaSubDTO;
import br.com.rotafuturo.carreiras.dto.UsuarioAreaDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.UsuarioAreaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioAreaRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;

/**
 * Serviço para gerenciar os vínculos de usuários com áreas e subáreas.
 */
@Service
public class UsuarioAreaService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioAreaService.class);
    
    private final UsuarioAreaRepository usuarioAreaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaRepository areaRepository;
    private final AreaSubRepository areaSubRepository;
    
    @Autowired
    public UsuarioAreaService(
            UsuarioAreaRepository usuarioAreaRepository,
            UsuarioRepository usuarioRepository,
            AreaRepository areaRepository,
            AreaSubRepository areaSubRepository) {
        this.usuarioAreaRepository = usuarioAreaRepository;
        this.usuarioRepository = usuarioRepository;
        this.areaRepository = areaRepository;
        this.areaSubRepository = areaSubRepository;
    }
    
    /**
     * Lista todas as áreas e subáreas vinculadas a um usuário.
     */
    public List<UsuarioAreaDTO> listarAreasDoUsuario(Integer usuarioId) {
        logger.info("Listando áreas do usuário ID: {}", usuarioId);
        
        // Busca TODOS os vínculos do usuário (com ou sem subárea)
        List<UsuarioAreaBean> usuarioAreas = usuarioAreaRepository.findAll().stream()
                .filter(ua -> ua.getUsuario() != null && ua.getUsuario().getUsuId().equals(usuarioId))
                .collect(Collectors.toList());
        
        logger.info("Encontrados {} vínculos de área para o usuário {}", usuarioAreas.size(), usuarioId);
        
        // Log detalhado para debug
        usuarioAreas.forEach(ua -> {
            logger.debug("Vínculo encontrado - USUA_ID: {}, AREA: {}, AREASUB: {}", 
                ua.getUsuaId(),
                ua.getArea() != null ? ua.getArea().getAreaDescricao() : "null",
                ua.getAreaSub() != null ? ua.getAreaSub().getAreasDescricao() : "null"
            );
        });
        
        return usuarioAreas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Converte UsuarioAreaBean para UsuarioAreaDTO.
     */
    private UsuarioAreaDTO convertToDTO(UsuarioAreaBean bean) {
        UsuarioAreaDTO dto = new UsuarioAreaDTO();
        dto.setUsuareaId(bean.getUsuaId());
        dto.setUsuarioId(bean.getUsuario() != null ? bean.getUsuario().getUsuId() : null);
        dto.setUsuareaDatacadastro(bean.getUsuaDatacadastro());
        dto.setUsuareaHoracadastro(bean.getUsuaHoracadastro());
        
        if (bean.getArea() != null) {
            AreaDTO areaDTO = new AreaDTO();
            areaDTO.setAreaId(bean.getArea().getAreaId());
            areaDTO.setAreaDescricao(bean.getArea().getAreaDescricao());
            dto.setArea(areaDTO);
        }
        
        if (bean.getAreaSub() != null) {
            AreaSubDTO areaSubDTO = new AreaSubDTO();
            areaSubDTO.setAreasId(bean.getAreaSub().getAreasId());
            areaSubDTO.setAreasDescricao(bean.getAreaSub().getAreasDescricao());
            dto.setAreaSub(areaSubDTO);
        }
        
        return dto;
    }
    
    /**
     * Vincula uma área e opcionalmente uma subárea ao usuário.
     */
    @Transactional
    public UsuarioAreaDTO vincularArea(Integer usuarioId, Integer areaId, Integer areaSubId) {
        logger.info("Vinculando área {} e subárea {} ao usuário {}", areaId, areaSubId, usuarioId);
        
        // Busca o usuário
        UsuarioBean usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioId));
        
        // Busca a área
        AreaBean area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área não encontrada: " + areaId));
        
        // Busca a subárea se informada
        AreaSubBean areaSub = null;
        if (areaSubId != null) {
            areaSub = areaSubRepository.findById(areaSubId)
                    .orElseThrow(() -> new RuntimeException("Subárea não encontrada: " + areaSubId));
        }
        
        // Cria o vínculo
        UsuarioAreaBean usuarioArea = new UsuarioAreaBean();
        usuarioArea.setUsuario(usuario);
        usuarioArea.setArea(area);
        usuarioArea.setAreaSub(areaSub);
        usuarioArea.setUsuaDatacadastro(LocalDate.now());
        usuarioArea.setUsuaHoracadastro(LocalTime.now());
        
        UsuarioAreaBean saved = usuarioAreaRepository.save(usuarioArea);
        
        logger.info("Vínculo criado com sucesso. ID: {}", saved.getUsuaId());
        
        return convertToDTO(saved);
    }
    
    /**
     * Remove o vínculo de uma área do usuário.
     */
    @Transactional
    public void desvincularArea(Integer usuareaId) {
        logger.info("Desvinculando área com ID: {}", usuareaId);
        
        Optional<UsuarioAreaBean> usuarioArea = usuarioAreaRepository.findById(usuareaId);
        
        if (usuarioArea.isPresent()) {
            usuarioAreaRepository.delete(usuarioArea.get());
            logger.info("Vínculo removido com sucesso");
        } else {
            throw new RuntimeException("Vínculo não encontrado: " + usuareaId);
        }
    }
    
    /**
     * Remove todos os vínculos de áreas do usuário.
     */
    @Transactional
    public void desvincularTodasAreas(Integer usuarioId) {
        logger.info("Desvinculando todas as áreas do usuário {}", usuarioId);
        
        List<UsuarioAreaBean> vinculos = usuarioAreaRepository.findAll().stream()
                .filter(ua -> ua.getUsuario() != null && ua.getUsuario().getUsuId().equals(usuarioId))
                .collect(Collectors.toList());
        
        usuarioAreaRepository.deleteAll(vinculos);
        
        logger.info("{} vínculos removidos", vinculos.size());
    }
}
