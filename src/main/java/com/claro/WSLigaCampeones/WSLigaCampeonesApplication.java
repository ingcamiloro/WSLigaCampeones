package com.claro.WSLigaCampeones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.Constantes;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

@SpringBootApplication
public class WSLigaCampeonesApplication {

	static {
		Configurador.configurar(Constantes.RUTA_ARCHIVO_PROPIEDADES, UtilsConstantes.LOGGER_PRINCIPAL,
				Constantes.APLICACION);
	}
	public static void main(String[] args) {
		SpringApplication.run(WSLigaCampeonesApplication.class, args);
		
	}

}
