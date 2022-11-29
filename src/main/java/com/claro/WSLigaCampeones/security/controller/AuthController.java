package com.claro.WSLigaCampeones.security.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claro.WSLigaCampeones.dto.Mensaje;
import com.claro.WSLigaCampeones.dto.UsuariosRequest;
import com.claro.WSLigaCampeones.dto.UsuariosResponse;
import com.claro.WSLigaCampeones.dto.ValidarUsuarioResponse;
import com.claro.WSLigaCampeones.security.dto.JwtDto;
import com.claro.WSLigaCampeones.security.dto.LoginUsuario;
import com.claro.WSLigaCampeones.security.dto.NuevoUsuario;
import com.claro.WSLigaCampeones.security.entity.Rol;
import com.claro.WSLigaCampeones.security.entity.Usuario;
import com.claro.WSLigaCampeones.security.enums.RolNombre;
import com.claro.WSLigaCampeones.security.jwt.JwtProvider;
//import com.claro.WSLigaCampeones.security.service.RolService;
//import com.claro.WSLigaCampeones.security.service.UsuarioService;
import com.claro.WSLigaCampeones.util.bd.ServiciosBD;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
public class AuthController { 

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
     
//    @Autowired
//    UsuarioService usuarioService;

//    @Autowired
//    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) throws SQLException, Exception{
		
		ValidarUsuarioResponse response = new ValidarUsuarioResponse();
		response=ServiciosBD.validarUsuario(nuevoUsuario.getNombreUsuario(), nuevoUsuario.getPassword(), response);

        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(response!=null) {
//            return new ResponseEntity(new Mensaje("el usuario no existe"), HttpStatus.BAD_REQUEST);
//        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
//            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);

//                new Usuario(nuevoUsuario.getNombreUsuario(),
//                        passwordEncoder.encode(nuevoUsuario.getPassword()));
//      
        
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        System.out.println("clave: "+usuario.getPassword());
        }
        
//        Set<Rol> roles = new HashSet<>();
//        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
//        if(nuevoUsuario.getRoles().contains("admin"))
//            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
//        usuario.setRoles(roles);
//        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @GetMapping("/token")
    public ResponseEntity<?> token(@RequestParam(name="nombreUsuario",required= true)String nombreUsuario ,@RequestParam(name="password",required= true)String password) throws SQLException, Exception{
    	JwtDto jwtDto =null;
      try {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nombreUsuario, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String pass=ServiciosBD.pass(password);
        jwtDto = new JwtDto(jwt, 1);
        return new ResponseEntity(jwtDto, HttpStatus.OK);
		} catch (AuthenticationException e) {
	        jwtDto = new JwtDto("", 0);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtDto);

		}
    }
}
