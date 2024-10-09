package com.example.retrofit;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class SimpleService {
    public static final String API_URL = "https://api.github.com";

    // Clase interna que representa un repositorio de GitHub
    public static class Repo {
        public final String name;
        public final String description;
        public final int stargazers_count;

        public Repo(String name, String description, int stargazers_count) {
            this.name = name;
            this.description = description;
            this.stargazers_count = stargazers_count;
        }
    }

    // Interfaz para definir la solicitud HTTP a la API de GitHub
    public interface GitHub {
        @GET("/users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }

    // Método principal
    public static void main(String... args) throws IOException {
        // Crear el cliente Retrofit que apunta a la API de GitHub
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia de la interfaz GitHub
        GitHub github = retrofit.create(GitHub.class);

        // Hacer una llamada a la API para obtener los repositorios del usuario
        Call<List<Repo>> call = github.listRepos("ddaassdaassd");

        // Ejecutar la llamada de manera síncrona
        List<Repo> repos = call.execute().body();

        // Imprimir los detalles de cada repositorio
        if (repos != null) {
            for (Repo repo : repos) {
                System.out.println("Nombre: " + repo.name);
                System.out.println("Descripción: " + repo.description);
                System.out.println("Estrellas: " + repo.stargazers_count);
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No se encontraron repositorios.");
        }
    }
}