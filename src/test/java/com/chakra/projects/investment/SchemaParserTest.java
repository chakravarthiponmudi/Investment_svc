package com.chakra.projects.investment;

import com.chakra.projects.investment.Domain.cams.CAS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class SchemaParserTest {

    @Test
    public void testParsing() {
        try {
            CAS cas = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readerFor(CAS.class).readValue(new File("/home/chakra/Playground/investment/src/test/java/com/chakra/projects/investment/cams_test_data.json"));
            System.out.println("Success");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
