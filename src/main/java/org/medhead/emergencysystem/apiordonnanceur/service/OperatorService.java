package org.medhead.emergencysystem.apiordonnanceur.service;

import org.medhead.emergencysystem.apiordonnanceur.model.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medhead.emergencysystem.apiordonnanceur.repository.OperatorProxy;
import org.medhead.emergencysystem.apiordonnanceur.model.Operator;

import lombok.Data;

@Data
@Service
public class OperatorService {

    @Autowired
    private OperatorProxy operatorProxy;

    public Operator getOperator(final int id) { return  operatorProxy.getOperator(id); }

    public Iterable<Operator> getOperators() { return operatorProxy.getOperators(); }

    public Operator saveOperator(Operator operator) {
        Operator savedOperator;
        savedOperator = operatorProxy.updateOperator(operator);
        return savedOperator;
    }

    public Operator attributedIncident(Operator operator, Incident i) {
        System.out.println("\tAttribution de l'incident " + i.getId() + " à l'opérateur " + operator.getId());
        operator.setIncidentId(i.getId().toString());
        this.saveOperator(operator);
        return operator;
    }

    public Operator lookForFreeOperator() {
        Iterable<Operator> listOperators = operatorProxy.getOperators();
        for (Operator o : listOperators) {
            if(o.getAvailable().compareTo("true")==0) {
                System.out.println("\tRecherche d'une opérateur disponible");
                System.out.println("\tL'opérateur " + o.getId() + " est disponible");
                return o; }
        }
        return null;
    }

    public Operator busyOperator(Operator operator) {
        System.out.println("\tL'opérateur " + operator.getId() + " est maintenant OCCUPE");
        operator.setAvailable("false");
        this.saveOperator(operator);
        return operator;
    }
}
