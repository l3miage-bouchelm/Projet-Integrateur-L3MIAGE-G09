package l3m.cyber.planner.controllers;

import org.springframework.stereotype.Controller;
//*******************import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

// Aucune modification necessaire dans ce fichier

import l3m.cyber.planner.endpoints.PlannerEndpoints;
import l3m.cyber.planner.requests.PlannerParameter;
import l3m.cyber.planner.responses.PlannerResult;
import l3m.cyber.planner.services.PlannerService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class PlannerController implements PlannerEndpoints {
	private final PlannerService plannerService;

    
    @Override
    //************ @GetMapping("/planif/")
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin("http://130.190.78.146:4200")
    public PlannerResult planif(PlannerParameter params) {
        return plannerService.getResult(params);
    }
}
