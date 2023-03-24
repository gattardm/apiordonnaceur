package org.medhead.emergencysystem.apiordonnanceur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medhead.emergencysystem.apiordonnanceur.repository.IncidentProxy;
import org.medhead.emergencysystem.apiordonnanceur.model.Incident;

import lombok.Data;

@Data
@Service
public class IncidentService {

    @Autowired
    private IncidentProxy incidentProxy;

    public Incident getIncident(final int id) { return incidentProxy.getIncident(id); }

    public Iterable<Incident> getIncidents() { return incidentProxy.getIncidents(); }

    public void deleteIncident(final int id) { incidentProxy.deleteIncident(id); }

    public Incident saveIncident(Incident incident) {
        Incident savedIncident;
        savedIncident = incidentProxy.updateIncident(incident);
        return savedIncident;
    }

    public Iterable<Incident> treatedIncidents() {
        Iterable<Incident> listIncidents = incidentProxy.getIncidents();
        for ( Incident i: listIncidents) {
//            Thread.sleep(3000);
            if (i.getTraitement().contains("false")) { this.treatedInProgressIncident(i); continue; }
            if (i.getTraitement().contains("En cours")) { this.treatedIncident(i); }
        }
        return incidentProxy.getIncidents();
    }

    public Incident treatedInProgressIncident(Incident incident) {
        System.out.println("=======================================\nTraitement de l'incident  : " + incident.getId());
        System.out.println("\tmodification de l'état de l'incident '" + incident.getTraitement() +"' à 'En cours'");
        incident.setTraitement("En cours");
        this.saveIncident(incident);
        return incident;
    }

    public Incident treatedIncident(Incident incident) {
        System.out.println("avant : " + incident.toString());
        System.out.println("modification En cours --> true");
        incident.setTraitement("true");
        System.out.println("après : " + incident.toString());
        this.saveIncident(incident);

        return incident;
    }
}
