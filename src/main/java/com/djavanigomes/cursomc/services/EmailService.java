package com.djavanigomes.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.djavanigomes.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
