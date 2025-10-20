package br.com.rotafuturo.carreiras.dto.importacao;

public class ErroImportacaoDTO {
    
    private Integer linha;
    private String erro;
    
    public ErroImportacaoDTO() {
    }
    
    public ErroImportacaoDTO(Integer linha, String erro) {
        this.linha = linha;
        this.erro = erro;
    }

    public Integer getLinha() {
        return linha;
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
