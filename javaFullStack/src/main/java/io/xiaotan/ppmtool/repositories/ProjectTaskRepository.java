package io.xiaotan.ppmtool.repositories;

import io.xiaotan.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

}
