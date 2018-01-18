package br.com.re98.autoescolamais.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by isaac on 1/9/18.
 */

public class ConverteData {

    private String dataFormatada;

    public String converteData(String dt, int format){
        String dataEmUmFormato = dt;

        if(dt != null){
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date data = null;
            try {
                data = formato.parse(dataEmUmFormato);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(format == 1){
                formato.applyPattern("dd/MM/yyyy");
            } else {
                formato.applyPattern("dd/MM/yy");
            }

            dataFormatada = formato.format(data);

        }
        else {
            dataFormatada = "";
        }

        return dataFormatada;

    }
}
