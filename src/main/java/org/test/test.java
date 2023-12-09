package org.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test
{
    //hi
    public Object[] borrowBook()
    {
        String result = "[\n" +
                "    {\n" +
                "        \"num\": 27,\n" +
                "        \"name\": \"내가 보여?\",\n" +
                "        \"writer\": \"박지희 글·그림\",\n" +
                "        \"publisher\": \"웅진씽크빅\",\n" +
                "        \"publishYear\": 2022,\n" +
                "        \"bookNum\": 1,\n" +
                "        \"borrowNum\": 0,\n" +
                "        \"borrowCount\": 0,\n" +
                "        \"registerDate\": 20231031,\n" +
                "        \"member\": null\n" +
                "    }\n" +
                "]";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(result);
            JsonNode firstNode = rootNode.get(0);

            int number=firstNode.path("num").asInt();
            System.out.println(1);
            System.out.println(number);
            String[] borrowInfo = {String.valueOf(number)};
            return borrowInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
