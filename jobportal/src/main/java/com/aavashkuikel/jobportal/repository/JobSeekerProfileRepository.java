package com.aavashkuikel.jobportal.repository;

import com.aavashkuikel.jobportal.entity.JobSeekerProfile;
import com.aavashkuikel.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {
}
