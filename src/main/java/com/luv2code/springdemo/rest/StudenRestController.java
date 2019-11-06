package com.luv2code.springdemo.rest;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luv2code.springdemo.entity.Student;
import com.luv2code.springdemo.responseentity.ResponseCode;
import com.luv2code.springdemo.responseentity.StudentErrorResponse;


@RestController
@RequestMapping("/api")
public class StudenRestController {

	private List<Student> theStudents = new ArrayList<>();;

/*	@PostConstruct
	public void loadData()
	{
		theStudents = new ArrayList<>();
		theStudents.add(new Student("Mudit","Agarwal"));
		theStudents.add(new Student("Shagun","Agarwal"));
	}  */

	@GetMapping("/getStudent")
	public List<Student> getStudentList() {
		return theStudents;
	}

	@PostMapping("/addStudent")
	public ResponseEntity<ResponseCode> addStudent(@RequestBody Student stu)
	{
		theStudents.add(stu);
		ResponseCode response = new ResponseCode();
		response.setStatus(HttpStatus.ACCEPTED.value());
		response.setMessage("Student successfully added");
		return new ResponseEntity<ResponseCode>(response,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getStudent/{studentId}")
	public Student getStudent(@PathVariable int studentId)
	{
		if(studentId >= theStudents.size() || studentId < 0)
			throw new StudentNotFoundException("Student id not found :"+ studentId);
		return theStudents.get(studentId);	
	}
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(Exception ex)
	{
		//create a StudentErrorResponse
		StudentErrorResponse error = new StudentErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		//return ResponseEntity
		return new ResponseEntity<StudentErrorResponse>(error, HttpStatus.NOT_FOUND);
		
	}

}
