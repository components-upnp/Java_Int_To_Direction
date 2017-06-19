package com.irit.upnp;

import com.irit.xml.LecteurXml;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mkostiuk on 19/06/2017.
 */

@UpnpService(
        serviceType = @UpnpServiceType(value = "IntToConvertService", version = 1),
        serviceId = @UpnpServiceId("IntToConvertService")
)
public class IntToConvertService {

    private final PropertyChangeSupport propertyChangeSupport;

    public IntToConvertService() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "CommandInt")
    String commandInt = "";

    @UpnpAction(name = "SetCommandInt")
    public void setCommandInt(@UpnpInputArgument(name = "NewCommandeIntValue") String c) throws IOException, SAXException, ParserConfigurationException {
        commandInt = c;
        LecteurXml l = new LecteurXml(commandInt);

        System.out.println(commandInt);
        HashMap<String,String> args = new HashMap<>();
        args.put("UDN", l.getUdn());
        args.put("COMMANDE", l.getIntVal());
        getPropertyChangeSupport().firePropertyChange("commandInt", "", args);
    }
}
