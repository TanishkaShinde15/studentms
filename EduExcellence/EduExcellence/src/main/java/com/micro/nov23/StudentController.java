package com.micro.nov23;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.micro.nov23.model.Payment;
import com.micro.nov23.model.Student;

@RestController
public class StudentController {
	
	@Autowired
	public StudentRepository studentRepository;
	
	@Autowired
	WebClient.Builder webClientBuilder;
	
	@GetMapping("fetchAllStudents")
	public ResponseEntity<List<Student>> fetchAllStudents(){
		
		return ResponseEntity.ok(studentRepository.findAll());
	}
	@GetMapping("/fetchSingleRecord/{firstName}")
	public ResponseEntity<Student >fetchSingleStudent(@PathVariable(value="firstName") String firstName){


        if (studentRepository.findById(firstName).isPresent())
        {
			return ResponseEntity.ok(studentRepository.findById(firstName).get());

        }
       else
        {
            return ResponseEntity.badRequest().body(null);
        }
}
	@PostMapping("createNewStudent")
	public ResponseEntity<String> createNewStudent(@RequestBody Student student){
		
		studentRepository.save(student);
		return ResponseEntity.ok("Created student record successfully");
	}
	@DeleteMapping("deleteStudent/{firstName}")
	public ResponseEntity<String> deleteStudent(@PathVariable(value="firstName") String firstName){
		
		 if (studentRepository.findById(firstName).isPresent())
	        {
			 studentRepository.deleteById(firstName);
			 return ResponseEntity.ok("Deleted student record successfully");
	        }
		 else
	        {
	            return ResponseEntity.badRequest().body(null);
	        }
	}
	@PutMapping("updateStudent/{firstName}")
	public Student updateStudent(@RequestBody Student student,@PathVariable(value="firstName") String firstName){
		Student studentUpdate=studentRepository.findById(firstName).get();
		if(student.getLastName()!=null) {
			studentUpdate.setLastName(student.getLastName());
		}
		if(student.getCity()!=null) {
			studentUpdate.setCity(student.getCity());
		}
			return this.studentRepository.save(studentUpdate);
	}
	
	@GetMapping("fetchFeePaymentInfo")
	public Payment fetchFeePaymentDetails(){
		
		return webClientBuilder.build().get().uri("http://localhost:8082/fetchAllPaymentDetails")
				.retrieve().bodyToMono(Payment.class).block();
		
	}

}
