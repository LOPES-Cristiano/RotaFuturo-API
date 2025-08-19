package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Integer usuId;

    @Column(name = "usu_email", unique = true, length = 200)
    private String usuEmail;

    @Column(name = "usu_senha", length = 250)
    private String usuSenha;

    @Column(name = "usu_datacadastro")
    private LocalDate usuDataCadastro;

    @Column(name = "usu_horacadastro")
    private LocalTime usuHoraCadastro;

    @Column(name = "usu_emailvalidado")
    private Boolean usuEmailValidado;

    @Column(name = "usu_ativo")
    private Boolean usuAtivo;

    @Column(name = "usu_trocasenha")
    private Boolean usuTrocaSenha;

    @Column(name = "usu_senhaantiga1", length = 250)
    private String usuSenhaAntiga1;

    @Column(name = "usu_senhaantiga2", length = 250)
    private String usuSenhaAntiga2;

}
