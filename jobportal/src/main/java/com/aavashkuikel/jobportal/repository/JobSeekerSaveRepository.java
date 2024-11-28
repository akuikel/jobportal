package com.aavashkuikel.jobportal.repository;

import com.aavashkuikel.jobportal.entity.JobPostActivity;
import com.aavashkuikel.jobportal.entity.JobSeekerProfile;
import com.aavashkuikel.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave,Integer> {

    public List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountID);

    List<JobSeekerSave> findByJob(JobPostActivity job);

}
