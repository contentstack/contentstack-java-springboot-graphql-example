package com.contentstack.gqlspring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

final class GraphqlBuilder {

    private final String tag;
    private final String url;
    private final JSONObject queryJson;
    private final HttpHeaders headers;

    public GraphqlBuilder(Builder builder) {
        this.tag = builder.tag;
        this.url = builder.url;
        this.queryJson = builder.queryJson;
        this.headers = builder.headers;
    }

    @Override
    public String toString() {
        return "GQL{" +
                ", tag='" + tag + '\'' +
                ", url='" + url + '\'' +
                ", queryJson=" + queryJson +
                ", headers=" + headers +
                '}';
    }

    public JsonNode fetch() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<JSONObject> entity = new HttpEntity<>(queryJson, this.headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String dataString = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(dataString);
    }

    public static class Builder {

        private String tag;
        private String url;
        private JSONObject queryJson = new JSONObject();
        private HttpHeaders headers = new HttpHeaders();

        private Builder() {
        }

        public static Builder newInstance() {
            return new Builder();
        }


        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }


        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setQueryString(String queryString) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.appendField("query", queryString);
            this.queryJson = jsonObject;
            return this;
        }

        public Builder setHeader(String access_token) {
            final HttpHeaders headers = new HttpHeaders();
            ArrayList<MediaType> acceptableMediaTypes = new ArrayList<>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            headers.setAccept(acceptableMediaTypes);
            headers.add("access_token", access_token);
            this.headers = headers;
            return this;
        }

        public GraphqlBuilder build() {
            return new GraphqlBuilder(this);
        }

    }

}
