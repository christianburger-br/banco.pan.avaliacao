package com.example.avaliacao.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ExternalService {

    private RestTemplate restTemplate=  new RestTemplate();
    private TreeMap <String, JSONObject> estadosTreeMap= null;

    //https://viacep.com.br/ws/{cep}/json
    public String consultaEnderecoPorCEP(String cep) {
        String result= "";

        String url = String.format(Locale.getDefault(), "https://viacep.com.br/ws/%s/json", cep );
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            result += response.toString();
        }
        return result;
    }

    /*
    //https://servicodados.ibge.gov.br/api/v1/localidades/estados/
    Use ignoreContentType() (see doc here):
    String myURL = "http://www.rfi.ro/podcast/emisiune/174/feed.xml";
    Document pod = Jsoup.connect(myURL).ignoreContentType(true).get();
    */
    public String consultaEstados() throws IOException, JSONException {
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";
        this.estadosTreeMap= new TreeMap<String, JSONObject>();
        List<JSONObject> estadosList = new ArrayList<JSONObject>();

        String response= "";
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String jsonStringResponse= doc.body().html();
        log.info(String.format(Locale.getDefault(), "ExternalService: consultaEstados: doc.body(): getting raw: >>\n\n%s\n\n", jsonStringResponse));
        JSONArray jsonArray= new JSONArray(jsonStringResponse);
        log.info(String.format(Locale.getDefault(), "ExternalService: consultaEstados: doc.body(): getting JSONObjects from array: >>\n\n %s elements\n\n", jsonArray.length()));
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject= jsonArray.getJSONObject(i);
            if ( !jsonObject.isNull("sigla")) {
                String sigla= jsonObject.getString("sigla");
                estadosTreeMap.put(sigla, jsonObject);
                //response+= jsonObject.toString() + "<br/>";
                log.info(String.format(Locale.getDefault(), "ExternalService: consultaEstados: \n\n>> sigla: %s <<\n\n", sigla));
            } else {
                log.info(String.format(Locale.getDefault(), "ExternalService: consultaEstados: jsonObject.isNull(\"sigla\"): >>\n\n%s\n\n", jsonObject.isNull("sigla")));
            }
        }

        TreeMap<String, JSONObject> orderedEstados = new TreeMap<String, JSONObject>(estadosTreeMap);
        JSONObject sp= orderedEstados.remove("SP");
        JSONObject rj= orderedEstados.remove("RJ");

        estadosList.add(sp);
        estadosList.add(rj);
        estadosList.addAll(orderedEstados.values());

        for (JSONObject jsonObject : estadosList) {
            log.info(String.format(Locale.getDefault(), "ExternalService: consultaEstadosList: value: %s", jsonObject));
        }

        //response= estadosTreeMap.values().toString();
        response= estadosList.toString();
        return response;
    }

    //https://servicodados.ibge.gov.br/api/v1/localidades/estados/{id}/municipios
    public String consultaMunicipoPorEstados(String estado) throws JSONException, IOException {
        this.consultaEstados();
        String response= null;
        if (estadosTreeMap.get(estado) != null) {
            JSONObject jsonObject= estadosTreeMap.get(estado);
            if (!jsonObject.isNull("id")) {
                String id = jsonObject.getString("id");
                String key= jsonObject.getString("id");
                String url = String.format(Locale.getDefault(), "https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios", id);
                log.info(String.format(Locale.getDefault(), "ExternalService: consultaMunicipioPorEstado: url: >> %s: >> sigla: %s", url, jsonObject.isNull("sigla")));
                Document doc = Jsoup.connect(url).ignoreContentType(true).get();
                response= doc.body().html();
            }
        }
        return response;
    }

}
