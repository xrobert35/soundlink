package soundlink.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import soundlink.dto.socket.Greeting;

@Controller
public class SoundLinkSocketController {

	@Autowired
	private SimpMessagingTemplate template;

	private FireGreeting r = null;

	@MessageMapping("/hello")
	@SendTo("/player")
	public Greeting greeting() throws Exception {
		return new Greeting("Hello !");
	}

	@MessageMapping("/jack")
	@SendTo("/player")
	public Greeting jack() throws Exception {
		if (r == null) {
			r = new FireGreeting(this);
			new Thread(r).start();
		}

		return new Greeting("done");
	}

	public void autSend() throws InterruptedException {
		template.convertAndSend("/player", new Greeting("AutoMessage"));
	}

	class FireGreeting implements Runnable {

		private SoundLinkSocketController listener;

		public FireGreeting(SoundLinkSocketController listener) {
			this.listener = listener;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(5000);
					listener.autSend();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
