package io.azar.pservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.azar.pservice.PasswordDTO;
import io.azar.pservice.exception.PasswordComplainceFailureException;
import io.azar.pservice.service.ValidationService;

@Controller
@RequestMapping("/api/")
public class PasswordValidationController {

	@Autowired
	private ValidationService validationService;

	@PostMapping("/v1/validatePassword")
	@ResponseBody
	public ResponseEntity<String> validatePassword(@RequestBody PasswordDTO password) {

		this.validationService.validatePassword(password.getPassword());
		return new ResponseEntity<String>("Password passed compliance test successfully.", HttpStatus.OK);
	}

	@ExceptionHandler({ PasswordComplainceFailureException.class })
	protected ResponseEntity<String> handlePasswordLengthViolationException(PasswordComplainceFailureException ex) {
		String error = ex.getMessage();
		return new ResponseEntity<String>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
