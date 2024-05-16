package l3m.cyber.planner.endpoints;

import org.springframework.http.HttpStatus;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.CrossOrigin;
>>>>>>> 3a67de21192c0dc39bad616e95e91836a7c4f4e8
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import l3m.cyber.planner.requests.PlannerParameter;
import l3m.cyber.planner.responses.PlannerResult;

@RestController
@RequestMapping("/planner")
@Tag(name = "Planner endpoints")
@CrossOrigin(origins = "http://localhost:4200")
public interface PlannerEndpoints {

    @Operation(description = "Appeler le planner afin d'avoir une planification")
    @ApiResponse(responseCode = "200", description = "La planification calculer par le web service")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/planif")
    PlannerResult planif(@RequestBody PlannerParameter params);
}
