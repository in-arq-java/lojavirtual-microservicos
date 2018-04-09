package br.nom.penha.bruno.arquiteto.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class FormatHelper {
    private static final FormatHelper instance = new FormatHelper();
    private final SimpleDateFormat dataSemana=new SimpleDateFormat("EEEE dd/MM/yyyy", new Locale("pt","br"));
    private final SimpleDateFormat dataCompleta=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final SimpleDateFormat dataSimples=new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat dataCurta=new SimpleDateFormat("dd/MM/yy");
    private final SimpleDateFormat dataDatabase=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat dataCartao = new SimpleDateFormat("MM/yy");
    public static final SimpleDateFormat sdfHora=new SimpleDateFormat("HH:mm");
    
    public static FormatHelper getInstance() {
        return instance;
    }
    private FormatHelper() {
    }
    
    // muda hora para String
    public String horaFormat(Date data) {
        if(data==null) return "";
        else return sdfHora.format(data);
    }
    
    public String bigDateFormat(Date data) {
        if(data==null) return "";
        else return dataSemana.format(data);
    }
    public String fullDateFormat(Date data) {
        if(data==null) return "";
        else return dataCompleta.format(data);
    }
    public String simpleDateFormat(Date data) {
        if(data==null) return "";
        else return dataSimples.format(data);
    }
    public String shortDateFormat(Date data) {
        if(data==null) return "";
        else return dataCurta.format(data);
    }
    public String databaseDateFormat(Date data) {
        if(data==null) return "";
        else return dataDatabase.format(data);
    }
    public static String dataCartaoFormat(Date data) {
        if(data==null) return "";
        else return dataCartao.format(data);        
    }
    
    // muda String para hora formatada
    public java.util.Date parseHora(String hora) throws ParseException {
        if(hora==null || hora.equals(""))
            return null;
        else {
            return sdfHora.parse(hora);
        }
    }
    
    public java.util.Date parseSimpleDate(String data) throws ParseException {
        if(data==null || data.equals(""))
            return null;
        else {
            return dataSimples.parse(data);
        }
    }
    public java.util.Date parseShortDate(String data) throws ParseException {
        if(data==null || data.equals(""))
            return null;
        else {
            return dataCurta.parse(data);
        }
    }
    public java.util.Date parseFullDate(String data) throws ParseException {
        if(data==null || data.equals(""))
            return null;
        else {
            return dataCompleta.parse(data);
        }
    }
    public java.util.Date parseBigDate(String data) throws ParseException {
        if(data==null || data.equals(""))
            return null;
        else {
            return dataSemana.parse(data);
        }
    }
    public java.util.Date parseDatabase(String data) throws ParseException {
        if(data==null || data.equals(""))
            return null;
        else {
            return dataDatabase.parse(data);
        }
    }
    
    public static java.util.Date parseDataCartao(String data) throws ParseException {
        if(data==null || data.equals(""))
            return null;
        else {
            return dataCartao.parse(data);
        }
    }
 
    
    public static String shortLocalTime(java.util.Date date) {
        return sdfHora.format(date);
    }
    
    // converte data em string (dd/MM/yyyy HH:mm) para data em string de bd (yyyy/MM/dd HH:mm) 
    public String converteData(String strData){
        Date data = null;
        try {
            data = parseFullDate(strData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        strData = databaseDateFormat(data);
        return strData;
    }
    // inverte data em string de bd (yyyy/MM/dd HH:mm) para data em string (dd/MM/yyyy HH:mm) 
    public String inverteData(String strData){
        Date data = null;
        try {
            data = parseDatabase(strData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        strData = fullDateFormat(data);
        return strData;
    }
    
}