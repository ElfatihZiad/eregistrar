package edu.mum.cs.cs425.eregistrar.security;

import edu.mum.cs.cs425.eregistrar.model.Student;
import edu.mum.cs.cs425.eregistrar.repository.StudentRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads a Student as a Spring Security principal. The student's business
 * key ({@code studentId}, e.g. "S1001") is used as the login username, and
 * the role stored on the entity becomes the granted authority (e.g.
 * "ROLE_STUDENT").
 */
@Service
public class StudentUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("No student with id " + studentId));

        return User.withUsername(student.getStudentId())
                .password(student.getPasswordHash())
                .authorities(new SimpleGrantedAuthority("ROLE_" + student.getRole().name()))
                .build();
    }
}
