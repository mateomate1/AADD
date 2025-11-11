package com.practicas.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.practicas.Objetos.Direccion;
import com.practicas.Objetos.Empleado;
import com.practicas.Objetos.Empresa;

public class JsonManager {
    private static final Logger log = LoggerFactory.getLogger(JsonManager.class);

    public static Empresa getEmpresa(File ficheroEntrada) {
        Empresa empresa = null;
        ObjectMapper mapper = null;
        JsonNode nodoRaiz = null;

        try {
            mapper = new ObjectMapper();
            nodoRaiz = mapper.readTree(ficheroEntrada);
            JsonNode nodoEmpresa = nodoRaiz.get("empresa");
            if (nodoEmpresa.isObject()) {
                nodoEmpresa = (ObjectNode) nodoEmpresa;
                empresa = new Empresa();
                JsonNode nodoSector = nodoEmpresa.get("sector");
                if (!(nodoSector.isNull() || nodoSector == null) && nodoSector.isValueNode()) {
                    empresa.setSector(nodoSector.asText());
                }
                JsonNode nodoEmpleados = nodoEmpresa.get("empleados");
                if (!(nodoEmpleados.isNull() || nodoEmpleados == null) && nodoEmpleados.isObject()) {
                    Iterator<String> ids = nodoEmpleados.fieldNames();
                    List<Empleado> empleados = new ArrayList<>();
                    while (ids.hasNext()) {
                        String id = ids.next();
                        JsonNode nodoEmpleado = nodoEmpleados.get(id);
                        Empleado empleado = new Empleado();
                        empleado.setId(Integer.parseInt(id));
                        empleado.setNombre(nodoEmpleado.get("nombre").asText());
                        empleado.setPuesto(nodoEmpleado.get("puesto").asText());
                        empleado.setSalario(nodoEmpleado.get("salario").asDouble());
                        empleados.add(empleado);
                    }
                    empresa.setEmpleados(empleados);
                }
                JsonNode nodoDireccion = nodoEmpresa.get("direccion");
                if (!(nodoDireccion.isNull() || nodoDireccion == null) && nodoDireccion.isObject()) {
                    nodoDireccion = (ObjectNode) nodoDireccion;
                    Direccion dir = new Direccion();
                    if (nodoDireccion.get("calle") != null && !nodoDireccion.get("calle").isNull()) {
                        dir.setCalle(nodoDireccion.get("calle").asText());
                    }

                    if (nodoDireccion.get("ciudad") != null && !nodoDireccion.get("ciudad").isNull()) {
                        dir.setCiudad(nodoDireccion.get("ciudad").asText());
                    }

                    if (nodoDireccion.get("codigo_postal") != null && !nodoDireccion.get("codigo_postal").isNull()) {
                        dir.setCodigoPostal(nodoDireccion.get("codigo_postal").asInt());
                    }

                    empresa.setDireccion(dir);
                }
            } else {
                log.warn("El nodo empresa no es un objeto");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return empresa;
    }

    public static boolean writeEmpresa(Empresa empresa, File ficheroSalida) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode nodoRaiz = mapper.createObjectNode();

        ObjectNode nodoEmpresa = mapper.createObjectNode();
        nodoEmpresa.put("sector", empresa.getSector());
        nodoRaiz.set("empresa", nodoEmpresa);
        ObjectNode nodoEmpleados = mapper.createObjectNode();
        for (Empleado empleado : empresa.getEmpleados()) {
            ObjectNode nodoEmpleado = mapper.createObjectNode();
            nodoEmpleado.put("nombre", empleado.getNombre());
            nodoEmpleado.put("puesto", empleado.getPuesto());
            nodoEmpleado.put("salario", empleado.getSalario());
            nodoEmpleados.set(empleado.getId() + "", nodoEmpleado);
        }
        nodoEmpresa.set("empleados", nodoEmpleados);
        ObjectNode nodoDireccion = mapper.createObjectNode();
        if (empresa.getDireccion().getCalle() == null) {
            nodoDireccion.putNull("calle");
        } else {
            nodoDireccion.put("calle", empresa.getDireccion().getCalle());
        }

        if (empresa.getDireccion().getCiudad() == null) {
            nodoDireccion.putNull("ciudad");
        } else {
            nodoDireccion.put("ciudad", empresa.getDireccion().getCiudad());
        }

        if (empresa.getDireccion().getCodigoPostal() == null) {
            nodoDireccion.putNull("codigo_postal");
        } else {
            nodoDireccion.put("codigo_postal", empresa.getDireccion().getCodigoPostal());
        }
        nodoEmpresa.set("direccion", nodoDireccion);
        nodoRaiz.put("origen", "MateoMario");
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroSalida, nodoRaiz);
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
