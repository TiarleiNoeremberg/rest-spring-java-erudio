package br.com.tiarlei;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreatingController {
	
	private static final String template = "Olá, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greating")
	public Greating greating(@RequestParam(value = "name", defaultValue = "Mundo") String name) {
		
		return new Greating(counter.incrementAndGet(), String.format(template, name));
		
	}

}
