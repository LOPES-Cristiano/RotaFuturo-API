package br.com.rotafuturo.carreiras.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.model.GrupoAcessoBean;
import br.com.rotafuturo.carreiras.repository.GrupoAcessoRepository;
@Service
public class GrupoAcessoService {
    @Autowired
    private GrupoAcessoRepository grupoAcessoRepository;
    public List<GrupoAcessoBean> listarTodos() {
        return grupoAcessoRepository.findAll();
    }
    public GrupoAcessoBean buscarPorId(Integer id) {
        return grupoAcessoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo de acesso não encontrado"));
    }
    public GrupoAcessoBean buscarPorDescricao(String descricao) {
        return grupoAcessoRepository.findByGruaDescricao(descricao);
    }
    public GrupoAcessoBean salvar(GrupoAcessoBean grupoAcesso) {
        return grupoAcessoRepository.save(grupoAcesso);
    }
    public void excluir(Integer id) {
        grupoAcessoRepository.deleteById(id);
    }
}