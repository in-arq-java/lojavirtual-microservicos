package br.nom.penha.bruno.arquiteto.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Classe com métodos utilitários para o JSF
 *
 */
public class JSFHelper {

    /**
     * adiciona uma mensagem global de sucesso no contexto Faces para ser
     * exibida em uma página JSF através da tag <h:messages>
     *
     * @param message texto da mensagem a ser exibida
     */
    public static void addSuccessMessage(String message) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    /**
     * adiciona uma mensagem global de erro no contexto Faces para ser exibida
     * em uma página JSF através da tag <h:messages>
     *
     * @param message texto da mensagem a ser exibida
     */
    public static void addErrorMessage(String message) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
}
