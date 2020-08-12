/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gyerent;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
/**
 *
 * @author Alejandro
 */
public class Coordenadas {
    private double latitud;
    private double longitud;
    private boolean ubicacionExite;
    
    
    //Constructor para inicializar Transportes
    public Coordenadas(double latitud, double longitud){
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    //Constructor para bsucar dispositivos cercanos dado una posicion por usuario   
    public Coordenadas(String ubicacion){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("890eac8e8a7b4ba8960778b0b971c9ed");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(ubicacion);
        request.setRestrictToCountryCode("ec");
        request.setBounds(-90.005657, -2.3009473,-79.850929 , -1.991027);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);

        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();
        if(firstResultLatLng == null){
            System.out.println("No se pudo encontrar su ubicacion");
            this.ubicacionExite = false;
        }else{
            this.ubicacionExite = true;
            this.latitud = firstResultLatLng.getLat();
            this.longitud = firstResultLatLng.getLng();
        }
    }
    public static String Ubicar(double latitud, double longitud){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("890eac8e8a7b4ba8960778b0b971c9ed");
        JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitud, longitud);
        request.setLanguage("es"); // prioritize results in a specific language using an IETF format language code
        request.setNoDedupe(true); // don't return duplicate results
        request.setLimit(5); // only return the first 5 results (default is 10)
        request.setNoAnnotations(true); // exclude additional info such as calling code, timezone, and currency
        request.setMinConfidence(3); // restrict to results with a confidence rating of at least 3 (out of 10)
        JOpenCageResponse response = jOpenCageGeocoder.reverse(request);
        // get the formatted address of the first result:
        String formattedAddress = response.getResults().get(0).getFormatted(); 
        return formattedAddress;
    }
    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public boolean isUbicacionExite() {
        return ubicacionExite;
    }
    
    

}
