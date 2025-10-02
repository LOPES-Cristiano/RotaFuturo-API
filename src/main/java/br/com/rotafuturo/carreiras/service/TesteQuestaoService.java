package br.com.rotafuturo.carreiras.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.model.TesteQuestaoBean;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoRepository;
@Service
public class TesteQuestaoService {
    @Autowired
    private TesteQuestaoRepository testeQuestaoRepository;
    public List<TesteQuestaoBean> findAll() {
        return testeQuestaoRepository.findAll();
    }
        public TesteQuestaoBean findById(Integer id) {
        return testeQuestaoRepository.findById(id).orElse(null);
    }
    public TesteQuestaoBean save(TesteQuestaoBean testeQuestao) {
        TesteQuestaoBean savedTesteQuestao = testeQuestaoRepository.save(testeQuestao);
        System.out.println("Saved TesteQuestao: " + savedTesteQuestao);
        return savedTesteQuestao;
    }
    public void deleteById(Integer id) {
        testeQuestaoRepository.deleteById(id);
    }
}
