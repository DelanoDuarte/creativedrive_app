/**
 * 
 */
package com.creativedrive.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.creativedrive.domain.Usuario;

/**
 * @author Delano Jr
 *
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

}
