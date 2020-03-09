package io.xiaotan.ppmtool.repositories;

import io.xiaotan.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectID);

    @Override
    Iterable<Project> findAll();
}
