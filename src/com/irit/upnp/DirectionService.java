package com.irit.upnp;

import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

/**
 * Created by mkostiuk on 19/06/2017.
 */

@UpnpService(
        serviceId = @UpnpServiceId("DirectionService"),
        serviceType = @UpnpServiceType(value = "DirectionService", version = 1)
)
public class DirectionService {

    private final PropertyChangeSupport propertyChangeSupport;

    public DirectionService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "Commande")
    private String commande = "";

    @UpnpAction(name = "SetCommande")
    public void setCommande(@UpnpInputArgument(name = "NewCommandeValue") String c) {
        commande = c;
        getPropertyChangeSupport().firePropertyChange("Commande", "", commande);
    }
}
