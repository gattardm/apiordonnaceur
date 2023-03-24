package org.medhead.emergencysystem.apiordonnanceur.controller;

import lombok.Data;
import org.medhead.emergencysystem.apiordonnanceur.model.Operator;
import org.medhead.emergencysystem.apiordonnanceur.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.medhead.emergencysystem.apiordonnanceur.service.IncidentService;
import org.medhead.emergencysystem.apiordonnanceur.model.Incident;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Data
@Controller
public class OrdonnanceurController {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private OperatorService operatorService;


    @GetMapping("/")
    public String home(Model model) throws InterruptedException {
        Iterable<Incident> listIncidents = incidentService.getIncidents();

        for ( Incident incident : listIncidents) {
            if (incident.getTraitement().contains("false")) {
                //Passage de l'incident du statut "Non traité" au statut "En cours"
                incidentService.treatedInProgressIncident(incident);

                //Attente d'un opérateur disponible
                Operator operator = null;
                while(operator == null) { operator = operatorService.lookForFreeOperator(); }

                //Attribution de l'incident à l'opérateur disponible
                operatorService.attributedIncident(operator, incident);

                //Déclaration de l'opérateur comme "Non disponible"
                operatorService.busyOperator(operator);
            }
        }

        //Mise à jour des listes
        listIncidents = incidentService.getIncidents();
        Iterable<Operator> listOperators = operatorService.getOperators();

        //Ajout des attributs pour l'affichage HTML
        model.addAttribute("incidents", listIncidents);
        model.addAttribute("operators", listOperators);

        return "home";
    }

}
