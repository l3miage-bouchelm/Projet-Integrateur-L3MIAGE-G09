package fr.uga.l3miage.integrator.responses;

import fr.uga.l3miage.integrator.DataType.Adresse;
import fr.uga.l3miage.integrator.enums.EtatsDeCommande;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import java.util.Date;

@Data
@Schema(name = "CommandeResponseDTO", description = "Commande response")
public class CommandeResponseDTO {
    private String reference;
    private EtatsDeCommande etat;
    private Date dateDeCommande;
    private double montant;
    //private String adresseClient;

    private String commentaire;
    private int tddTheorique;
    private int tdmTheorique;
    private Date dateDeLivraisonEffective;
    private int dureeDeLivraison;

    //private Adresse adresseClient; // Nouveau champ
    //private String clientEmail;

    private ClientResponseDTO client; // Ajouté ici




}
