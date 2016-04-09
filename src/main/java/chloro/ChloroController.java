package chloro;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChloroController {
	@RequestMapping("/")
	public String chloro(Model model) throws IOException {
		return "chloro.html";
	}
	
}
