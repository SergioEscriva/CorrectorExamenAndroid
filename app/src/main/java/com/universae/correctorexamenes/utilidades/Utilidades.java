package com.universae.correctorexamenes.utilidades;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utilidades {



    private static final String JSON_FILE_PATH = "src/Json/codigos.json";

    public JSONArray json(String codigo) throws JSONException, IOException {
//	String filePath = "src/Json/codigos.json"; // Reemplaza con la ruta correcta
	JSONArray jsonArray = new JSONArray();

	// Check if the file exists
	boolean fileExists = new File(JSON_FILE_PATH).exists();

	if (fileExists) {
	    // Read the existing JSON content
	    try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {
		StringBuilder jsonString = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
		    jsonString.append(line);
		}
		JSONObject json = new JSONObject(jsonString.toString());

		// Check if the key "00004" exists and is a JSONArray
		if (json.has(codigo) && json.get(codigo) instanceof JSONArray) {
		    jsonArray = json.getJSONArray(codigo);
		} else {
		    return null;
		}

	    } catch (IOException | JSONException e) {
		System.out.print("error");
		// Handle potential errors
		e.printStackTrace();
	    }
	}

	return jsonArray;
    }

    public void guardarJson(Map<Integer, String> plantillaCorrecionMap, String codigo) {

	// Check if the file exists
	boolean fileExists = new File(JSON_FILE_PATH).exists();

	JSONObject json;
	if (fileExists) {
	    // Read the existing JSON content
	    try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {
		StringBuilder jsonString = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
		    jsonString.append(line);
		}
		json = new JSONObject(jsonString.toString());
	    } catch (IOException | JSONException e) {
		// Handle potential errors
		e.printStackTrace();
		return; // Or throw an exception if desired
	    }
	} else {
	    // Create a new JSON object if the file doesn't exist
	    json = new JSONObject();
	}

	// Add the new data to the JSON object
	try {
	    // Ensure we are appending to an existing JSONArray or creating a new one if it
	    // doesn't exist
	    JSONArray existingData;
	    if (json.has(codigo)) {
		existingData = json.getJSONArray(codigo);
	    } else {
		existingData = new JSONArray();
		json.put(codigo, existingData);
	    }

	    // Add each value from the map to the JSONArray
	    for (String value : plantillaCorrecionMap.values()) {
		existingData.put(value);
	    }

	} catch (JSONException e) {
	    e.printStackTrace();
	}

	// Write the updated JSON to the file
	try (PrintWriter out = new PrintWriter(new FileWriter(JSON_FILE_PATH))) {
	    out.write(json.toString()); // Pretty print with an indentation of 4 spaces
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}
