package com.mindhub.cerveceria.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Peticion {

    public static boolean realizarPeticion(Double montoTotal,
                                           String numeroDeTajerta,
                                           Integer cvv
                                           //LocalDate fechaDeVencimiento
                                           ){

        try{

            URL url = new URL("http://localhost:8080/api/pay/v2");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("totalToPay", montoTotal);
            params.put("numberCard", numeroDeTajerta);
            params.put("cvv", cvv);
            //params.put("thruDate", fechaDeVencimiento);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()){

                postData.append(param.getKey());
                postData.append("=");
                postData.append(param.getValue());

                if(postData.length() != 0){
                    postData.append("&");
                }

            }

            byte[] postByte = postData.toString().getBytes(StandardCharsets.UTF_8);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Context-Type", "text/plain");
            conn.setRequestProperty("Content-Length", String.valueOf(postByte.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postByte);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            for (int c = in.read(); c != -1; c = in.read()){
                System.out.print((char) c);
            }

            return true;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }


    }
}
