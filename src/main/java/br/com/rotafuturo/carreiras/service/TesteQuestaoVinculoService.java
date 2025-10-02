package br.com.rotafuturo.carreiras.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoVinculoRepository;
@Service
public class TesteQuestaoVinculoService {
    @Autowired
    private TesteQuestaoVinculoRepository testeQuestaoVinculoRepository;
    public List<TesteQuestaoVinculoBean> findAll() {
        return testeQuestaoVinculoRepository.findAll();
    }
    public TesteQuestaoVinculoBean save(TesteQuestaoVinculoBean testeQuestaoVinculo) {
        System.out.println("Saving TesteQuestaoVinculo: " + testeQuestaoVinculo);
        System.out.println("Teste ID: " + testeQuestaoVinculo.getTesteId());
        System.out.println("TesteQuestao ID: " + (testeQuestaoVinculo.getTesteQuestao() != null ? testeQuestaoVinculo.getTesteQuestao().getTesqId() : "null"));
        return testeQuestaoVinculoRepository.save(testeQuestaoVinculo);
    }
    public void deleteById(Integer id) {
        testeQuestaoVinculoRepository.deleteById(id);
    }
}
