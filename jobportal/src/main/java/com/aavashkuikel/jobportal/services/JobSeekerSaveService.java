package com.aavashkuikel.jobportal.services;

import com.aavashkuikel.jobportal.entity.JobPostActivity;
import com.aavashkuikel.jobportal.entity.JobSeekerProfile;
import com.aavashkuikel.jobportal.entity.JobSeekerSave;
import com.aavashkuikel.jobportal.repository.JobSeekerSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;


    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId)
    {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job)
    {
        return jobSeekerSaveRepository.findByJob(job);
    }

}