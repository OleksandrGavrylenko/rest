package it.discovery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@ResponseBody
@RequestMapping("current")
public class DateTimeController {

    @RequestMapping("/date")
    public String currentDate() {
        return LocalDate.now().toString();
    }

    @RequestMapping("/time")
    public String currentTime() {
        return LocalTime.now().toString();
    }
}
