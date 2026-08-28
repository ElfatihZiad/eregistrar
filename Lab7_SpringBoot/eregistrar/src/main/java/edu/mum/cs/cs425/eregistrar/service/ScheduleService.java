package edu.mum.cs.cs425.eregistrar.service;

import edu.mum.cs.cs425.eregistrar.model.Section;
import edu.mum.cs.cs425.eregistrar.repository.SectionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Read side of the schedule: the published sections a student can browse and register for. */
@Service
public class ScheduleService {

    private final SectionRepository sectionRepository;

    public ScheduleService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Transactional(readOnly = true)
    public List<Section> getPublishedSchedule() {
        return sectionRepository.findAllOrderByBlockAndCourse();
    }
}
