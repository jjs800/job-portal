package com.silva.repository;

import com.silva.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jobPostingRepository")
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
	Iterable<JobPosting> findJobPostingsByEmployerId(int empId);

}