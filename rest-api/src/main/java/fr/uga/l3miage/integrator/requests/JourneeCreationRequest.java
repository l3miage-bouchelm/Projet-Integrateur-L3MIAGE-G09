package fr.uga.l3miage.integrator.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class JourneeCreationRequest {

    @NotNull
    private String reference;

    @NotNull
    private Date date;

    @NotNull
    private double distanceAParcourir;

    @NotNull
    private double montant;

    @NotNull
    private int tdmTheorique;
}
