package com.irit.upnp;

import com.irit.xml.GenerateurXml;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.HashMap;

/**
 * Created by mkostiuk on 19/06/2017.
 */
public class IntToDirectionAdapterServer implements Runnable {

    private LocalService<DirectionService> directionService;
    private LocalService<IntToConvertService> intToDirectionService;
    private GenerateurXml gen;

    @Override
    public void run() {
        final UpnpService upnpService = new UpnpServiceImpl();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                upnpService.shutdown();
            }
        });

        gen = new GenerateurXml();

        // Add the bound local device to the registry
        try {
            upnpService.getRegistry().addDevice(
                    createDevice()
            );
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        intToDirectionService.getManager().getImplementation().getPropertyChangeSupport()
                .addPropertyChangeListener(evt -> {
                    if (evt.getPropertyName().equals("commandInt")) {
                        HashMap<String,String> args = (HashMap<String, String>) evt.getNewValue();
                        int val = Integer.parseInt(args.get("COMMANDE"));

                        System.out.println(val);

                        String commande = "";

                        if (val < 50)
                            commande = "GAUCHE";
                        else
                            commande = "DROITE";

                        args.put("COMMANDE", commande);
                        String rep = "";
                        try {
                             rep = gen.getDocXml(args);
                             directionService.getManager().getImplementation().setCommande(rep);
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (TransformerException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private LocalDevice createDevice() throws ValidationException {

        DeviceIdentity identity =
                new DeviceIdentity(
                        UDN.uniqueSystemIdentifier("Int To Direction Adapter")
                );

        DeviceType type =
                new UDADeviceType("Adapter", 1);

        DeviceDetails details =
                new DeviceDetails(
                        "Int To Direction Adapter",					// Friendly Name
                        new ManufacturerDetails(
                                "UPS-IRIT",								// Manufacturer
                                ""),								// Manufacturer URL
                        new ModelDetails(
                                "IntToDirectionAdapter",						// Model Name
                                "Permet de convertir un entier en Direction",	// Model Description
                                "v1" 								// Model Number
                        )
                );

        intToDirectionService =
                new AnnotationLocalServiceBinder().read(IntToConvertService.class);
        intToDirectionService.setManager(
                new DefaultServiceManager<>(intToDirectionService, IntToConvertService.class)
        );

        directionService =
                new AnnotationLocalServiceBinder().read(DirectionService.class);
        directionService.setManager(
                new DefaultServiceManager<>(directionService, DirectionService.class)
        );

        return new LocalDevice(
                identity, type, details,
                new LocalService[] {directionService, intToDirectionService}
        );
    }
}
