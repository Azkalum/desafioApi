package gft.desafioapi.service;

import gft.desafioapi.entity.Usuario;
import gft.desafioapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UsuarioService implements UserDetailsService {

        @Autowired
        private PasswordEncoder encoder;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Transactional
        public Usuario salvarUsuario(Usuario usuario){

                return usuarioRepository.save(usuario);
        }

        public UserDetails authUsuario(Usuario usuario ){

                UserDetails user = loadUserByUsername(usuario.getUsuario());

                boolean igual = encoder.matches( usuario.getSenha(), user.getPassword() );

                if(igual){
                        return user;
                }

                throw new RuntimeException(String.valueOf(HttpStatus.BAD_REQUEST));
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                Usuario usuario = usuarioRepository.findByUsuario(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

                String[] roles = usuario.isAdmin() ?
                        new String[]{"ADMIN", "USER"} : new String[]{"USER"};

                return User
                        .builder()
                        .username(usuario.getUsuario())
                        .password(usuario.getSenha())
                        .roles(roles)
                        .build();

        }

}
