package com.claro.WSLigaCampeones.security.service;

import com.claro.WSLigaCampeones.dto.UsuariosRequest;
import com.claro.WSLigaCampeones.dto.UsuariosResponse;
import com.claro.WSLigaCampeones.security.com.claro.WSLigaCampeones.security.encriptadoaes.EncriptadorAES;
import com.claro.WSLigaCampeones.security.entity.Usuario;
import com.claro.WSLigaCampeones.security.entity.UsuarioPrincipal;
import com.claro.WSLigaCampeones.util.bd.ServiciosBD;
import com.claro.WSLigaCampeones.util.configuracion.Constantes;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static Propiedades prop = Propiedades.getInstance();

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
    	
    	ServiciosBD bd= new ServiciosBD(); 
		UsuariosResponse userResponse= new UsuariosResponse();
		UsuariosRequest usuarioRequest=new UsuariosRequest();
		 EncriptadorAES encriptador = new EncriptadorAES();
		 PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		usuarioRequest.setCedula(nombreUsuario);
		try {
			bd.prcAdmUsuarios(usuarioRequest,userResponse,"C", "security");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Usuario usuario=new Usuario();
		if(userResponse.getCursorUsuarios().get(0)!=null) {
			usuario.setNombreUsuario(userResponse.getCursorUsuarios().get(0).getCedula());
			String passworgDesencriptado="";
			try {
				 passworgDesencriptado=encriptador.desencriptar(userResponse.getCursorUsuarios().get(0).getClave(), prop.getPropiedad(Constantes.CLAVE_ENCRIPTACION));
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
					| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			usuario.setPassword(passwordEncoder.encode(passworgDesencriptado));
			usuario.setNombre(userResponse.getCursorUsuarios().get(0).getNombres()
					+" "+userResponse.getCursorUsuarios().get(0).getNombres());
			usuario.setEmail(userResponse.getCursorUsuarios().get(0).getCorreo());
		}
		
		
//        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
        return UsuarioPrincipal.build(usuario);
    }
}
