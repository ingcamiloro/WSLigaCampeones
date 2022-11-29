//package com.claro.WSLigaCampeones.security.service;
//
//import com.claro.WSLigaCampeones.security.entity.Rol;
//import com.claro.WSLigaCampeones.security.enums.RolNombre;
//import com.claro.WSLigaCampeones.security.repository.RolRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@Transactional
//public class RolService {
//
//    @Autowired
//    RolRepository rolRepository;
//
//    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
//        return rolRepository.findByRolNombre(rolNombre);
//    }
//
//    public void save(Rol rol){
//        rolRepository.save(rol);
//    }
//}
