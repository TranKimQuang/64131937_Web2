package edu.quangtk.services;

import edu.quangtk.entity.Faculty;
import edu.quangtk.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + id));
    }
    public long getFacultyCount() {
        return facultyRepository.count();
    }


    public Faculty getFacultyBySlug(String slug) {
        return facultyRepository.findBySlug(slug);
    }

    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        Faculty faculty = getFacultyById(id);
        faculty.setName(updatedFaculty.getName());
        faculty.setSlug(updatedFaculty.getSlug());
        faculty.setDescription(updatedFaculty.getDescription());
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        Faculty faculty = getFacultyById(id);
        facultyRepository.delete(faculty);
    }
}