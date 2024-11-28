package com.aavashkuikel.jobportal.repository;

import com.aavashkuikel.jobportal.entity.JobPostActivity;
import com.aavashkuikel.jobportal.entity.JobSeekerApply;
import com.aavashkuikel.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);

}
