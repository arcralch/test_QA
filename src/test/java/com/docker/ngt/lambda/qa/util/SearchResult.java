package com.docker.ngt.lambda.qa.util;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class SearchResult {
    
    private Collection<Object> result;

    public SearchResult(Collection<Object> result){
        this.result = result;
    }

    public String asJson() {
		try {
			return new ObjectMapper().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			throw new UnsupportedOperationException("Uh, not possible to convert represent as json " + result, e);
		}
	}

	public String asJsonPath(String jsonPath) {
		return JsonPath.parse(asJson()).read(jsonPath).toString();
	}
	public int size() {
		return result.size();
	}
}