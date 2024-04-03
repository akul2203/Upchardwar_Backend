package com.upchardwar.app.xwebsocket.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {



	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		System.out.println("setup--------------------------------");
		config.enableSimpleBroker("/user");
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
//                             .setAllowedOriginPatterns("*");
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
		resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		converter.setContentTypeResolver(resolver);
		messageConverters.add(converter);
		return false;
	}
	
	   public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	       
	    }
	   
	    @Override
	    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
	        // Increase the maximum message size (payload size)
	        registration.setMessageSizeLimit(1024 * 1024 *20); // 1 MB
	    }
}