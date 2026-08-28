package edu.mum.cs.cs425.eregistrar.repository;

import edu.mum.cs.cs425.eregistrar.model.Registration;
import edu.mum.cs.cs425.eregistrar.model.RegistrationStatus;
import edu.mum.cs.cs425.eregistrar.model.Section;
import edu.mum.cs.cs425.eregistrar.model.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByStudentAndStatus(Student student, RegistrationStatus status);

    Optional<Registration> findByStudentAndSectionAndStatus(
            Student student, Section section, RegistrationStatus status);
}
