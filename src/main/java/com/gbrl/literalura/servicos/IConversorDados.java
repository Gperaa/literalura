package com.gbrl.literalura.servicos;

public interface IConversorDados {
    <T> T converteDados(String json, Class<T> classe);
}
