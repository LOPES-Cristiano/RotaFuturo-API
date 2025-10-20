package br.com.rotafuturo.carreiras.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar endpoints que requerem permissão de Administrador.
 * A verificação é case-insensitive (aceita: Administrador, ADMINISTRADOR, administrador, etc)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAdmin {
    String message() default "Acesso negado: apenas administradores podem acessar este recurso";
}
