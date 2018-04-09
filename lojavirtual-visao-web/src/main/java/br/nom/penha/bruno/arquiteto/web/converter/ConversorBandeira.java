package br.nom.penha.bruno.arquiteto.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.nom.penha.bruno.arquiteto.model.BandeiraCartaoCredito;

@FacesConverter(forClass=BandeiraCartaoCredito.class)
public class ConversorBandeira implements Converter {

    private static final String NOME = "nomeBandeira=";
    private static final String ID = "idBandeira=";
    public ConversorBandeira() {
    }

    @Override
    public Object getAsObject(FacesContext facesContext,
                              UIComponent uIComponent, String novoValor) {        
        String texto = novoValor;
        int posDescricao = texto.indexOf(NOME);
        int posId = texto.indexOf(ID);
        String nome = texto.substring(posDescricao + NOME.length(), posId-1);
        Integer id = new Integer(texto.substring(posId + ID.length(), texto.length()));        
        return new BandeiraCartaoCredito(id,nome);
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent uIComponent, Object object) {                              
        BandeiraCartaoCredito bandeira= (BandeiraCartaoCredito)object;
        return NOME+bandeira.getNome()+"&"+ID+bandeira.getId();
    }
}
