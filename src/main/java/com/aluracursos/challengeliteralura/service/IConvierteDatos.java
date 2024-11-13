package com.aluracursos.challengeliteralura.service;


public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
