package com.aavashkuikel.jobportal.controller;

import com.aavashkuikel.jobportal.entity.*;
import com.aavashkuikel.jobportal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class JobSeekerApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobSeekerProfileService jobSeekerProfileService;


    @Autowired
    public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UsersService usersService, JobSeekerApplyService jobSeekerApplyService, JobSeekerSaveService jobSeekerSaveService, RecruiterProfileService recruiterProfileService, JobSeekerProfileService jobSeekerProfileService) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
        this.jobSeekerApplyService = jobSeekerApplyService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.recruiterProfileService = recruiterProfileService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @GetMapping("/job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model)
    {
        JobPostActivity jobDetails= jobPostActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList=  jobSeekerApplyService.getCandidatesJobs(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList= jobSeekerSaveService.getJobCandidates(jobDetails);

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter")))
            {
             RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
             if (user !=null)
             {
                 model.addAttribute("applyList", jobSeekerApplyList);
             }
            }else {
              JobSeekerProfile user=  jobSeekerProfileService.getCurrentSeekerProfile();
              if (user!=null){
                  boolean exists= false;
                  boolean saved = false;
                  for (JobSeekerApply jobSeekerApply: jobSeekerApplyList)
                  {
                      if (jobSeekerApply.getUserId()== user.getUserAccountId())
                      {
                            exists=true;
                            break;
                      }
                  }
                  for (JobSeekerSave jobSeekerSave: jobSeekerSaveList)
                  {
                      if (jobSeekerSave.getUserId().getUserAccountId()== user.getUserAccountId())
                      {
                          saved= true;
                          break;
                      }
                  }
                  model.addAttribute("alreadyApplied", exists);
                  model.addAttribute("alreadySaved", saved);
              }
             }

        }
        JobSeekerApply jobSeekerApply= new JobSeekerApply();
        model.addAttribute("applyJob", jobSeekerApply);

        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "job-details";

    }


}
