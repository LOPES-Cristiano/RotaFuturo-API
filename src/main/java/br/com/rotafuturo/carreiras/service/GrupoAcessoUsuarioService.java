package br.com.rotafuturo.carreiras.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.model.GrupoAcessoBean;
import br.com.rotafuturo.carreiras.model.GrupoAcessoUsuarioBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.GrupoAcessoRepository;
import br.com.rotafuturo.carreiras.repository.GrupoAcessoUsuarioRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;
@Service
public class GrupoAcessoUsuarioService {
    @Autowired
    private GrupoAcessoUsuarioRepository grupoAcessoUsuarioRepository;
    @Autowired
    private GrupoAcessoRepository grupoAcessoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public List<GrupoAcessoUsuarioBean> listarTodos() {
        return grupoAcessoUsuarioRepository.findAll();
    }
    public List<GrupoAcessoUsuarioBean> listarPorUsuario(Integer usuId) {
        return grupoAcessoUsuarioRepository.findByUsuarioId(usuId);
    }
    public List<String> listarGruposDoUsuario(Integer usuId) {
        return grupoAcessoUsuarioRepository.findByUsuarioId(usuId).stream()
                .map(gau -> gau.getGrupoAcesso().getGruaDescricao())
                .collect(Collectors.toList());
    }
    public boolean usuarioPertenceAoGrupo(Integer usuId, String grupoDescricao) {
        return grupoAcessoUsuarioRepository.usuarioPertenceAoGrupo(usuId, grupoDescricao);
    }
    public GrupoAcessoUsuarioBean associarUsuarioAGrupo(Integer usuId, Integer gruaId) {
        UsuarioBean usuario = usuarioRepository.findById(usuId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        GrupoAcessoBean grupoAcesso = grupoAcessoRepository.findById(gruaId)
                .orElseThrow(() -> new RuntimeException("Grupo de acesso não encontrado"));
        GrupoAcessoUsuarioBean associacao = new GrupoAcessoUsuarioBean();
        associacao.setUsuario(usuario);
        associacao.setGrupoAcesso(grupoAcesso);
        return grupoAcessoUsuarioRepository.save(associacao);
    }
    public void removerUsuarioDoGrupo(Integer usuId, Integer gruaId) {
        List<GrupoAcessoUsuarioBean> associacoes = grupoAcessoUsuarioRepository.findByUsuarioId(usuId);
        associacoes.stream()
            .filter(assoc -> assoc.getGrupoAcesso().getGruaId().equals(gruaId))
            .findFirst()
            .ifPresent(assoc -> grupoAcessoUsuarioRepository.deleteById(assoc.getGauId()));
    }
}