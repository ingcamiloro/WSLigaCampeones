package com.claro.WSLigaCampeones.interfase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.claro.WSLigaCampeones.util.configuracion.Parametro;

@Component
public interface ParametroJpaRepository extends JpaRepository<Parametro, String> , CrudRepository<Parametro, String>{
	
	Parametro findByParNombre(String parNombre);

}
