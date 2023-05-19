package com.chakra.projects.investment.service.carparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PythonRunner {
    public static String execute(String fileName, String password) {
        try {
            // Specify the path to the Python interpreter and the Python script
            String pythonInterpreter = "python3";
            String pythonScript = "/home/chakra/Playground/Investments/investment/src/main/java/com/chakra/projects/investment/service/carparser/carparser.py";

//            String fileName = "/home/chakra/Playground/Investments/investment/upload-dir/may_2.pdf";
//            String password = "sairam123*";

            // Create the ProcessBuilder and set the command
            ProcessBuilder processBuilder = new ProcessBuilder(pythonInterpreter, pythonScript);

            processBuilder.environment().put("PYTHONPATH", " /home/chakra/.local/lib/python3.10/site-packages\n");

            processBuilder.command().add(fileName);
            processBuilder.command().add(password);


            // Start the process
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }


            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            String errorLine;
            int exitCode = process.waitFor();

            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }

            // Wait for the process to complete


            if (exitCode != 0) {
                return null;
            }


            return response.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
