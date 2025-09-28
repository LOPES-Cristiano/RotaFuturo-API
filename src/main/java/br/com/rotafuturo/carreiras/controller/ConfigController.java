package br.com.rotafuturo.carreiras.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.repository.TesteRepository;

@RestController
@RequestMapping("/config")
public class ConfigController {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private TesteRepository testeRepository;
    
    /**
     * Retorna configurações da aplicação
     * Usa a lógica de que o teste vocacional nunca tem área associada
     * e o teste de subárea sempre tem área associada
     */
    @GetMapping
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        
        // Busca o teste vocacional (sem área associada)
        try {
            List<TesteBean> testesVocacionais = testeRepository.findVocationalTests();
            if (testesVocacionais != null && !testesVocacionais.isEmpty()) {
                Integer testeId = testesVocacionais.get(0).getTesId();
                logger.info("Teste vocacional encontrado: {}", testeId);
                config.put("testeVocacionalId", testeId);
            } else {
                logger.warn("Nenhum teste vocacional encontrado no banco de dados");
                config.put("testeVocacionalId", null);
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar teste vocacional: {}", e.getMessage());
            config.put("testeVocacionalId", null);
        }
        
        // Busca um teste de subárea genérico (com área associada)
        try {
            List<TesteBean> testesSubarea = testeRepository.findSubareaTests();
            if (testesSubarea != null && !testesSubarea.isEmpty()) {
                Integer testeId = testesSubarea.get(0).getTesId();
                logger.info("Teste de subárea encontrado: {}", testeId);
                config.put("testeSubareaId", testeId);
            } else {
                logger.warn("Nenhum teste de subárea encontrado no banco de dados");
                config.put("testeSubareaId", null);
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar teste de subárea: {}", e.getMessage());
            config.put("testeSubareaId", null);
        }
        
        return config;
    }
    
    /**
     * Retorna o ID do teste de subárea com base na área selecionada
     * @param areaId ID da área selecionada no primeiro teste
     * @return ID do teste de subárea associado à área ou um teste genérico se não encontrado
     */
    @GetMapping("/teste-subarea/{areaId}")
    public ResponseEntity<Map<String, Object>> getTesteSubareaByArea(@PathVariable Integer areaId) {
        logger.info("Buscando teste de subárea para a área ID: {}", areaId);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Conforme a regra de negócio, o segundo teste (teste de subárea) sempre tem área associada
            // Primeiro, verificamos se existe um teste específico para a área selecionada
            List<TesteBean> testes = testeRepository.findByAreaId(areaId);
            
            if (testes != null && !testes.isEmpty()) {
                // Encontramos um teste associado a esta área específica
                Integer testeId = testes.get(0).getTesId();
                logger.info("Teste de subárea encontrado para a área {}: {}", areaId, testeId);
                response.put("testeSubareaId", testeId);
            } else {
                // Não encontramos um teste específico para esta área
                // Buscamos qualquer teste de subárea (que sempre tem área associada, segundo a regra)
                List<TesteBean> testesSubarea = testeRepository.findSubareaTests();
                
                if (testesSubarea != null && !testesSubarea.isEmpty()) {
                    Integer testeId = testesSubarea.get(0).getTesId();
                    logger.info("Teste de subárea genérico encontrado: {}", testeId);
                    response.put("testeSubareaId", testeId);
                } else {
                    logger.warn("Nenhum teste de subárea encontrado no banco de dados");
                    response.put("testeSubareaId", null);
                }
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro ao buscar teste de subárea para área {}: {}", areaId, e.getMessage());
            response.put("testeSubareaId", null);
            return ResponseEntity.ok(response);
        }
    }
}