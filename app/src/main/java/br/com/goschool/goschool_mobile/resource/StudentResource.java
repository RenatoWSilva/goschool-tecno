package br.com.goschool.goschool_mobile.resource;
import java.util.List;

import ModulesMaps.Student;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentResource {

    @GET("/student")
    Call<List<Student>> get();

    @GET("/student/{id}")
    Call<List<Student>> get(Integer id);

}
